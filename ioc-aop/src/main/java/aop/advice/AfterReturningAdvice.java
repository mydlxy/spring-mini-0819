package aop.advice;

import aop.config.Pointcut;

import java.lang.reflect.Method;

/**
 * @author myd
 * @date 2022/8/23  11:33
 */

public class AfterReturningAdvice implements Advice{

    private static final String AFTER_RETURNING = "after-returning";

    Pointcut pointcut;

    String methodName;

    Method afterReturning;








}
