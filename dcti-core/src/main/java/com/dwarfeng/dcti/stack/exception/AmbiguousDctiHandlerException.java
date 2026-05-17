package com.dwarfeng.dcti.stack.exception;

/**
 * DCTI 处理器歧义异常。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class AmbiguousDctiHandlerException extends DctiQosException {

    private static final long serialVersionUID = -3222521935449557308L;

    public AmbiguousDctiHandlerException() {
    }

    public AmbiguousDctiHandlerException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "应用上下文中有多个 DCTI 处理器, 但是没有指定 handlerName";
    }
}
