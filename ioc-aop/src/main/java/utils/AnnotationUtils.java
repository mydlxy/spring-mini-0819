package utils;

import bean.BeanDefinition;
import parse.annotation.Autowired;
import parse.annotation.Value;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author myd
 * @date 2022/8/19  18:01
 */

public class AnnotationUtils {

    /**
     * 获取给定路径path下的所有类名；
     *
     * @param path
     * @param classNames
     */
    public static void getAllClassNames(String path,String packageName, List<String> classNames) {
        File file = new File(path);
        if (file.isDirectory()) {
            String[] list = file.list();
            for (String s : list) {
                String childPackageName = (packageName == null || packageName.trim().length() == 0) ? s : packageName +"."+ s;
                String childPath = path+"/"+s;
                getAllClassNames(childPath,childPackageName, classNames);
            }
        } else {
            if (packageName.matches(".+\\.class$"))
                classNames.add(packageName.substring(0, packageName.length() - 6));
        }
    }


    public static List<BeanDefinition> scanAndGetBeanDefinitions(List<String> classNames, Set<Class<? extends Annotation> > scanType) throws ClassNotFoundException {
        if(classNames.isEmpty())return null;
        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        for (String className : classNames) {
            Class scanClass = Class.forName(className);
            Annotation[] annotations = scanClass.getAnnotations();
            for (Annotation annotation : annotations) {
                if(scanType.contains(annotation.annotationType())){
                    getBeanDefinition(className,scanClass,beanDefinitions);
                }
            }
        }
        return beanDefinitions;
    }

    public static void getBeanDefinition(String className,Class beanClass,List<BeanDefinition> beanDefinitions){
        BeanDefinition beanDefinition = new BeanDefinition();
        String beanName = className.substring(0,1).toLowerCase() + className.substring(1);
        beanDefinition.setBeanName(beanName).setBeanClass(beanClass);
        Field[] fields = beanClass.getDeclaredFields();
        //只处理字段的 @Value，@Autowire这2个注解
        for (Field field : fields) {
            Value val = field.getAnnotation(Value.class);
            if(val != null){//@Value 注解只能注入值：原始类型 + 包装类型  + String
                beanDefinition.setFieldValue(field.getName(),val.value());
            }else {// @Autowired注解填入字段的是：fieldName <-> className
                Autowired autowired = field.getAnnotation(Autowired.class);
               if(autowired != null) beanDefinition.setFieldAutowiredRef(field.getName(),field.getType().getTypeName());
            }
        }
        beanDefinitions.add(beanDefinition);
    }


    public static boolean matchSuperOrInterface(Class beanClass,String targetClassName){
        Class superClass = beanClass.getSuperclass();
        while(true){
            if(superClass == null || superClass.getName().equals(Object.class.getName()))break;
            if(beanClass.getSuperclass().getName().equals(targetClassName))return true;
            superClass =superClass.getSuperclass();
        }
        Class[] interfaces = beanClass.getInterfaces();
        if(interfaces == null || interfaces.length ==0 )return false;
        for (Class beanInterface : interfaces) {
            if(beanInterface.getName().equals(targetClassName))return true;
            if(matchSuperOrInterface(beanInterface,targetClassName))return true;
        }
        return false;
    }


}
