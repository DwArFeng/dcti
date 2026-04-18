package com.dwarfeng.dcti.sdk.bean.dto;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * FastJson 数据值测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonDataInfoTest {

    @Test
    public void testMap() {
        DataInfo dataInfo = new DataInfo(123L, "v", new Date(1000L), 456789);

        FastJsonDataInfo fastJsonDataInfo = FastJsonDataInfo.of(dataInfo);
        DataInfo actual = FastJsonDataInfo.toStackBean(fastJsonDataInfo);

        Assert.assertNotNull(actual);
        Assert.assertEquals(dataInfo.getPointLongId(), actual.getPointLongId());
        Assert.assertEquals(dataInfo.getValue(), actual.getValue());
        Assert.assertEquals(dataInfo.getHappenedDate(), actual.getHappenedDate());
        Assert.assertEquals(dataInfo.getHappenedDateNanoOffset(), actual.getHappenedDateNanoOffset());
    }

    @Test
    public void testDeserializeCompatibleWithLegacyMessage() {
        String message = "{\"point_long_id\":123,\"value\":\"v\",\"happened_date\":1000}";

        FastJsonDataInfo fastJsonDataInfo = JSON.parseObject(message, FastJsonDataInfo.class);
        DataInfo actual = FastJsonDataInfo.toStackBean(fastJsonDataInfo);

        Assert.assertEquals(0, fastJsonDataInfo.getHappenedDateNanoOffset());
        Assert.assertNotNull(actual);
        Assert.assertEquals(0, actual.getHappenedDateNanoOffset());
    }
}
