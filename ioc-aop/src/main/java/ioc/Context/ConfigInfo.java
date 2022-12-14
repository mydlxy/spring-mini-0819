package ioc.Context;

import aop.advice.Advice;
import ioc.bean.BeanDefinition;
import ioc.utils.AnnotationUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author myd
 * @date 2022/8/19  16:07
 */

public class ConfigInfo {

    private Map<String , List<Advice>> aspects = new HashMap<>();

    private ConcurrentHashMap<String , BeanDefinition>beanDefinitions = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String , Properties>    propertiesMap   = new ConcurrentHashMap<>();

    public void registerBean(String name,BeanDefinition beanDefinition){
        BeanDefinition returnDef = beanDefinitions.putIfAbsent(name, beanDefinition);
        if(returnDef != null)throw new RuntimeException("bean id重复，id="+name);
    }


    public BeanDefinition getBeanDefinitionByClassName(String className){

        int count=0;
        BeanDefinition ret=null;
        for (BeanDefinition beanDefinition : beanDefinitions.values()) {
            if(beanDefinition.getBeanClass().getTypeName().equals(className) ||
                    AnnotationUtils.matchSuperOrInterface(beanDefinition.getBeanClass(),className)){
                if(count==0){
                    ret = beanDefinition;
                    count=1;
                }else
                    throw new RuntimeException("类型为 :"+className+" 的对象在容器中注册了多个，无法通过类型找到确定对象");
             }
        }
        if(ret == null)
            throw new RuntimeException("该类型对象在容器中不存在:"+className);
        return ret;
    }

    /*
    * 首先匹配文件名 xxx.name ===> xxx  name
    * ===>    properties = get(xxx);
    * ===> value =  properties.get(name);
    * if(value == null) ==> 就以 xxx.name为key，搜索所有properties文件
     * */
    public String getPropertiesValue(String key){

        if(propertiesMap.isEmpty())
            throw new NullPointerException("没有加载 properties 文件。不能解析@Value引用：${"+key+"}");


        if(key.matches(".*\\..*")){
            int index = key.indexOf('.');
            String propertiesName = key.substring(0,index);
            String key1 = key.substring(index+1);
            Properties properties = propertiesMap.get(propertiesName);
            if(properties !=null){
                Object value =  properties.get(key1);
                if(value!=null)return (String)value;
            }
        }



        for (Properties properties : propertiesMap.values()) {
            Object value =  properties.get(key);
            if(value != null)return (String)value;
        }
        throw new RuntimeException("properties中没有定义key："+key);

    }

    public BeanDefinition getBeanDefinitionByName(String name){
        BeanDefinition beanDefinition = beanDefinitions.get(name);
        if(beanDefinition == null)
            throw new RuntimeException("找不到 id="+name);
        return beanDefinition;
    }

    public void addProperties(String propertiesFileName,Properties properties){
        Properties returnPro = propertiesMap.putIfAbsent(propertiesFileName, properties);
        if(returnPro != null)throw new RuntimeException("properties 文件名重复，properties ="+propertiesFileName);
    }


    public  ConcurrentHashMap<String , BeanDefinition> getBeanDefinitions(){
        return beanDefinitions;
    }


    public Map<String, List<Advice>> getAspect() {
        return aspects;
    }

    public void addAspect(String id,List<Advice> aspect){
        if(aspects.containsKey(id))
            throw new RuntimeException("aspect id repeat :"+id);

        aspects.put(id,aspect);
    }







    public ConfigInfo setAspect(Map<String, List<Advice>> aspect) {
        this.aspects = aspect;
        return this;
    }
}
