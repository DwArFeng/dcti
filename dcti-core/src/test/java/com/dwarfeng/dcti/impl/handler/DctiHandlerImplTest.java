package com.dwarfeng.dcti.impl.handler;

import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dcti.stack.exception.DctiException;
import com.dwarfeng.dcti.stack.handler.DctiHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * DCTI 处理器实现测试。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class DctiHandlerImplTest {

    @Autowired
    private DctiHandler dctiHandler;

    @Test
    public void testToMessageConsistentWithDataInfoUtil() throws HandlerException {
        DataInfo dataInfo = new DataInfo(123L, "v", new Date(1000L), 456789);

        String expected = DataInfoUtil.toMessage(dataInfo);
        String actual = dctiHandler.toMessage(dataInfo);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testFromMessageConsistentWithDataInfoUtil() throws HandlerException {
        DataInfo dataInfo = new DataInfo(123L, "v", new Date(1000L), 456789);
        String message = DataInfoUtil.toMessage(dataInfo);

        DataInfo expected = DataInfoUtil.fromMessage(message);
        DataInfo actual = dctiHandler.fromMessage(message);

        Assert.assertEquals(expected.getPointLongId(), actual.getPointLongId());
        Assert.assertEquals(expected.getValue(), actual.getValue());
        Assert.assertEquals(expected.getHappenedDate(), actual.getHappenedDate());
        Assert.assertEquals(expected.getHappenedDateNanoOffset(), actual.getHappenedDateNanoOffset());
    }

    @Test
    public void testLegacyMessageWithoutNanoOffset() throws HandlerException {
        String legacy = "{\"point_long_id\":123,\"value\":\"v\",\"happened_date\":1000}";
        DataInfo expected = DataInfoUtil.fromMessage(legacy);
        DataInfo actual = dctiHandler.fromMessage(legacy);

        Assert.assertEquals(0, actual.getHappenedDateNanoOffset());
        Assert.assertEquals(expected.getHappenedDateNanoOffset(), actual.getHappenedDateNanoOffset());
        Assert.assertEquals(expected.getPointLongId(), actual.getPointLongId());
    }

    @Test(expected = DctiException.class)
    public void testToMessageNullDataInfo() throws HandlerException {
        dctiHandler.toMessage(null);
    }

    @Test(expected = DctiException.class)
    public void testFromMessageNull() throws HandlerException {
        dctiHandler.fromMessage(null);
    }

    @Test(expected = DctiException.class)
    public void testFromMessageEmpty() throws HandlerException {
        dctiHandler.fromMessage("");
    }

    @Test(expected = DctiException.class)
    public void testFromMessageBlank() throws HandlerException {
        dctiHandler.fromMessage("   ");
    }

    @Test(expected = DctiException.class)
    public void testFromMessageInvalidJson() throws HandlerException {
        dctiHandler.fromMessage("not-json-at-all");
    }
}
