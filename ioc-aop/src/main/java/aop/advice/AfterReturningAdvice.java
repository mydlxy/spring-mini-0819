package aop.advice;

import aop.config.Pointcut;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author myd
 * @date 2022/8/23  11:33
 */

public class AfterReturningAdvice extends Advice {


    public AfterReturningAdvice(String type, Pointcut pointcut, String methodName) {
        super(type, pointcut, methodName);
    }
}
