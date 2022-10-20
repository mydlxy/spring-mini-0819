package aop.proxy;

import aop.advice.Advice;
import aop.config.MethodAdvice;
import aop.parse.utils.AOPUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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

        AOPUtils.exeAdvice(methodAdvice.getBeforeInterceptor().getBefore(),method);
        Object ret =null;
        try {
             ret = method.invoke(target, args);
             AOPUtils.exeAdvice(methodAdvice.getAfterReturningInterceptor().getAfterReturning(),method);
        }catch (Throwable e){
            Set<Advice> afterThrowing = methodAdvice.getAfterThrowingInterceptor().getAfterThrowing();
            if(afterThrowing.isEmpty())
                throw new RuntimeException(e.getMessage());
            else
                AOPUtils.exeAdvice(afterThrowing,method);
        }
        return ret;
    }




}
