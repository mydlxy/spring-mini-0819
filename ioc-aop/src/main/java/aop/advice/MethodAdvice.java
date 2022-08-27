package aop.advice;

/**
 * @author myd
 * @date 2022/8/23  12:44
 */

public class MethodAdvice {

    BeforeInterceptor beforeInterceptor;

    AfterReturningInterceptor afterReturningInterceptor;

    AfterThrowingInterceptor afterThrowingInterceptor;

    public BeforeInterceptor getBeforeInterceptor() {
        return beforeInterceptor;
    }

    public MethodAdvice setBeforeInterceptor(BeforeInterceptor beforeInterceptor) {
        this.beforeInterceptor = beforeInterceptor;
        return this;
    }

    public AfterReturningInterceptor getAfterReturningInterceptor() {
        return afterReturningInterceptor;
    }

    public MethodAdvice setAfterReturningInterceptor(AfterReturningInterceptor afterReturningInterceptor) {
        this.afterReturningInterceptor = afterReturningInterceptor;
        return this;
    }

    public AfterThrowingInterceptor getAfterThrowingInterceptor() {
        return afterThrowingInterceptor;
    }

    public MethodAdvice setAfterThrowingInterceptor(AfterThrowingInterceptor afterThrowingInterceptor) {
        this.afterThrowingInterceptor = afterThrowingInterceptor;
        return this;
    }
}
