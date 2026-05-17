package com.dwarfeng.dcti.sdk.util;

/**
 * 常量类。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class Constants {

    // region XSD 默认值

    public static final String XSD_DEFAULT_DCTI_HANDLER_NAME = "dctiHandler";
    public static final String XSD_DEFAULT_DCTI_QOS_HANDLER_NAME = "dctiQosHandler";
    public static final String XSD_DEFAULT_DCTI_QOS_SERVICE_NAME = "dctiQosService";
    public static final String XSD_DEFAULT_SERVICE_EXCEPTION_MAPPER_NAME = "mapServiceExceptionMapper";

    // endregion

    private Constants() {
        throw new IllegalStateException("禁止实例化");
    }
}
