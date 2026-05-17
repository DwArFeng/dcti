package com.dwarfeng.dcti.node.configuration;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * DCTI 命名空间处理器。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DctiNamespaceHandlerSupport extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("handler", new DctiHandlerDefinitionParser());
        registerBeanDefinitionParser("qos", new DctiQosDefinitionParser());
    }
}
