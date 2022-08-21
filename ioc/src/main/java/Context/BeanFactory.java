package Context;

import bean.*;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import exception.CycleDependencyException;
import utils.AnnotationUtils;
import utils.BeanUtils;
import utils.TypeConvert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * @author myd
 * @date 2022/8/19  15:08
 */

public class BeanFactory {

    static Logger log = Logger.getLogger(BeanFactory.class.getName());

    public static final String SINGLE="single";

    List<BeanPostProcessor> beanPostProcessors;



    ConfigInfo configInfo = new ConfigInfo();
    /*
    * 将 单例 beanDefinition初始化成Object ，没有给成员变量赋值，没有经过任何处理
    * */
    Map<String,Object> initSingleBean = new HashMap<>();

    /*
    * 经过beanPostProcessor处理过的 singleBean；比如aop处理之类的
    * */
    Map<String,Object> postProcessSingleBean = new HashMap<>();

    /*
    * 可以使用的 bean，结合BeanDefinition中的信息给 postProcessSingleBean 中bean的属性赋值
    * */
    Map<String,Object> useSingleBean = new HashMap<>();
    /*
    * 正在创建的 beanName
    * */
    Set<String> creatingBean  = new LinkedHashSet<>();

    public void registerBeanPostProcessor(){
        //扫描 所有的BeanPostProcessor

    }

    public Object creatBean(BeanDefinition beanDefinition,boolean creatSingle) throws Exception {
        String beanName = beanDefinition.getBeanName();
        if (creatSingle && !beanDefinition.getScope().equals(SINGLE))
            throw new RuntimeException("  single  bean  have prototype bean [ " + beanName + "]");
        if (creatingBean.contains(beanName)) {
            StringBuilder sb = new StringBuilder();
            creatingBean.forEach(name -> sb.append(name + "->"));
            throw new CycleDependencyException(sb.toString() + beanName);
        }

        /*
         * A构造函数引用 B，在创建 A时已经创建了B
         *
         * 在遍历到B的BeanDefinition信息时，就可以再次不用创建B了
         * */
        if (initSingleBean.containsKey(beanName))
            return initSingleBean.get(beanName);

        /*将正在创建的bean加入到creatingBean中*/
        creatingBean.add(beanName);

        Object bean = null;
        Class beanClass = beanDefinition.getBeanClass();
        if (beanDefinition.nonParamConstruct()) {//无参构造
            bean = beanClass.newInstance();
        } else {//有参构造
            ConstructorValues constructorValues = beanDefinition.getConstructorValues();
            Constructor constructor = BeanUtils.getConstructor(beanClass, constructorValues.getParamTypeListOrder());
            Object[] initArgs = new Object[constructorValues.getParamValueListOrder().size()];
            //获取构造方法的参数值
            getConstructParamValue(constructorValues,initArgs);
            getConstructorParamRefValue(constructorValues,initArgs,creatSingle);
            bean = constructor.newInstance(initArgs);
        }
        creatingBean.remove(beanName);
        if(beanDefinition.getScope().equals(SINGLE))initSingleBean.put(beanName,bean);
        return bean;
    }
    public Map<String,Object> getInitSingleBean(){
        return initSingleBean;
    }
    public void getConstructParamValue(ConstructorValues constructorValues,Object[] initArgs){
        List<String> paramTypeListOrder = constructorValues.getParamTypeListOrder();
        List<String> paramValueListOrder = constructorValues.getParamValueListOrder();
        List<PropertyValue> propertyValues = constructorValues.getPropertyValues();
        if (propertyValues == null)return ;
        for (PropertyValue propertyValue : propertyValues) {
            String type = propertyValue.getType();
            String value = propertyValue.getValue();
            for (int i = 0; i < paramTypeListOrder.size(); i++) {
                if (type.equals(paramTypeListOrder.get(i)) && value.equals(paramValueListOrder.get(i))) {
                    //转化值
                    initArgs[i] = BeanUtils.convertTrueTypeValue(type, value, configInfo);
                    break;
                }
            }
        }
    }
    public void getConstructorParamRefValue(ConstructorValues constructorValues,Object[] initArgs,boolean creatSingle) throws Exception {
        List<String> paramTypeListOrder = constructorValues.getParamTypeListOrder();
        List<String> paramValueListOrder = constructorValues.getParamValueListOrder();
        List<BeanReference> beanReferences = constructorValues.getBeanReferences();
        if (beanReferences == null)return;
        for (BeanReference beanReference : beanReferences) {
            String type = beanReference.getType();
            String ref = beanReference.getRef();
            for (int i = 0; i < paramTypeListOrder.size(); i++) {
                if (type.equals(paramTypeListOrder.get(i)) && ref.equals(paramValueListOrder.get(i))) {
                    initArgs[i] = creatBean(configInfo.getBeanDefinitionByName(ref),creatSingle);
                    break;
                }
            }
        }
    }


    public Object creatPrototype(BeanDefinition beanDefinition) throws Exception {

        log.info("create bean type:"+beanDefinition.getScope()+";beanName="+beanDefinition.getBeanName());
       Object bean =  creatBean(beanDefinition,false);
       if(beanPostProcessors != null){
           beanPostProcessors.forEach(beanPostProcessor -> {
               try {
                   beanPostProcessor.postProcessor();
               } catch (Exception e) {
                   e.printStackTrace();
                   throw new RuntimeException(e.getMessage());
               }
           });
       }
       fillBeanProperty(beanDefinition,bean);
       return bean;
    }
    public void fillBeanProperty(BeanDefinition beanDefinition,Object bean) throws Exception {

        //被proxy代理的类，生成bean和原本类没有继承关系，因此不能直接设置字段值，只能找到
        //其原本的类，将其赋值；因为被代理类，会持有原本类，因此赋值给原本类之后，就行了；
        if(beanDefinition.getScope().equals(SINGLE))useSingleBean.put(beanDefinition.getBeanName(),bean);
        if(Proxy.class.isInstance(bean)){
            bean= initSingleBean.get(beanDefinition.getBeanName());
        }
        //cglib ,没有被代理的类都可以
        FieldValues fieldValues = beanDefinition.getFieldValues();
        if(fieldValues == null)return;
        Class beanClass = beanDefinition.getBeanClass();
        List<PropertyValue> propertyValues = fieldValues.getPropertyValues();
        List<BeanReference> beanReferences = fieldValues.getBeanReferences();
        if(propertyValues != null)
        for (PropertyValue propertyValue : propertyValues) {
            Field field = beanClass.getDeclaredField(propertyValue.getName());
            field.setAccessible(true);

            field.set(bean, BeanUtils.convertTrueTypeValue(field.getType().getTypeName(),propertyValue.getValue().trim(),configInfo));
        }
        if(beanReferences != null)
        for (BeanReference beanReference : beanReferences) {
            Field field = beanClass.getDeclaredField(beanReference.getName());
            field.setAccessible(true);
            boolean autowired = beanReference.isAutowired();
            if(!autowired){
                Object value = postProcessSingleBean.get(beanReference.getRef());
                if(value ==null)value = creatPrototype(configInfo.getBeanDefinitionByName(beanReference.getRef()));
                field.set(bean,value);
            }else{
                Object value =  getBeanByClassName(beanReference.getType(),postProcessSingleBean);
                if(value == null) value = creatPrototype(configInfo.getBeanDefinitionByClassName(beanReference.getType()));
                field.set(bean,value);
            }
        }

    }




    public Object getBeanByClassName(String className,Map<String,Object> cache){
        int count=0;
        Object ret=null;
        for (Object value : cache.values()) {
            if(value.getClass().getTypeName().equals(className) ||
                    AnnotationUtils.matchSuperOrInterface(value.getClass(),className)){
                if(count == 0){
                    ret=value;
                    count=1;
                }else{
                    throw new RuntimeException(className+" :@Autowired注解该类型超过 1个对象");
                }
            }
        }
        return ret;
    }


    /*

    通过id找bean
     */
    public Object getBeanByName(String name) throws Exception {
        BeanDefinition beanDefinition = configInfo.getBeanDefinitionByName(name);
        String single = beanDefinition.getScope();
        if(single.equals(SINGLE))
            return  useSingleBean.get(name);
        else
            return creatPrototype(beanDefinition);
    }


    public Object getBeanByClassName(String className) throws Exception {
        BeanDefinition beanDefinition = configInfo.getBeanDefinitionByClassName(className);
        String single = beanDefinition.getScope();
        if(single.equals(SINGLE)){
            for (Object value : useSingleBean.values()) {
                if(value.getClass().getTypeName().equals(className) ||
                        AnnotationUtils.matchSuperOrInterface(value.getClass(),className))
                    return value;
            }
        }else{
            return creatPrototype(beanDefinition);

        }
        throw new RuntimeException("找不到类型："+className);
    }

}









