package aop.proxy;

import Context.BeanPostProcessor;
import aop.advice.Advice;
import aop.config.MethodAdvice;
import aop.config.PointcutUtils;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * @author myd
 * @date 2022/8/28  23:49
 */

public class CglibProxy  {

    Object target;
    MethodAdvice methodAdvice;

    CglibProxy(Object target,MethodAdvice methodAdvice){
        this.target = target;
        this.methodAdvice = methodAdvice;
    }





    public Object proxy() throws Exception {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

                exeAdvice(methodAdvice.getBeforeInterceptor().getBefore(),method);
                Object ret = null;
                try {
                     ret = proxy.invokeSuper(obj, args);
                    exeAdvice(methodAdvice.getAfterReturningInterceptor().getAfterReturning(),method);
                }catch (Throwable e){
                    List<Advice> afterThrowing = methodAdvice.getAfterThrowingInterceptor().getAfterThrowing();
                   if(afterThrowing.isEmpty())
                       throw new RuntimeException(e.getMessage());
                   else
                    exeAdvice(afterThrowing,method);
//
                }
                return ret;
            }
        });
        return enhancer.create();
    }


    public void exeAdvice(List<Advice> advices, Method method) throws InvocationTargetException, IllegalAccessException {
        if(!advices.isEmpty()) {
            for (Advice advice : advices)
                if(PointcutUtils.matchMethod(advice.getPointcut(),method))
                    advice.advice();
        }
    }

}
