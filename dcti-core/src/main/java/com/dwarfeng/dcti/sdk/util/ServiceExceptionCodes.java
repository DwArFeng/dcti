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
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
