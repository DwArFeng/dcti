package com.dwarfeng.dcti.sdk.util;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dutil.basic.time.TimeUtil;

import javax.annotation.Nonnull;
import java.util.Comparator;

/**
 * 基于发生时间点（日期 + 毫秒内纳秒偏移）的拥有发生时间的数据值比较器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DataInfoHappenedInstantComparator implements Comparator<DataInfo> {

    public static final DataInfoHappenedInstantComparator INSTANCE = new DataInfoHappenedInstantComparator();

    @Override
    public int compare(@Nonnull DataInfo o1, @Nonnull DataInfo o2) {
        return TimeUtil.compare(
                o1.getHappenedDate(), o1.getHappenedDateNanoOffset(),
                o2.getHappenedDate(), o2.getHappenedDateNanoOffset()
        );
    }
}
