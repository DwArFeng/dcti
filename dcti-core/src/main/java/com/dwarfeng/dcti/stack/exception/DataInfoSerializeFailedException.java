package com.dwarfeng.dcti.stack.exception;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;

/**
 * DataInfo 序列化为消息文本失败时抛出的异常。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DataInfoSerializeFailedException extends DctiException {

    private static final long serialVersionUID = -8591457636489904800L;

    private final DataInfo dataInfo;

    public DataInfoSerializeFailedException(DataInfo dataInfo) {
        this.dataInfo = dataInfo;
    }

    public DataInfoSerializeFailedException(Throwable cause, DataInfo dataInfo) {
        super(cause);
        this.dataInfo = dataInfo;
    }

    @Override
    public String getMessage() {
        return "DataInfo 序列化为消息文本失败: " + dataInfo;
    }
}
