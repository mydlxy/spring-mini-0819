package bean;

/**
 * @author myd
 * @date 2022/8/19  12:53
 */

public class BeanReference {

    /*field 字段的引用类型
    *
    * Object  field1;
    *
    * Object 是被引用的类型：
    * 要在xml中配置bean，或者在注解中注解注入到容器
    * ref是bean的id
    * 在xml中配置时<property  name="field1"  ref=""/>
    *
    * */
    String name;

    String type;

    /**
     *1. 在xml中配置的都会有ref，根据ref引用的id在容器中找到引用的类；
     *
     * 2.在使用autowired注解时，是根据class信息来找匹配的类型
     * 因此ref没有用：ref=null；
     *
     */
    String ref;


    boolean autowired;

    public BeanReference(){}

    public static BeanReference fieldRef(String name,String ref){
        return new BeanReference().setName(name).setRef(ref);
    }
    public static BeanReference constructRef(String type,String ref){
        return new BeanReference().setType(type).setRef(ref);
    }


    /**
     * @param name  fieldName
     * @param type  引用类型
     * @return
     */
    public static BeanReference fieldAutowired(String name,String type){
        return new BeanReference().setName(name).setType(type).setAutowired(true);
    }

    public String getName() {
        return name;
    }
    public BeanReference setName(String name) {
        this.name = name;
        return this;
    }
    public String getRef() {
        return ref;
    }
    public BeanReference setRef(String ref) {
        this.ref = ref;
        return this;
    }
    public String getType() {
        return type;
    }
    public BeanReference setType(String type) {
        this.type = type;
        return this;
    }

    public boolean isAutowired() {
        return autowired;
    }

    public BeanReference setAutowired(boolean autowired) {
        this.autowired = autowired;
        return this;
    }
}
