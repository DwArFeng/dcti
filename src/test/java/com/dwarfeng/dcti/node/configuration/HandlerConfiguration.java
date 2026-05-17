package com.dwarfeng.dcti.node.configuration;

import com.dwarfeng.dcti.impl.handler.DctiHandlerImpl;
import com.dwarfeng.dcti.stack.handler.DctiHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlerConfiguration {

    @Bean
    public DctiHandler dctiHandler() {
        return new DctiHandlerImpl();
    }
}
