package com.dwarfeng.dcti.node.configuration;

import com.dwarfeng.dcti.impl.handler.DctiHandlerImpl;
import com.dwarfeng.dcti.impl.handler.DctiQosHandlerImpl;
import com.dwarfeng.dcti.impl.service.DctiQosServiceImpl;
import com.dwarfeng.dcti.sdk.util.Constants;
import com.dwarfeng.dcti.stack.handler.DctiHandler;
import com.dwarfeng.dcti.stack.handler.DctiQosHandler;
import com.dwarfeng.dcti.stack.service.DctiQosService;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 单例模式配置。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
@Configuration
public class SingletonHandlerConfiguration {

    /**
     * DCTI 处理器的 Bean 名称。
     */
    public static final String BEAN_NAME_DCTI_HANDLER = Constants.XSD_DEFAULT_DCTI_HANDLER_NAME;

    @Bean(name = BEAN_NAME_DCTI_HANDLER)
    public DctiHandler dctiHandler() {
        return new DctiHandlerImpl();
    }

    @Bean
    public DctiQosHandler dctiQosHandler(Map<String, DctiHandler> dctiHandlerMap) {
        return new DctiQosHandlerImpl(dctiHandlerMap);
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public DctiQosService dctiQosService(DctiQosHandler dctiQosHandler, ServiceExceptionMapper sem) {
        return new DctiQosServiceImpl(dctiQosHandler, sem);
    }
}
