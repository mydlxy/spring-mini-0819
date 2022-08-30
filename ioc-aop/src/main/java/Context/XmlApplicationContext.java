package Context;

import aop.advice.Advice;
import aop.config.MethodAdvice;
import aop.proxy.AopProxy;
import bean.BeanDefinition;
import org.dom4j.DocumentException;
import parse.xml.RegisterParseLabel;
import parse.xml.XmlParse;
import parse.xml.node.ComponentScan;
import parse.xml.node.XmlNode;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author myd
 * @date 2022/8/19  15:33
 */

public class XmlApplicationContext implements ApplicationContext {

    BeanFactory beanFactory = new BeanFactory();

    MethodAdvice methodAdvice;

    String[] paths;

    int countDownLatch;

    BeanPostProcessor beanPostProcessor;

    public XmlApplicationContext(String path) throws Throwable {
        this(path,null,null);
    }




    /*
    * 可以注册 ：
    * 要解析的标签 ，
    *
    * 以及要扫描的注解
    * */
    public XmlApplicationContext(String paths,
                                 Map<String, XmlNode> xmlNodes,
                                 List<Class<? extends Annotation>> scanClasses) throws Throwable {
        this.paths = paths.split(",");
        countDownLatch = this.paths.length;
        //注册要解析的新标签
        if(xmlNodes!=null) RegisterParseLabel.getRegisterParseLabel().setRegisterParseLabels(xmlNodes);
        //注册要扫描的注解类
        if(scanClasses != null) ComponentScan.getComponentScan().addScanType(scanClasses);
        loadBean();
    }


     void  getConfigInfo() throws Throwable {
        if(countDownLatch > 1) {
            threadsParseXml();
        }else{
            XmlParse.parseXml(paths[0],beanFactory.configInfo);
        }

     }



     public void threadsParseXml() throws InterruptedException {
         ExecutorService tasks = Executors.newFixedThreadPool(countDownLatch);
         CountDownLatch latch = new CountDownLatch(countDownLatch);
         StringBuffer sb = new StringBuffer();
         for (int i = 0; i < countDownLatch; i++) {
             int finalI = i;
             tasks.execute(()->{
                 try {
                     XmlParse.parseXml(paths[finalI],beanFactory.configInfo);
                 } catch (DocumentException e) {
//                        e.printStackTrace();
                     sb.append("exception parse  ["+paths[finalI]+"] :"+e.getMessage());
                 }catch (RuntimeException e){
                     sb.append("exception parse  ["+paths[finalI]+"] :"+e.getMessage());
                 }
                 latch.countDown();
             });
         }
         latch.await();
         tasks.shutdown();
         if(!sb.toString().equals(""))
             throw new RuntimeException(sb.toString());

     }



     public void loadBean()throws Throwable {
        //将配置的bean加载到容器中
         getConfigInfo();
        //初始化init;
         initSingleBeans();
         //postProcessor 注册实现 BeanPostProcessor接口的类
         registerBeanPostProcessor();
         //处理bean
         beanPostProcessor();
         //填充bean
         fillBeanProperties();

     }


     public void initSingleBeans(){

         Collection<BeanDefinition> values = beanFactory.configInfo.getBeanDefinitions().values();
         values.forEach(beanDefinition -> {
             try {
                 if(beanDefinition.getScope().equals(BeanFactory.SINGLE))
                    beanFactory.creatBean(beanDefinition,true);
             } catch (Exception e) {
                 e.printStackTrace();
                 throw new RuntimeException(e.getMessage());
             }
         });


     }



     public void registerBeanPostProcessor(){
         beanFactory.registerBeanPostProcessor(AopProxy.getInstance());
         beanPostProcessor = AopProxy.getInstance();
     }


     //循环遍历beanPostProcessor接口；
    public void beanPostProcessor(){
        if(!beanFactory.configInfo.getAspect().isEmpty()){
            methodAdvice = new MethodAdvice();
            initMethodAdvice();
            beanFactory.getInitSingleBean().forEach((name,bean)->{
            try {
                   bean =  beanPostProcessor.postProcessor(bean,methodAdvice);
                    beanFactory.postProcessSingleBean.put(name,bean);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage());
                }
            });
        }else{
            beanFactory.postProcessSingleBean= beanFactory.initSingleBean;
        }
    }


    /**
     * aspect将ref，转换成Object；
     *
     */
    void initMethodAdvice(){
        Map<String, List<Advice>> aspects = beanFactory.configInfo.getAspect();
        aspects.forEach((ref,advices)->{
            Object bean = beanFactory.getBeanInInitBean(ref);
            List<Advice> adviceSet = aspects.get(ref);
            if(bean == null){
                    throw new RuntimeException("aspect bean not single ref:"+ ref);
            }
            fillMethodAdvice(bean,adviceSet);
        });

    }

    /*
    * methodName  ->  method
    * */
    void fillMethodAdvice(Object aspect,List<Advice> advices){
        Class<?> beanClass = aspect.getClass();
        advices.forEach(advice->{
            String methodName = advice.getMethodName();
            try {
                advice.setMethod(beanClass.getDeclaredMethod(methodName,null)).setAspect(aspect);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new RuntimeException("获取切面配置的method 异常"+methodName);
            }
            methodAdvice.addAdvice(advice);
        });
    }

    public void fillBeanProperties(){
        ConcurrentHashMap<String, BeanDefinition> beanDefinitions = beanFactory.configInfo.getBeanDefinitions();
        Map<String, Object> postProcessSingleBean = beanFactory.postProcessSingleBean;
        postProcessSingleBean.forEach((name,bean)->{
            BeanDefinition beanDefinition = beanDefinitions.get(name);
            try {
                beanFactory.fillBeanProperty(beanDefinition,bean);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public Object getBean(String name) {
        try {
            return beanFactory.getBeanByName(name);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Object getBean(Class beanClass) {
        try {
            return beanFactory.getBeanByClassName(beanClass.getTypeName());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
