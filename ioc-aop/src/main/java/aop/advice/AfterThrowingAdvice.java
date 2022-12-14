package aop.advice;

import aop.config.Pointcut;

import java.lang.reflect.InvocationTargetException;

/**
 * @author myd
 * @date 2022/8/23  11:33
 */

public class AfterThrowingAdvice extends Advice {


    public AfterThrowingAdvice(String ref, String type, Pointcut pointcut, String methodName) {
        super(ref, type, pointcut, methodName);
    }
}
