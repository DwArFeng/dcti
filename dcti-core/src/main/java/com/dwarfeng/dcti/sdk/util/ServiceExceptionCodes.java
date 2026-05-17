package com.dwarfeng.dcti.sdk.util;

import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 服务异常代码。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class ServiceExceptionCodes {

    private static int EXCEPTION_CODE_OFFSET = 22000;

    // 根异常。
    public static final ServiceException.Code DCTI_HANDLER_FAILED =
            new ServiceException.Code(offset(0), "dcti handler failed");
    // DataInfo 序列化/反序列化异常。
    public static final ServiceException.Code DATA_INFO_SERIALIZE_FAILED =
            new ServiceException.Code(offset(1), "data info serialize failed");
    public static final ServiceException.Code DATA_INFO_DESERIALIZE_FAILED =
            new ServiceException.Code(offset(2), "data info deserialize failed");
    // QoS 路由异常。
    public static final ServiceException.Code DCTI_QOS_FAILED =
            new ServiceException.Code(offset(10), "dcti qos failed");
    public static final ServiceException.Code AMBIGUOUS_DCTI_HANDLER =
            new ServiceException.Code(offset(11), "ambiguous dcti handler");
    public static final ServiceException.Code NO_DCTI_HANDLER_PRESENT =
            new ServiceException.Code(offset(12), "no dcti handler present");
    public static final ServiceException.Code DCTI_QOS_HANDLER_NOT_FOUND =
            new ServiceException.Code(offset(13), "dcti qos handler not found");

    private static int offset(int i) {
        return EXCEPTION_CODE_OFFSET + i;
    }

    /**
     * 获取异常代号的偏移量。
     *
     * @return 异常代号的偏移量。
     */
    public static int getExceptionCodeOffset() {
        return EXCEPTION_CODE_OFFSET;
    }

    /**
     * 设置异常代号的偏移量。
     *
     * @param exceptionCodeOffset 指定的异常代号的偏移量。
     */
    public static void setExceptionCodeOffset(int exceptionCodeOffset) {
        // 设置 EXCEPTION_CODE_OFFSET 的值。
        EXCEPTION_CODE_OFFSET = exceptionCodeOffset;

        // 以新的 EXCEPTION_CODE_OFFSET 为基准，更新异常代码的值。
        DCTI_HANDLER_FAILED.setCode(offset(0));
        DATA_INFO_SERIALIZE_FAILED.setCode(offset(1));
        DATA_INFO_DESERIALIZE_FAILED.setCode(offset(2));
        DCTI_QOS_FAILED.setCode(offset(10));
        AMBIGUOUS_DCTI_HANDLER.setCode(offset(11));
        NO_DCTI_HANDLER_PRESENT.setCode(offset(12));
        DCTI_QOS_HANDLER_NOT_FOUND.setCode(offset(13));
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
