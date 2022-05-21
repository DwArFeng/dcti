package com.dwarfeng.dcti.sdk.util;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dcti.sdk.bean.dto.FastJsonDataInfo;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;

import javax.annotation.Nonnull;

/**
 * 数据点工具类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DataInfoUtil {

    /**
     * 数据信息转换为文本。
     *
     * @param dataInfo 指定的数据信息。
     * @return 指定的数据信息转换成的文本。
     */
    public static String toMessage(@Nonnull DataInfo dataInfo) {
        FastJsonDataInfo fastJsonDataInfo = FastJsonDataInfo.of(dataInfo);
        return JSON.toJSONString(fastJsonDataInfo);
    }

    /**
     * 文本转换成数据信息。
     *
     * @param message 指定的文本。
     * @return 指定的文本转换成的数据信息。
     */
    public static DataInfo fromMessage(@Nonnull String message) {
        FastJsonDataInfo fastJsonDataInfo = JSON.parseObject(message, FastJsonDataInfo.class);
        return FastJsonDataInfo.toStackBean(fastJsonDataInfo);
    }

    private DataInfoUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
