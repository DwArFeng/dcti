package com.dwarfeng.dcti.node.configuration;

import com.dwarfeng.dcti.impl.handler.DctiHandlerImpl;
import com.dwarfeng.dcti.sdk.util.BeanDefinitionParserUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

/**
 * DCTI Handler 元素的 BeanDefinitionParser。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DctiHandlerDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, @Nonnull ParserContext parserContext) {
        String handlerName = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("handler-name")
        );

        BeanDefinitionParserUtil.makeSureBeanNameNotDuplicated(parserContext, handlerName);

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(DctiHandlerImpl.class);
        builder.setScope(BeanDefinition.SCOPE_SINGLETON);
        builder.setLazyInit(false);
        parserContext.getRegistry().registerBeanDefinition(handlerName, builder.getBeanDefinition());

        return null;
    }
}
