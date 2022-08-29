package aop.proxy;

import Context.BeanPostProcessor;
import aop.advice.Advice;
import aop.config.MethodAdvice;
import aop.config.Pointcut;
import aop.config.PointcutUtils;

import java.util.List;
import java.util.Set;


/**
 * @author myd
 * @date 2022/8/28  23:26
 */

public class AopProxy implements BeanPostProcessor {



    private static  AopProxy single = new AopProxy();


    public static AopProxy getInstance(){
        return single;
    }


    public boolean matchClass(Pointcut pointcut,Object bean){

       return PointcutUtils.matchClass(pointcut,bean.getClass()) ;
    }


    public void matchAndAddAdvice(Object bean, Advice advice,MethodAdvice methodAdvice){
        if(matchClass(advice.getPointcut(),bean) )methodAdvice.addAdvice(advice);
    }

    public void getMethodAdvice(Object bean, Set<Advice> advices, MethodAdvice methodAdvice){
        advices.forEach(advice->{
            if(!bean.equals(advice.getAspect()))
                matchAndAddAdvice(bean,advice,methodAdvice);
        });

    }




    public MethodAdvice getMethodAdvice(Object bean, MethodAdvice methodAdvice){

        MethodAdvice beanAdvice = new MethodAdvice();
        getMethodAdvice(bean,methodAdvice.getBeforeInterceptor().getBefore(),beanAdvice);
        getMethodAdvice(bean,methodAdvice.getAfterReturningInterceptor().getAfterReturning(),beanAdvice);
        getMethodAdvice(bean,methodAdvice.getAfterThrowingInterceptor().getAfterThrowing(),beanAdvice);
        return beanAdvice;

    }

    @Override
    public Object postProcessor(Object bean, MethodAdvice methodAdvice) throws Exception {
        MethodAdvice beanAdvice = getMethodAdvice(bean, methodAdvice);
        if(!beanAdvice.isEmpty()){
            if(bean.getClass().getInterfaces().length == 0){//cglibProxy
                CglibProxy cglibProxy = new CglibProxy(bean,beanAdvice);
                bean = cglibProxy.proxy();
            }else{//jdkProxy
                JDKProxy jdkProxy = new JDKProxy(bean,beanAdvice);
                bean = jdkProxy.proxy();
            }
        }

        return bean;
    }
}
