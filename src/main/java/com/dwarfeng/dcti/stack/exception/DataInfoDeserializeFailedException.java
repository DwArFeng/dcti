package com.dwarfeng.dcti.stack.exception;

/**
 * 消息文本反序列化为 DataInfo 失败时抛出的异常。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DataInfoDeserializeFailedException extends DctiException {

    private static final long serialVersionUID = -2686905115985328129L;

    private final String message;

    public DataInfoDeserializeFailedException(String message) {
        this.message = message;
    }

    public DataInfoDeserializeFailedException(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "消息文本反序列化为 DataInfo 失败: " + message;
    }
}
