package com.dwarfeng.dcti.sdk.util;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;

import javax.annotation.Nonnull;
import java.util.Comparator;

/**
 * 基于发生日期的数据信息比较器。
 *
 * <p>
 * 该比较器仅比较 {@code happenedDate}，不会比较 {@code happenedDateNanoOffset}。
 * 若需要“毫秒 + 毫秒内纳秒偏移”的精确排序，请使用 {@link TimedValueHappenedInstantComparator}。
 *
 * @author DwArFeng
 * @since 1.1.2
 * @deprecated 该比较器仅保留旧行为，请改用 {@link TimedValueHappenedInstantComparator}。
 */
@Deprecated
public class TimedValueHappenedDateComparator implements Comparator<TimedValue> {

    public static final TimedValueHappenedDateComparator INSTANCE = new TimedValueHappenedDateComparator();

    @Override
    public int compare(@Nonnull TimedValue o1, @Nonnull TimedValue o2) {
        return o1.getHappenedDate().compareTo(o2.getHappenedDate());
    }
}
