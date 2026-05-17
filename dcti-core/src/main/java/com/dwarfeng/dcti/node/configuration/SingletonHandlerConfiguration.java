package com.dwarfeng.dcti.node.configuration;

import com.dwarfeng.dcti.impl.handler.DctiHandlerImpl;
import com.dwarfeng.dcti.stack.handler.DctiHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 单例模式配置。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
@Configuration
public class SingletonHandlerConfiguration {

    @Bean
    public DctiHandler dctiHandler() {
        return new DctiHandlerImpl();
    }
}
