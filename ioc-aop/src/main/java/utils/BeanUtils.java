package utils;

import Context.ConfigInfo;
import bean.BeanReference;
import bean.ConstructorValues;
import bean.PropertyValue;


import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author myd
 * @date 2022/8/20  2:44
 */

public class BeanUtils {
    
    
    
    
    public static Constructor getConstructor(Class beanClass, List<String>paramList){
        Constructor[] constructors = beanClass.getConstructors();
        for (Constructor constructor : constructors) {
            Parameter[] parameters = constructor.getParameters();
            if(parameters.length == paramList.size()
               && compareParamList(constructor,paramList)){
                return constructor;
            }
        }
        throw new RuntimeException("没有找到匹配的构造方法："+beanClass.getName());
        
    }


    public static boolean compareParamList(Constructor constructor,List<String>paramList){
        if(constructor.getParameterCount() != paramList.size())return false;
        Type[] types = constructor.getGenericParameterTypes();
        for (int i = 0; i < types.length; i++) {
            if(!types[i].getTypeName().equals(paramList.get(i)))return false;
        }
        return true;
    }





    public static Object convertTrueTypeValue(String type, String value, ConfigInfo configInfo){
        if(value.matches("\\$\\{.+\\}"))value  = getPropertiesValue(value,configInfo);
        return TypeConvert.simpleTypeConvert(value,type);

    }

    public static String getPropertiesValue(String refPropertiesKey, ConfigInfo configInfo){
        refPropertiesKey = refPropertiesKey.substring(2,refPropertiesKey.length()-1).trim();
        if(refPropertiesKey.equals(""))throw new NullPointerException("@Value引用值为空");
        return configInfo.getPropertiesValue(refPropertiesKey);
    }

    public static String getSimpleName(String className){
        int index = className.lastIndexOf(".");
        String beanName = className.substring(index+1);
        return beanName.substring(0,1).toLowerCase() + beanName.substring(1);
    }


}
