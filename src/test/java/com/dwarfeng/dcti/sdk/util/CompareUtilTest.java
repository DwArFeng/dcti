package com.dwarfeng.dcti.sdk.util;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;

/**
 * 比较工具类测试。
 *
 * @author DwArFeng
 * @since 2.0.1
 */
public class CompareUtilTest {

    @Test
    public void testLongIdComparator() {
        Assert.assertTrue(CompareUtil.LONG_ID_ASC_COMPARATOR.compare(1L, 2L) < 0);
        Assert.assertTrue(CompareUtil.LONG_ID_DESC_COMPARATOR.compare(1L, 2L) > 0);
    }

    @Test
    public void testInstantComparator() {
        Instant instant1 = Instant.ofEpochSecond(10L, 100L);
        Instant instant2 = Instant.ofEpochSecond(10L, 200L);

        Assert.assertTrue(CompareUtil.INSTANT_ASC_COMPARATOR.compare(instant1, instant2) < 0);
        Assert.assertTrue(CompareUtil.INSTANT_DESC_COMPARATOR.compare(instant1, instant2) > 0);
    }

    @Test
    public void testDataInfoDefaultComparator() {
        DataInfo dataInfo1 = new DataInfo(1L, "v1", new Date(1000L), 200);
        DataInfo dataInfo2 = new DataInfo(1L, "v2", new Date(1000L), 100);
        DataInfo dataInfo3 = new DataInfo(2L, "v3", new Date(900L), 0);

        Assert.assertTrue(CompareUtil.DATA_INFO_DEFAULT_COMPARATOR.compare(dataInfo2, dataInfo1) < 0);
        Assert.assertTrue(CompareUtil.DATA_INFO_DEFAULT_COMPARATOR.compare(dataInfo1, dataInfo3) < 0);
    }

    @Test
    public void testDataInfoHappenedInstantComparator() {
        DataInfo dataInfo1 = new DataInfo(1L, "v1", new Date(1000L), 100);
        DataInfo dataInfo2 = new DataInfo(2L, "v2", new Date(1000L), 200);

        Assert.assertTrue(CompareUtil.DATA_INFO_HAPPENED_INSTANT_ASC_COMPARATOR.compare(dataInfo1, dataInfo2) < 0);
        Assert.assertTrue(CompareUtil.DATA_INFO_HAPPENED_INSTANT_DESC_COMPARATOR.compare(dataInfo1, dataInfo2) > 0);
    }

    @Test
    public void testTimedValueComparator() {
        TimedValue timedValue1 = new TimedValue("v1", new Date(1000L), 100);
        TimedValue timedValue2 = new TimedValue("v2", new Date(1000L), 200);
        TimedValue timedValue3 = new TimedValue("v3", new Date(1001L), 0);

        Assert.assertTrue(CompareUtil.TIMED_VALUE_DEFAULT_COMPARATOR.compare(timedValue1, timedValue2) < 0);
        Assert.assertTrue(
                CompareUtil.TIMED_VALUE_HAPPENED_INSTANT_ASC_COMPARATOR.compare(timedValue2, timedValue3) < 0
        );
        Assert.assertTrue(
                CompareUtil.TIMED_VALUE_HAPPENED_INSTANT_DESC_COMPARATOR.compare(timedValue2, timedValue3) > 0
        );
    }
}
