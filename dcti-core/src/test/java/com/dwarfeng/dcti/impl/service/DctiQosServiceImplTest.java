package com.dwarfeng.dcti.impl.service;

import com.dwarfeng.dcti.node.configuration.SingletonHandlerConfiguration;
import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dcti.stack.service.DctiQosService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * DCTI QoS 服务测试。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class DctiQosServiceImplTest {

    @Autowired
    private DctiQosService dctiQosService;

    @Test
    public void testListHandlerNamesContainsDefaultBean() throws ServiceException {
        List<String> names = dctiQosService.listHandlerNames();
        Assert.assertEquals(1, names.size());
        Assert.assertEquals(SingletonHandlerConfiguration.BEAN_NAME_DCTI_HANDLER, names.get(0));
    }

    @Test
    public void testQosServiceNullHandlerNameMatchesDataInfoUtil() throws ServiceException {
        DataInfo dataInfo = new DataInfo(123L, "v", new Date(1000L), 456789);
        String expected = DataInfoUtil.toMessage(dataInfo);
        String actual = dctiQosService.toMessage(null, dataInfo);
        Assert.assertEquals(expected, actual);

        DataInfo neo = dctiQosService.fromMessage(null, expected);
        DataInfo utilNeo = DataInfoUtil.fromMessage(expected);
        Assert.assertEquals(utilNeo.getPointLongId(), neo.getPointLongId());
        Assert.assertEquals(utilNeo.getValue(), neo.getValue());
        Assert.assertEquals(utilNeo.getHappenedDate(), neo.getHappenedDate());
        Assert.assertEquals(utilNeo.getHappenedDateNanoOffset(), neo.getHappenedDateNanoOffset());
    }
}
