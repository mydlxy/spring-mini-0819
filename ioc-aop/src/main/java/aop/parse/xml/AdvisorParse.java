package aop.parse.xml;

import Context.ConfigInfo;
import org.dom4j.Element;
import parse.xml.node.XmlNode;

import java.util.List;

/**
 * @author myd
 * @date 2022/8/21  15:43
 */

public class AdvisorParse implements XmlNode {

    private static final String ASPECT = "aspect";
    private static final String EXPRESSION = "expression";
    private static final String ID = "id";
    private static final String POINTCUT = "pointcut";
    private static final String ADVICE_BEAN_NAME = "adviceBeanName";
    private static final String ADVISOR = "advisor";
    private static final String ADVICE_REF = "advice-ref";
    private static final String POINTCUT_REF = "pointcut-ref";
    private static final String REF = "ref";
    private static final String BEFORE = "before";
    private static final String AFTER = "after";
    private static final String AFTER_RETURNING_ELEMENT = "after-returning";
    private static final String AFTER_THROWING_ELEMENT = "after-throwing";
    private static final String AROUND = "around";
    private static final String RETURNING = "returning";
    private static final String RETURNING_PROPERTY = "returningName";
    private static final String THROWING = "throwing";
    private static final String THROWING_PROPERTY = "throwingName";
    private static final String ARG_NAMES = "arg-names";
    private static final String ARG_NAMES_PROPERTY = "argumentNames";
    private static final String ASPECT_NAME_PROPERTY = "aspectName";
    private static final String DECLARATION_ORDER_PROPERTY = "declarationOrder";
    private static final String ORDER_PROPERTY = "order";
    private static final int METHOD_INDEX = 0;
    private static final int POINTCUT_INDEX = 1;
    private static final int ASPECT_INSTANCE_FACTORY_INDEX = 2;

    /**
     *
     * <aop:config>
     *     <aop:pointcut id="pointcut-1"  expression="xxxxxx"  />
     *      <aop:aspect id="" ref="">
     *          <aop:before pointcut=""  method=""/> 执行targetMethod方法前执行
     *          <aop:after pointcut-ref="pointcut-1"   method=""/>  发生异常也执行finally
     *          <aop:after-returning pointcut="" method="" />执行targetMethod之后执行
     *          <aop:after-throwing pointcut=""  method="" exception="" />发生异常之后执行；
     *          <aop:around pointcut ="" method="" /> 是前几个的集合。。
     *      </aop:aspect>
     * </aop:config>
     */

    @Override
    public void parseLabel(Element node, ConfigInfo configInfo) {

        String aspect = node.attributeValue(REF);//切面，ref：关联的beanID

        List<Element> elements = node.elements();

    }
}
