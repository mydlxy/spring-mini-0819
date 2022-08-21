package bean;

/**
 * @author myd
 * @date 2022/8/19  13:15
 */

public class BeanDefinition {

    /*bean的id*/
    String beanName;
    /*bean的class*/
    Class beanClass;
    /*字段值信息*/
    FieldValues fieldValues;
    /*构造参数信息*/
    ConstructorValues constructorValues;
    String scope = "single";
    /**
     * 判断bean注入容器时是否使用：无参构造*/
    public boolean nonParamConstruct(){
        return constructorValues ==null;
    }
    public void setFieldValue(String name,String value){
        if(fieldValues == null){
            fieldValues = new FieldValues();
        }
        fieldValues.setValue(name,value);
    }
    public void setFieldRef(String name,String ref){
        if(fieldValues == null){
            fieldValues = new FieldValues();
        }
        fieldValues.setRef(name,ref);
    }
    public void setFieldAutowiredRef(String name,String type){
        if(fieldValues == null){
            fieldValues = new FieldValues();
        }
        fieldValues.setAutowiredRef(name,type);
    }
    public void setConstructValue(String type,String value){
        if(constructorValues == null){
            constructorValues = new ConstructorValues();
        }
        constructorValues.setValue(type,value);
        //将参数列表顺序添加
        constructorValues.setParamTypeAndValueByOrder(type,value);
    }
    public void setConstructRef(String type,String ref){
        if(constructorValues == null){
            constructorValues = new ConstructorValues();
        }
        constructorValues.setRef(type,ref);
        //将参数列表顺序添加
        constructorValues.setParamTypeAndValueByOrder(type,ref);
    }
    public String getBeanName() {
        return beanName;
    }
    public BeanDefinition setBeanName(String beanName) {
        this.beanName = beanName;
        return this;
    }
    public Class getBeanClass() {
        return beanClass;
    }
    public BeanDefinition setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
        return this;
    }
    public FieldValues getFieldValues() {
        return fieldValues;
    }
    public BeanDefinition setFieldValues(FieldValues fieldValues) {
        this.fieldValues = fieldValues;
        return this;
    }
    public ConstructorValues getConstructorValues() {
        return constructorValues;
    }
    public BeanDefinition setConstructorValues(ConstructorValues constructorValues) {
        this.constructorValues = constructorValues;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public BeanDefinition setScope(String scope) {
        this.scope = scope;
        return this;
    }
}
