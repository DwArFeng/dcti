package com.dwarfeng.dcti.sdk.util;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * 基于发生日期的数据信息比较器测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class TimedValueHappenedDateComparatorTest {

    @Deprecated
    @Test
    public void testCompare() {
        TimedValue timedValue1 = new TimedValue("v1", new Date(1000L), 100);
        TimedValue timedValue2 = new TimedValue("v2", new Date(1000L), 200);
        TimedValue timedValue3 = new TimedValue("v3", new Date(1001L), 0);

        Assert.assertEquals(0, TimedValueHappenedDateComparator.INSTANCE.compare(timedValue1, timedValue2));
        Assert.assertTrue(TimedValueHappenedDateComparator.INSTANCE.compare(timedValue2, timedValue3) < 0);
    }
}
