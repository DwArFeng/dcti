package com.dwarfeng.dcti.sdk.util;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;

import javax.annotation.Nonnull;
import java.util.Comparator;

/**
 * 基于发生时间的拥有发生时间的数据值比较器。
 *
 * @author DwArFeng
 * @since 1.1.2
 */
public class DataInfoHappenedDateComparator implements Comparator<DataInfo> {

    public static final DataInfoHappenedDateComparator INSTANCE = new DataInfoHappenedDateComparator();

    @Override
    public int compare(@Nonnull DataInfo o1, @Nonnull DataInfo o2) {
        return o1.getHappenedDate().compareTo(o2.getHappenedDate());
    }
}
