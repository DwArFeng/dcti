package com.dwarfeng.dcti.sdk.util;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * 基于发生日期的拥有发生时间的数据值比较器测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DataInfoHappenedDateComparatorTest {

    @Deprecated
    @Test
    public void testCompare() {
        DataInfo dataInfo1 = new DataInfo(1L, "v1", new Date(1000L), 100);
        DataInfo dataInfo2 = new DataInfo(2L, "v2", new Date(1000L), 200);
        DataInfo dataInfo3 = new DataInfo(3L, "v3", new Date(1001L), 0);

        Assert.assertEquals(0, DataInfoHappenedDateComparator.INSTANCE.compare(dataInfo1, dataInfo2));
        Assert.assertTrue(DataInfoHappenedDateComparator.INSTANCE.compare(dataInfo2, dataInfo3) < 0);
    }
}
