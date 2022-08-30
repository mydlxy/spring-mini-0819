package aop.advice;

import aop.config.Pointcut;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author myd
 * @date 2022/8/22  10:30
 */

public class Advice {

    public static final String BEFORE = "before";
    public static final String AFTER = "after";
    public static final String AFTER_RETURNING = "afterReturning";
    public static final String AFTER_THROWING = "afterThrowing";
    public static final String AROUND = "around";

    String type;

    Pointcut pointcut;

    String methodName;

    Method method;

    Object aspect;

    String ref;


    @Override
    public int hashCode() {
        int h = 0;
        h = h*31 + pointcut.hashCode();
        h = h*31 + method.hashCode();
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        Advice  advice = (Advice) obj;
        return  advice.getPointcut().equals(pointcut)&&
                advice.getMethod().equals(method);
    }

    public void advice() throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        method.invoke(aspect,null);
    }




    public Advice(String ref,String type,Pointcut pointcut, String methodName){
        this(ref,type,pointcut,methodName,null,null);
    }

    public Advice(String ref,String type,Pointcut pointcut, String methodName, Method method, Object aspect) {
        this.ref =ref;
        this.type = type;
        this.pointcut = pointcut;
        this.methodName = methodName;
        this.method = method;
        this.aspect = aspect;
    }

    public Pointcut getPointcut() {
        return pointcut;
    }

    public Advice setPointcut(Pointcut pointcut) {
        this.pointcut = pointcut;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public Advice setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Method getMethod() {
        return method;
    }

    public Advice setMethod(Method method) {
        this.method = method;
        return this;
    }

    public Object getAspect() {
        return aspect;
    }

    public Advice setAspect(Object aspect) {
        this.aspect = aspect;
        return this;
    }

    public String getType() {
        return type;
    }

    public Advice setType(String type) {
        this.type = type;
        return this;
    }

    public String getRef() {
        return ref;
    }

    public Advice setRef(String ref) {
        this.ref = ref;
        return this;
    }
}
