package com.dwarfeng.dcti.stack.handler;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * DCTI 处理器。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public interface DctiHandler extends Handler {

    /**
     * 将指定的数据信息序列化为消息文本。
     *
     * @param dataInfo 指定的数据信息。
     * @return 消息文本。
     * @throws HandlerException 处理器异常。
     */
    String toMessage(DataInfo dataInfo) throws HandlerException;

    /**
     * 将指定的消息文本反序列化为数据信息。
     *
     * @param message 指定的消息文本。
     * @return 数据信息。
     * @throws HandlerException 处理器异常。
     */
    DataInfo fromMessage(String message) throws HandlerException;
}
