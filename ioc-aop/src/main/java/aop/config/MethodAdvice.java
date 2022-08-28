package aop.config;

import aop.advice.Advice;
import aop.advice.AfterReturningInterceptor;
import aop.advice.AfterThrowingInterceptor;
import aop.advice.BeforeInterceptor;

/**
 * @author myd
 * @date 2022/8/23  12:44
 */

public class MethodAdvice {

    BeforeInterceptor beforeInterceptor;

    AfterReturningInterceptor afterReturningInterceptor;

    AfterThrowingInterceptor afterThrowingInterceptor;





    public boolean isEmpty(){
       return  beforeInterceptor.getBefore().isEmpty() &&
               afterThrowingInterceptor.getAfterThrowing().isEmpty() &&
               afterReturningInterceptor.getAfterReturning().isEmpty();
    }


    public MethodAdvice() {
        beforeInterceptor = new BeforeInterceptor();
        afterReturningInterceptor = new AfterReturningInterceptor();
        afterThrowingInterceptor = new AfterThrowingInterceptor();
    }

    public void addAdvice(Advice advice){
        switch (advice.getType()){

           case Advice.AFTER :addAfter(advice);
                break;
            case Advice.AFTER_RETURNING :addAfterReturning(advice);
                break;
            case Advice.AFTER_THROWING : addAfterThrowing(advice);
                break;
            case Advice.AROUND : addAround(advice);
                break;
            case Advice.BEFORE : addBefore(advice);
                break;
            default:
                throw new IllegalArgumentException("advice type error"+advice.getType());
        }

    }

    public void addAfterThrowing(Advice advice){
        afterThrowingInterceptor.add(advice);
    }

    public void addBefore(Advice advice){
        beforeInterceptor.add(advice);
    }

    public void addAfterReturning(Advice advice){
        afterReturningInterceptor.add(advice);
    }

    public void addAfter(Advice advice){
        afterReturningInterceptor.add(advice);
        afterThrowingInterceptor.add(advice);
    }

    public void addAround(Advice advice){
        beforeInterceptor.add(advice);
        afterReturningInterceptor.add(advice);
        afterThrowingInterceptor.add(advice);
    }








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
