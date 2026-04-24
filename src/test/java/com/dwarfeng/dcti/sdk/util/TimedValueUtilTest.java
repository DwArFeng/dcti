package com.dwarfeng.dcti.sdk.util;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.dutil.basic.time.TimeUtil;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;

/**
 * 拥有发生时间的数据值工具类测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class TimedValueUtilTest {

    @Test
    public void testSetHappenedInstantAndGetHappenedInstant() {
        TimedValue timedValue = new TimedValue();
        Instant expected = Instant.ofEpochSecond(1713333333L, 123456789L);

        TimedValueUtil.setHappenedInstant(timedValue, expected);
        Instant actual = TimedValueUtil.getHappenedInstant(timedValue);

        Assert.assertEquals(expected, actual);
    }

    // 该方法本身是用来进行异常测试的，故忽略相关异常警告。
    @SuppressWarnings("DataFlowIssue")
    @Test(expected = NullPointerException.class)
    public void testSetHappenedInstantWithNullTimedValue() {
        TimedValueUtil.setHappenedInstant(null, Instant.now());
    }

    // 该方法本身是用来进行异常测试的，故忽略相关异常警告。
    @SuppressWarnings("DataFlowIssue")
    @Test(expected = NullPointerException.class)
    public void testSetHappenedInstantWithNullInstant() {
        TimedValue timedValue = new TimedValue();
        TimedValueUtil.setHappenedInstant(timedValue, null);
    }

    // 该方法本身是用来进行异常测试的，故忽略相关异常警告。
    @SuppressWarnings("DataFlowIssue")
    @Test(expected = NullPointerException.class)
    public void testGetHappenedInstantWithNullTimedValue() {
        TimedValueUtil.getHappenedInstant(null);
    }

    @Test(expected = NullPointerException.class)
    public void testGetHappenedInstantWithNullHappenedDate() {
        TimedValue timedValue = new TimedValue();
        timedValue.setHappenedDateNanoOffset(0);
        TimedValueUtil.getHappenedInstant(timedValue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHappenedInstantWithNanoOffsetBeyondMax() {
        TimedValue timedValue = new TimedValue();
        timedValue.setHappenedDate(new Date(1000L));
        timedValue.setHappenedDateNanoOffset(TimeUtil.MAX_NANO_OFFSET + 1);
        TimedValueUtil.getHappenedInstant(timedValue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHappenedInstantWithNanoOffsetBeyondMin() {
        TimedValue timedValue = new TimedValue();
        timedValue.setHappenedDate(new Date(1000L));
        timedValue.setHappenedDateNanoOffset(TimeUtil.MIN_NANO_OFFSET - 1);
        TimedValueUtil.getHappenedInstant(timedValue);
    }

    @Test
    public void testNewInstance() {
        String expectedValue = "hello";
        Instant expectedInstant = Instant.ofEpochSecond(1713333333L, 123456789L);

        TimedValue timedValue = TimedValueUtil.newInstance(expectedValue, expectedInstant);

        Assert.assertEquals(expectedValue, timedValue.getValue());
        Assert.assertEquals(expectedInstant, TimedValueUtil.getHappenedInstant(timedValue));
    }

    // 该方法本身是用来进行异常测试的，故忽略相关异常警告。
    @SuppressWarnings("DataFlowIssue")
    @Test(expected = NullPointerException.class)
    public void testNewInstanceWithNullInstant() {
        TimedValueUtil.newInstance("hello", null);
    }
}
