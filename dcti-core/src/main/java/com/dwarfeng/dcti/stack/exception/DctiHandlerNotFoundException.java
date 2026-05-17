package com.dwarfeng.dcti.stack.exception;

/**
 * DCTI 处理器未找到异常。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DctiHandlerNotFoundException extends DctiQosException {

    private static final long serialVersionUID = 6029789569291451086L;

    private final String handlerName;

    public DctiHandlerNotFoundException(String handlerName) {
        this.handlerName = handlerName;
    }

    public DctiHandlerNotFoundException(Throwable cause, String handlerName) {
        super(cause);
        this.handlerName = handlerName;
    }

    @Override
    public String getMessage() {
        return "应用上下文中没有找到名称为 " + handlerName + " 的 DCTI 处理器";
    }
}
