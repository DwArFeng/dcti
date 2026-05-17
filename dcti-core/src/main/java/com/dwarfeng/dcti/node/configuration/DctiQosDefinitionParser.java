package com.dwarfeng.dcti.node.configuration;

import com.dwarfeng.dcti.impl.handler.DctiQosHandlerImpl;
import com.dwarfeng.dcti.impl.service.DctiQosServiceImpl;
import com.dwarfeng.dcti.sdk.util.BeanDefinitionParserUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

/**
 * DCTI Qos 元素的 BeanDefinitionParser。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DctiQosDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, @Nonnull ParserContext parserContext) {
        String qosHandlerName = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("qos-handler-name")
        );
        String qosServiceName = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("qos-service-name")
        );
        String semRef = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("sem-ref")
        );

        BeanDefinitionParserUtil.makeSureBeanNameNotDuplicated(parserContext, qosHandlerName);
        BeanDefinitionParserUtil.makeSureBeanNameNotDuplicated(parserContext, qosServiceName);

        BeanDefinitionBuilder dctiQosHandlerBuilder = BeanDefinitionBuilder.rootBeanDefinition(
                DctiQosHandlerImpl.class
        );
        dctiQosHandlerBuilder.getRawBeanDefinition().setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
        dctiQosHandlerBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
        dctiQosHandlerBuilder.setLazyInit(false);
        parserContext.getRegistry().registerBeanDefinition(qosHandlerName, dctiQosHandlerBuilder.getBeanDefinition());

        BeanDefinitionBuilder dctiQosServiceBuilder = BeanDefinitionBuilder.rootBeanDefinition(
                DctiQosServiceImpl.class
        );
        dctiQosServiceBuilder.getRawBeanDefinition().setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
        ConstructorArgumentValues dctiQosServiceConstructorArgumentValues = new ConstructorArgumentValues();
        dctiQosServiceConstructorArgumentValues.addIndexedArgumentValue(0, new RuntimeBeanReference(qosHandlerName));
        dctiQosServiceConstructorArgumentValues.addIndexedArgumentValue(1, new RuntimeBeanReference(semRef));
        dctiQosServiceBuilder.getRawBeanDefinition().setConstructorArgumentValues(
                dctiQosServiceConstructorArgumentValues
        );
        dctiQosServiceBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
        dctiQosServiceBuilder.setLazyInit(false);
        parserContext.getRegistry().registerBeanDefinition(qosServiceName, dctiQosServiceBuilder.getBeanDefinition());

        return null;
    }
}
