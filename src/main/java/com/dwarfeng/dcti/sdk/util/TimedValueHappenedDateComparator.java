package com.dwarfeng.dcti.sdk.util;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;

import javax.annotation.Nonnull;
import java.util.Comparator;

/**
 * 基于发生时间的数据信息比较器。
 *
 * @author DwArFeng
 * @since 1.1.2
 */
public class TimedValueHappenedDateComparator implements Comparator<TimedValue> {

    public static final TimedValueHappenedDateComparator INSTANCE = new TimedValueHappenedDateComparator();

    @Override
    public int compare(@Nonnull TimedValue o1, @Nonnull TimedValue o2) {
        return o1.getHappenedDate().compareTo(o2.getHappenedDate());
    }
}
