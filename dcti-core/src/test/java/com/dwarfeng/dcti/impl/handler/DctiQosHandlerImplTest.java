package com.dwarfeng.dcti.impl.handler;

import com.dwarfeng.dcti.node.configuration.SingletonHandlerConfiguration;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dcti.stack.exception.DctiHandlerNotFoundException;
import com.dwarfeng.dcti.stack.handler.DctiQosHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * DCTI QoS 处理器实现测试。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class DctiQosHandlerImplTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DctiQosHandler dctiQosHandler;

    @Test
    public void testListHandlerNamesSortedAndUnmodifiable() throws HandlerException {
        List<String> names = dctiQosHandler.listHandlerNames();
        Assert.assertEquals(1, names.size());
        Assert.assertEquals(SingletonHandlerConfiguration.BEAN_NAME_DCTI_HANDLER, names.get(0));
        try {
            names.add("x");
            Assert.fail();
        } catch (UnsupportedOperationException ignored) {
        }
    }

    @Test
    public void testNullHandlerNameWithSingleHandler() throws HandlerException {
        DataInfo dataInfo = new DataInfo(1L, "v", new Date(1L), 0);
        String message = dctiQosHandler.toMessage(null, dataInfo);
        DataInfo roundTrip = dctiQosHandler.fromMessage(null, message);

        Assert.assertEquals(dataInfo.getPointLongId(), roundTrip.getPointLongId());
        Assert.assertEquals(dataInfo.getValue(), roundTrip.getValue());
    }

    @Test(expected = DctiHandlerNotFoundException.class)
    public void testHandlerNotFound() throws HandlerException {
        dctiQosHandler.toMessage("missing", new DataInfo(1L, "v", new Date(1L), 0));
    }

    @Test
    public void testRouteByExplicitBeanNameConsistentWithNull() throws HandlerException {
        DataInfo dataInfo = new DataInfo(9L, "x", new Date(2L), 3);
        String byName = dctiQosHandler.toMessage(SingletonHandlerConfiguration.BEAN_NAME_DCTI_HANDLER, dataInfo);
        String byNull = dctiQosHandler.toMessage(null, dataInfo);
        Assert.assertEquals(byNull, byName);

        DataInfo neoByName = dctiQosHandler.fromMessage(SingletonHandlerConfiguration.BEAN_NAME_DCTI_HANDLER, byName);
        DataInfo neoByNull = dctiQosHandler.fromMessage(null, byName);
        Assert.assertEquals(neoByNull.getPointLongId(), neoByName.getPointLongId());
        Assert.assertEquals(neoByNull.getValue(), neoByName.getValue());
    }
}
