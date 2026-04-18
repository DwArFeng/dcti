package com.dwarfeng.dcti.sdk.util;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.dutil.basic.time.TimeUtil;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

/**
 * 拥有发生时间的数据值工具类。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class TimedValueUtil {

    /**
     * 将指定数据值中的发生时间转换为时间点。
     *
     * @param timedValue 指定的数据值。
     * @return 指定数据值中的发生时间表示的时间点。
     * @throws NullPointerException     参数为 {@code null} 或发生时间字段为 {@code null}。
     * @throws IllegalArgumentException 纳秒偏移超出合法范围。
     */
    @Nonnull
    public static Instant getHappenedInstant(@Nonnull TimedValue timedValue) {
        Objects.requireNonNull(timedValue, "timedValue 不能为空");
        Date happenedDate = Objects.requireNonNull(timedValue.getHappenedDate(), "timedValue.happenedDate 不能为空");
        int happenedDateNanoOffset = timedValue.getHappenedDateNanoOffset();
        TimeUtil.checkNanoOffset(happenedDateNanoOffset);
        return TimeUtil.toInstant(happenedDate, happenedDateNanoOffset);
    }

    /**
     * 使用指定的发生时间点设置指定数据值的发生时间。
     *
     * @param timedValue 指定的数据值。
     * @param instant    指定的发生时间点。
     * @throws NullPointerException 参数为 {@code null}。
     */
    public static void setHappenedInstant(@Nonnull TimedValue timedValue, @Nonnull Instant instant) {
        Objects.requireNonNull(timedValue, "timedValue 不能为空");
        Objects.requireNonNull(instant, "instant 不能为空");
        timedValue.setHappenedDate(TimeUtil.toDate(instant));
        timedValue.setHappenedDateNanoOffset(TimeUtil.toNanoOffset(instant));
    }

    private TimedValueUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
