package com.dwarfeng.dcti.sdk.util;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;

import javax.annotation.Nonnull;
import java.util.Comparator;

/**
 * 基于发生日期的拥有发生时间的数据值比较器。
 *
 * <p>
 * 该比较器仅比较 {@code happenedDate}，不会比较 {@code happenedDateNanoOffset}。
 * 若需要“毫秒 + 毫秒内纳秒偏移”的精确排序，请使用 {@link DataInfoHappenedInstantComparator}。
 *
 * @author DwArFeng
 * @since 1.1.2
 * @deprecated 该比较器仅保留旧行为，请改用 {@link DataInfoHappenedInstantComparator}。
 */
@Deprecated
public class DataInfoHappenedDateComparator implements Comparator<DataInfo> {

    public static final DataInfoHappenedDateComparator INSTANCE = new DataInfoHappenedDateComparator();

    @Override
    public int compare(@Nonnull DataInfo o1, @Nonnull DataInfo o2) {
        return o1.getHappenedDate().compareTo(o2.getHappenedDate());
    }
}
