package com.dwarfeng.dcti.sdk.util;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dutil.basic.time.TimeUtil;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;

/**
 * 数据点工具类测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DataInfoUtilTest {

    @Test
    public void testSetHappenedInstantAndGetHappenedInstant() {
        DataInfo dataInfo = new DataInfo();
        Instant expected = Instant.ofEpochSecond(1713333333L, 123456789L);

        DataInfoUtil.setHappenedInstant(dataInfo, expected);
        Instant actual = DataInfoUtil.getHappenedInstant(dataInfo);

        Assert.assertEquals(expected, actual);
    }

    @SuppressWarnings("DataFlowIssue")
    @Test(expected = NullPointerException.class)
    public void testSetHappenedInstantWithNullDataInfo() {
        DataInfoUtil.setHappenedInstant(null, Instant.now());
    }

    // 该方法本身是用来进行异常测试的，故忽略相关异常警告。
    @SuppressWarnings("DataFlowIssue")
    @Test(expected = NullPointerException.class)
    public void testSetHappenedInstantWithNullInstant() {
        DataInfo dataInfo = new DataInfo();
        DataInfoUtil.setHappenedInstant(dataInfo, null);
    }

    // 该方法本身是用来进行异常测试的，故忽略相关异常警告。
    @SuppressWarnings("DataFlowIssue")
    @Test(expected = NullPointerException.class)
    public void testGetHappenedInstantWithNullDataInfo() {
        DataInfoUtil.getHappenedInstant(null);
    }

    @Test(expected = NullPointerException.class)
    public void testGetHappenedInstantWithNullHappenedDate() {
        DataInfo dataInfo = new DataInfo();
        dataInfo.setHappenedDateNanoOffset(0);
        DataInfoUtil.getHappenedInstant(dataInfo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHappenedInstantWithNanoOffsetBeyondMax() {
        DataInfo dataInfo = new DataInfo();
        dataInfo.setHappenedDate(new Date(1000L));
        dataInfo.setHappenedDateNanoOffset(TimeUtil.MAX_NANO_OFFSET + 1);
        DataInfoUtil.getHappenedInstant(dataInfo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHappenedInstantWithNanoOffsetBeyondMin() {
        DataInfo dataInfo = new DataInfo();
        dataInfo.setHappenedDate(new Date(1000L));
        dataInfo.setHappenedDateNanoOffset(TimeUtil.MIN_NANO_OFFSET - 1);
        DataInfoUtil.getHappenedInstant(dataInfo);
    }

    @Test
    public void testNewInstance() {
        long expectedPointLongId = 123456789L;
        String expectedValue = "hello";
        Instant expectedInstant = Instant.ofEpochSecond(1713333333L, 123456789L);

        DataInfo dataInfo = DataInfoUtil.newInstance(expectedPointLongId, expectedValue, expectedInstant);

        Assert.assertEquals(expectedPointLongId, dataInfo.getPointLongId());
        Assert.assertEquals(expectedValue, dataInfo.getValue());
        Assert.assertEquals(expectedInstant, DataInfoUtil.getHappenedInstant(dataInfo));
    }

    // 该方法本身是用来进行异常测试的，故忽略相关异常警告。
    @SuppressWarnings("DataFlowIssue")
    @Test(expected = NullPointerException.class)
    public void testNewInstanceWithNullInstant() {
        DataInfoUtil.newInstance(123456789L, "hello", null);
    }
}
