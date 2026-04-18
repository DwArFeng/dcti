package com.dwarfeng.dcti.sdk.util;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.dutil.basic.time.TimeUtil;

import javax.annotation.Nonnull;
import java.util.Comparator;

/**
 * 基于发生时间点（日期 + 毫秒内纳秒偏移）的数据信息比较器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class TimedValueHappenedInstantComparator implements Comparator<TimedValue> {

    public static final TimedValueHappenedInstantComparator INSTANCE = new TimedValueHappenedInstantComparator();

    @Override
    public int compare(@Nonnull TimedValue o1, @Nonnull TimedValue o2) {
        return TimeUtil.compare(
                o1.getHappenedDate(), o1.getHappenedDateNanoOffset(),
                o2.getHappenedDate(), o2.getHappenedDateNanoOffset()
        );
    }
}
