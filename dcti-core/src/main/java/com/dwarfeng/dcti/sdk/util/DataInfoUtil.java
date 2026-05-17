package com.dwarfeng.dcti.sdk.util;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dcti.sdk.bean.dto.FastJsonDataInfo;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dutil.basic.time.TimeUtil;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

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

    /**
     * 将指定数据信息中的发生时间转换为时间点。
     *
     * @param dataInfo 指定的数据信息。
     * @return 指定数据信息中的发生时间表示的时间点。
     * @throws NullPointerException     参数为 {@code null} 或发生时间字段为 {@code null}。
     * @throws IllegalArgumentException 纳秒偏移超出合法范围。
     * @since 2.0.0
     */
    @Nonnull
    public static Instant getHappenedInstant(@Nonnull DataInfo dataInfo) {
        Objects.requireNonNull(dataInfo, "dataInfo 不能为空");
        Date happenedDate = Objects.requireNonNull(dataInfo.getHappenedDate(), "dataInfo.happenedDate 不能为空");
        int happenedDateNanoOffset = dataInfo.getHappenedDateNanoOffset();
        TimeUtil.checkNanoOffset(happenedDateNanoOffset);
        return TimeUtil.toInstant(happenedDate, happenedDateNanoOffset);
    }

    /**
     * 使用指定的发生时间点设置指定数据信息的发生时间。
     *
     * @param dataInfo 指定的数据信息。
     * @param instant  指定的发生时间点。
     * @throws NullPointerException 参数为 {@code null}。
     * @since 2.0.0
     */
    public static void setHappenedInstant(@Nonnull DataInfo dataInfo, @Nonnull Instant instant) {
        Objects.requireNonNull(dataInfo, "dataInfo 不能为空");
        Objects.requireNonNull(instant, "instant 不能为空");
        dataInfo.setHappenedDate(TimeUtil.toDate(instant));
        dataInfo.setHappenedDateNanoOffset(TimeUtil.toNanoOffset(instant));
    }

    /**
     * 根据指定参数构造新的数据信息。
     *
     * @param pointLongId     指定的数据点 ID。
     * @param value           指定的数据值。
     * @param happenedInstant 指定的发生时间点。
     * @return 构造的数据信息。
     * @throws NullPointerException 参数 <code>happenedInstant</code> 为 <code>null</code>。
     * @since 2.0.1
     */
    @Nonnull
    public static DataInfo newInstance(long pointLongId, String value, @Nonnull Instant happenedInstant) {
        Objects.requireNonNull(happenedInstant, "happenedInstant 不能为空");
        return new DataInfo(
                pointLongId, value, TimeUtil.toDate(happenedInstant), TimeUtil.toNanoOffset(happenedInstant)
        );
    }

    private DataInfoUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
