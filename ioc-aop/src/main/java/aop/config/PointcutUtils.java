package aop.config;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Ref;

/**
 * @author myd
 * @date 2022/8/22  10:15
 */

public class PointcutUtils {

    private static final String ACCESS_MODIFIER = "(public|private|protected|default|\\*)";

    private static final String RETURN_TYPE;

//    private static final String CLASS_PATH = "((\\*?\\w+\\*?|\\*)\\.)*(\\*?\\w+\\*?|\\*)";
    private static final String CLASS_PATH = "((\\*?\\w+\\*?|\\*)\\.)*(\\*?\\w+\\*?|\\*)(\\.\\.)?";
    private static final String METHOD_NAME = "(\\*?\\w+\\*?|\\*)";

    private static final String PARAM_LIST;

    private static final String EXECUTION;
    //基础类型
    private static final String BASIC_TYPE = "(byte|char|boolean|short|int|long|double|float)";
    //参数类型
    private static final String PARAM_TYPE;
    static {
        //参数类型：基础类型 + Object + 数组类型
        PARAM_TYPE = "(" + BASIC_TYPE + "|[A-Z]\\w*)(\\[\\])*";
        //返回值类型：void  + 参数类型
        RETURN_TYPE = "(void|" + PARAM_TYPE + "|\\*)";
        //参数列表
        PARAM_LIST = "(\\.\\.|" + PARAM_TYPE + "(\\s*,\\s*" + PARAM_TYPE + ")*" + "|)";
        //execution表达式
        EXECUTION = "\\s*execution\\s*\\(\\s*" + ACCESS_MODIFIER + "\\s+" + RETURN_TYPE + "\\s+" + CLASS_PATH + "\\s+" + METHOD_NAME + "\\(\\s*" + PARAM_LIST + "\\s*\\)\\s*" + "\\s*\\)\\s*";

    }
    static boolean checkPointcut(String pointcutReg) {
        return pointcutReg == null ? false : pointcutReg.matches(EXECUTION);
    }
    public static Pointcut parsePointcut(String pointcut) {
        if (!checkPointcut(pointcut)) throw new IllegalArgumentException("execution grammar format error.");
        String exeReg = getBracketStr(pointcut);//获取execution();中括号包裹的部分；
        String paramList = getBracketStr(exeReg).replaceAll(" ", "");
        int start = exeReg.indexOf("(");
        exeReg = exeReg.substring(0, start);
        String[] regs = exeReg.split("\\s+");

        String accessModifier = regs[0];
        String returnType = regs[1];
        String classPath = regs[2];
        String methodName = regs[3];

        return new Pointcut(accessModifier, returnType, classPath, methodName, paramList);
    }
    static String getBracketStr(String str) {
        int start = str.indexOf("(");
        int end = str.lastIndexOf(")");
        return str.substring(start + 1, end).trim();
    }
    public static boolean matchClass(Pointcut pointcut, Class cla) {
        if (pointcut.getClassPath().equals("*")) return true;
        String regex = pointcut.getClassPath().replaceAll("\\*", "\\\\w*");
        if(regex.endsWith("..")){
            regex = regex.substring(0,regex.length()-2);
            regex = regex.replaceAll("\\.","\\\\.");//将 "." ==> "\."
            regex+=".*";
        }else{
            regex = regex.replaceAll("\\.", "\\\\.");
        }
        return cla.getTypeName().matches(regex);
//        return cla.getTypeName().matches(pointcut.getClassPath().replaceAll("\\*", "\\\\w*").replaceAll("\\.", "\\\\."));
    }
    public static boolean matchMethod(Pointcut pointcut, Method method) {
        return matchModifier(pointcut.getAccessModifier(), method) &&
                matchReturnType(pointcut.getReturnType(), method) &&
                matchMethodName(pointcut.getMethodName(), method) &&
                matchParamList(pointcut.getParamList(), method);
    }
    static boolean matchModifier(String modifier, Method method) {
        int modifiers = method.getModifiers();
        switch (modifier) {
            case "default":
                return method.isDefault();
            case "public":
                return Modifier.isPublic(modifiers);
            case "private":
                return Modifier.isPrivate(modifiers);
            case "protected":
                return Modifier.isProtected(modifiers);
            case "*":
                return true;
            default:
                return false;
        }
    }
    static boolean matchReturnType(String returnType, Method method) {
        if (returnType.equals("*")) return true;
        return returnType.equals(method.getReturnType().getSimpleName());
    }
    static boolean matchMethodName(String name, Method method) {
        if (name.equals("*")) return true;
        return method.getName().matches(name.replaceAll("\\*", "\\\\w*"));
    }
    static boolean matchParamList(String paramList, Method method) {

        if (paramList.equals("..")) return true;

        Class<?>[] parameterTypes = method.getParameterTypes();
        if ((paramList.equals(""))) {
            if (parameterTypes.length == 0) return true;
            else return false;
        }
        StringBuilder methodParamList = new StringBuilder();
        for (Class<?> parameterType : parameterTypes) {
            methodParamList.append("," + parameterType.getSimpleName());
        }
        String methodParam = methodParamList.toString().substring(1);
        return methodParam.equals(paramList);
    }


}
