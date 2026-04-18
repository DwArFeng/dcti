package com.dwarfeng.dcti.sdk.bean.dto;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * FastJson 拥有发生时间的数据值测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonTimedValueTest {

    @Test
    public void testMap() {
        TimedValue timedValue = new TimedValue("v", new Date(1000L), 456789);

        FastJsonTimedValue fastJsonTimedValue = FastJsonTimedValue.of(timedValue);
        TimedValue actual = FastJsonTimedValue.toStackBean(fastJsonTimedValue);

        Assert.assertNotNull(actual);
        Assert.assertEquals(timedValue.getValue(), actual.getValue());
        Assert.assertEquals(timedValue.getHappenedDate(), actual.getHappenedDate());
        Assert.assertEquals(timedValue.getHappenedDateNanoOffset(), actual.getHappenedDateNanoOffset());
    }

    @Test
    public void testDeserializeCompatibleWithLegacyMessage() {
        String message = "{\"value\":\"v\",\"happened_date\":1000}";

        FastJsonTimedValue fastJsonTimedValue = JSON.parseObject(message, FastJsonTimedValue.class);
        TimedValue actual = FastJsonTimedValue.toStackBean(fastJsonTimedValue);

        Assert.assertEquals(0, fastJsonTimedValue.getHappenedDateNanoOffset());
        Assert.assertNotNull(actual);
        Assert.assertEquals(0, actual.getHappenedDateNanoOffset());
    }
}
