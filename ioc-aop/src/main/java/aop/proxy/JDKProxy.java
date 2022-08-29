package aop.proxy;

import Context.BeanPostProcessor;
import aop.advice.Advice;
import aop.advice.AfterReturningInterceptor;
import aop.config.MethodAdvice;
import aop.config.PointcutUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Set;

/**
 * @author myd
 * @date 2022/8/28  15:47
 */

public class JDKProxy implements  InvocationHandler {

    Object target;
    MethodAdvice methodAdvice;

    public  JDKProxy(Object target,MethodAdvice methodAdvice){
        this.target = target;
        this.methodAdvice = methodAdvice;
    }



    public Object proxy() throws Exception {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        exeAdvice(methodAdvice.getBeforeInterceptor().getBefore(),method);
        Object ret =null;
        try {
             ret = method.invoke(target, args);
            exeAdvice(methodAdvice.getAfterReturningInterceptor().getAfterReturning(),method);
        }catch (Throwable e){
            Set<Advice> afterThrowing = methodAdvice.getAfterThrowingInterceptor().getAfterThrowing();
            if(afterThrowing.isEmpty())
                throw new RuntimeException(e.getMessage());
            else
                exeAdvice(afterThrowing,method);
        }
        return ret;
    }


    public void exeAdvice(Set<Advice> advices, Method method) throws InvocationTargetException, IllegalAccessException {
        if(!advices.isEmpty()) {
            for (Advice advice : advices)
                if(PointcutUtils.matchMethod(advice.getPointcut(),method))
                    advice.advice();
        }
    }


}
