package com.dwarfeng.dcti.sdk.util;

import com.dwarfeng.dcti.stack.exception.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 异常的帮助工具类。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class ServiceExceptionHelper {

    /**
     * 向指定的映射中添加 dcti 默认的目标映射。
     *
     * <p>
     * 该方法可以在配置类中快速的搭建目标映射。
     *
     * @param map 指定的映射，允许为 null。
     * @return 添加了默认目标的映射。
     */
    public static Map<Class<? extends Exception>, ServiceException.Code> putDefaultDestination(
            Map<Class<? extends Exception>, ServiceException.Code> map
    ) {
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }

        map.put(DctiException.class, ServiceExceptionCodes.DCTI_HANDLER_FAILED);
        map.put(DataInfoSerializeFailedException.class, ServiceExceptionCodes.DATA_INFO_SERIALIZE_FAILED);
        map.put(DataInfoDeserializeFailedException.class, ServiceExceptionCodes.DATA_INFO_DESERIALIZE_FAILED);
        map.put(DctiQosException.class, ServiceExceptionCodes.DCTI_QOS_FAILED);
        map.put(AmbiguousDctiHandlerException.class, ServiceExceptionCodes.AMBIGUOUS_DCTI_HANDLER);
        map.put(NoDctiHandlerPresentException.class, ServiceExceptionCodes.NO_DCTI_HANDLER_PRESENT);
        map.put(DctiHandlerNotFoundException.class, ServiceExceptionCodes.DCTI_QOS_HANDLER_NOT_FOUND);

        return map;
    }

    private ServiceExceptionHelper() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
