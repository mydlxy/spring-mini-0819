package ioc.bean;

/**
 * @author myd
 * @date 2022/8/19  12:46
 */

public class PropertyValue {

    /*
    * <property name="rrr" value/>
    * */
    private  String  name;
    /*Constructor 构造参数的param的引用类型
     * <constructor  type="java.lang.String"   value="" />
     * */
    private String type;
    private  String value;
    public PropertyValue(){}
    public static PropertyValue constructProperty(String type,String value){
        return new PropertyValue().setType(type).setValue(value);
    }
    public static PropertyValue fieldProperty(String name,String value){
        return new PropertyValue().setName(name).setValue(value);
    }
    public PropertyValue setName(String name) {
        this.name = name;
        return this;
    }
    public PropertyValue setType(String type) {
        this.type = type;
        return this;
    }
    public PropertyValue setValue(String value) {
        this.value = value;
        return this;
    }
    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }
}
