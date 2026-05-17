package com.dwarfeng.dcti.impl.service;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dcti.stack.handler.DctiQosHandler;
import com.dwarfeng.dcti.stack.service.DctiQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;

import javax.annotation.Nullable;
import java.util.List;

/**
 * DCTI QoS 服务实现。
 *
 * <p>
 * 委托 {@link DctiQosHandler} 完成按名称路由后的序列化与反序列化，并通过 {@link ServiceExceptionMapper}
 * 将 Handler 层异常转换为 {@link ServiceException}。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DctiQosServiceImpl implements DctiQosService {

    private final DctiQosHandler dctiQosHandler;
    private final ServiceExceptionMapper sem;

    public DctiQosServiceImpl(DctiQosHandler dctiQosHandler, ServiceExceptionMapper sem) {
        this.dctiQosHandler = dctiQosHandler;
        this.sem = sem;
    }

    @Override
    public List<String> listHandlerNames() throws ServiceException {
        try {
            return dctiQosHandler.listHandlerNames();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("列出所有 DCTI 处理器名称时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public String toMessage(@Nullable String handlerName, DataInfo dataInfo) throws ServiceException {
        try {
            return dctiQosHandler.toMessage(handlerName, dataInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("将指定的数据信息序列化为消息文本时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public DataInfo fromMessage(@Nullable String handlerName, String message) throws ServiceException {
        try {
            return dctiQosHandler.fromMessage(handlerName, message);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("将指定的消息文本反序列化为数据信息时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
