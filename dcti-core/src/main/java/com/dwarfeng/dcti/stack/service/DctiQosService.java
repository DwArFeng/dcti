package com.dwarfeng.dcti.stack.service;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dcti.stack.handler.DctiHandler;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

import javax.annotation.Nullable;
import java.util.List;

/**
 * DCTI QoS 服务。
 *
 * <p>
 * 参数 <code>handlerName</code> 为对应 {@link DctiHandler} 实例的 <code>bean name</code>。<br>
 * 当应用上下文中只有一个 {@link DctiHandler} 时，参数 <code>handlerName</code> 可以为 <code>null</code>。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public interface DctiQosService {

    /**
     * 列出所有 DCTI 处理器名称。
     *
     * <p>
     * 返回结果按字典序排序且不可变。
     *
     * @return 所有处理器的名称组成的列表（按字典序排序，不可变）。
     * @throws ServiceException 服务异常。
     */
    List<String> listHandlerNames() throws ServiceException;

    /**
     * 将指定的数据信息序列化为消息文本。
     *
     * @param handlerName 处理器名称。
     * @param dataInfo    指定的数据信息。
     * @return 消息文本。
     * @throws ServiceException 服务异常。
     */
    String toMessage(@Nullable String handlerName, DataInfo dataInfo) throws ServiceException;

    /**
     * 将指定的消息文本反序列化为数据信息。
     *
     * @param handlerName 处理器名称。
     * @param message     指定的消息文本。
     * @return 数据信息。
     * @throws ServiceException 服务异常。
     */
    DataInfo fromMessage(@Nullable String handlerName, String message) throws ServiceException;
}
