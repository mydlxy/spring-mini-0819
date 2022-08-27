package aop.config;

/**
 * @author myd
 * @date 2022/8/21  15:39
 */

public class Pointcut {


    private String accessModifier;

    private String returnType;

    private String classPath;

    private String methodName;

    private String paramList;

    public Pointcut(String accessModifier, String returnType, String classPath, String methodName, String paramList) {
        this.accessModifier = accessModifier;
        this.returnType = returnType;
        this.classPath = classPath;
        this.methodName = methodName;
        this.paramList = paramList;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public Pointcut setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
        return this;
    }

    public String getReturnType() {
        return returnType;
    }

    public Pointcut setReturnType(String returnType) {
        this.returnType = returnType;
        return this;
    }

    public String getClassPath() {
        return classPath;
    }

    public Pointcut setClassPath(String classPath) {
        this.classPath = classPath;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public Pointcut setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public String getParamList() {
        return paramList;
    }

    public Pointcut setParamList(String paramList) {
        this.paramList = paramList;
        return this;
    }
}
