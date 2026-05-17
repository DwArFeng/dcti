package com.dwarfeng.dcti.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * DCTI QoS 异常。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DctiQosException extends HandlerException {

    private static final long serialVersionUID = 8466386237534907375L;

    public DctiQosException() {
        super();
    }

    public DctiQosException(String message) {
        super(message);
    }

    public DctiQosException(String message, Throwable cause) {
        super(message, cause);
    }

    public DctiQosException(Throwable cause) {
        super(cause);
    }
}
