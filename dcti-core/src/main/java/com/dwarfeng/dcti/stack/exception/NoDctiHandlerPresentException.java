package com.dwarfeng.dcti.stack.exception;

/**
 * 没有 DCTI 处理器异常。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class NoDctiHandlerPresentException extends DctiQosException {

    private static final long serialVersionUID = -8609292741479038025L;

    public NoDctiHandlerPresentException() {
    }

    public NoDctiHandlerPresentException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "应用上下文中没有 DCTI 处理器";
    }
}
