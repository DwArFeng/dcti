package com.dwarfeng.dcti.sdk.util;

import com.dwarfeng.dcti.stack.exception.DctiException;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import javax.annotation.Nonnull;

/**
 * DCTI 异常帮助类。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class DctiExceptionHelper {

    /**
     * 将指定的异常转化为处理器异常。
     *
     * @param e 指定的异常。
     * @return 解析后得到的处理器异常。
     */
    public static HandlerException parse(@Nonnull Exception e) {
        if (e instanceof HandlerException) {
            return (HandlerException) e;
        }
        return new DctiException(e);
    }

    private DctiExceptionHelper() {
        throw new IllegalStateException("禁止实例化");
    }
}
