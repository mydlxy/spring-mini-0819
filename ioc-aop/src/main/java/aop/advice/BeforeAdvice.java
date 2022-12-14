package aop.advice;

import aop.config.Pointcut;

/**
 * @author myd
 * @date 2022/8/22  10:56
 */

public class BeforeAdvice extends Advice {

    public BeforeAdvice(String ref, String type, Pointcut pointcut, String methodName) {
        super(ref, type, pointcut, methodName);
    }
}
