package ioc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author myd
 * @date 2022/8/19  13:11
 */

public class ConstructorValues {

    List<String> paramTypeListOrder;
    List<String> paramValueListOrder;
    List<PropertyValue> propertyValues;
    List<BeanReference> beanReferences;
    public void setValue(String type,String value){
        if(propertyValues== null){
            propertyValues =  new ArrayList<>();
        }
        propertyValues.add(PropertyValue.constructProperty(type,value));
    }
    public void setRef(String type,String ref){
        if(beanReferences == null){
            beanReferences = new ArrayList<>();
        }
        beanReferences.add(BeanReference.constructRef(type,ref));
    }
    public List<String> getParamTypeListOrder() {
        return paramTypeListOrder;
    }

    public void setParamTypeByOrder(String typeOrder){
        if(paramTypeListOrder == null)paramTypeListOrder = new ArrayList<>();
        paramTypeListOrder.add(typeOrder);
    }

    public void setParamValueByOrder(String value){
        if(paramValueListOrder == null)paramValueListOrder = new ArrayList<>();
        paramValueListOrder.add(value);
    }

    public void setParamTypeAndValueByOrder(String type,String value){
        setParamTypeByOrder(type);
        setParamValueByOrder(value);
    }
    public ConstructorValues setParamTypeListOrder(List<String> paramTypeListOrder) {
        this.paramTypeListOrder = paramTypeListOrder;
        return this;
    }
    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public List<String> getParamValueListOrder() {
        return paramValueListOrder;
    }

    public ConstructorValues setParamValueListOrder(List<String> paramValueListOrder) {
        this.paramValueListOrder = paramValueListOrder;
        return this;
    }

    public ConstructorValues setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
        return this;
    }

    public List<BeanReference> getBeanReferences() {
        return beanReferences;
    }

    public ConstructorValues setBeanReferences(List<BeanReference> beanReferences) {
        this.beanReferences = beanReferences;
        return this;
    }
}
