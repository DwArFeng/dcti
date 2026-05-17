package com.dwarfeng.dcti.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * Dcti 模块 Handler 层基础异常。
 *
 * <p>
 * 表示与 dcti 处理器相关的失败语义，具体场景由子类细分（如序列化、反序列化失败）。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DctiException extends HandlerException {

    private static final long serialVersionUID = -6057046465965601647L;

    public DctiException() {
        super();
    }

    public DctiException(String message) {
        super(message);
    }

    public DctiException(String message, Throwable cause) {
        super(message, cause);
    }

    public DctiException(Throwable cause) {
        super(cause);
    }
}
