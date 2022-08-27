package bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author myd
 * @date 2022/8/19  13:10
 */

public class FieldValues {
    List<PropertyValue> propertyValues;
    List<BeanReference> beanReferences;
    public void setValue(String name ,String value){
        if(propertyValues == null){
            propertyValues = new ArrayList<>();
        }
        propertyValues.add(PropertyValue.fieldProperty(name,value));
    }
    public void setRef(String name,String ref){
        if(beanReferences == null){
            beanReferences = new ArrayList<>();
        }
        beanReferences.add(BeanReference.fieldRef(name,ref));
    }
    public void setAutowiredRef(String name,String type){
        if(beanReferences == null){
            beanReferences = new ArrayList<>();
        }
        beanReferences.add(BeanReference.fieldAutowired(name,type));
    }
    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }
    public FieldValues setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
        return this;
    }
    public List<BeanReference> getBeanReferences() {
        return beanReferences;
    }
    public FieldValues setBeanReferences(List<BeanReference> beanReferences) {
        this.beanReferences = beanReferences;
        return this;
    }
}
