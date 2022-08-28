package Context;

import aop.config.MethodAdvice;

/**
 * @author myd
 * @date 2022/8/20  15:09
 */

public interface BeanPostProcessor {

    Object postProcessor(Object bean, MethodAdvice methodAdvice) throws Exception;
}
