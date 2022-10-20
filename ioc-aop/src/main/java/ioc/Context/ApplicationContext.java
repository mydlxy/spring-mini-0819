package ioc.Context;

/**
 * @author myd
 * @date 2022/8/19  12:46
 */

public interface ApplicationContext {

     Object getBean(String name);

     Object getBean(Class beanClass);



}
