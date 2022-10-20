package ioc.utils;

/**
 * @author myd
 * @date 2022/8/19  12:49
 */

public class TypeConvert {



    /**
     *
     * 在xml中读取的值类型都是String，
     *该方法 将String转换成属性值的类型；在生成bean时，转换成字段的属性；
     * @param value
     * @param type
     * @return
     */
    public static Object simpleTypeConvert(String value,String type){
        switch (type){
            case "java.lang.String":
                return value;
            case "java.lang.Integer":
                return Integer.valueOf(value);
            case "int":
                return Integer.parseInt(value);
            case "java.lang.Boolean":
                return Boolean.valueOf(value);
            case "boolean":
                return Boolean.parseBoolean(value);
            case "java.lang.Short":
                return Short.valueOf(value);
            case "short":
                return Short.parseShort(value);
            case "java.lang.Long":
                return Long.valueOf(value);
            case "long":
                return Long.parseLong(value);
            case "java.lang.Byte":
                return Byte.valueOf(value);
            case "byte":
                return Byte.parseByte(value);
            case "java.lang.Character":
                return Character.valueOf(value.charAt(0));
            case "char":
                return value.charAt(0);
            case "java.lang.Double":
                return Double.valueOf(value);
            case "double":
                return Double.parseDouble(value);
            case "java.lang.Float":
                return Float.valueOf(value);
            case "float":
                return Float.parseFloat(value);
            default:
                throw new IllegalArgumentException("不支持该参数类型的直接赋值："+type);
        }
    }

}
