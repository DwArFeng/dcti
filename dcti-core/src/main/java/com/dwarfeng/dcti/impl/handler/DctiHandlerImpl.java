package com.dwarfeng.dcti.impl.handler;

import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.sdk.util.DctiExceptionHelper;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dcti.stack.exception.DataInfoDeserializeFailedException;
import com.dwarfeng.dcti.stack.exception.DataInfoSerializeFailedException;
import com.dwarfeng.dcti.stack.handler.DctiHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * DCTI 处理器实现。
 *
 * <p>
 * 内部复用 {@link DataInfoUtil#toMessage(DataInfo)} 与 {@link DataInfoUtil#fromMessage(String)}，
 * 以保持与既有工具类完全一致的序列化格式与兼容性。
 *
 * <p>
 * 对不合规输入抛出 dcti 定义的 {@link HandlerException} 体系异常，而不是向调用方泄漏未包装的运行期异常。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DctiHandlerImpl implements DctiHandler {

    public DctiHandlerImpl() {
    }

    @BehaviorAnalyse
    @Override
    public String toMessage(DataInfo dataInfo) throws HandlerException {
        try {
            return toMessage0(dataInfo);
        } catch (Exception e) {
            throw DctiExceptionHelper.parse(e);
        }
    }

    private String toMessage0(DataInfo dataInfo) throws Exception {
        try {
            Objects.requireNonNull(dataInfo, "参数 dataInfo 不能为 null");
            return DataInfoUtil.toMessage(dataInfo);
        } catch (Exception e) {
            throw new DataInfoSerializeFailedException(e, dataInfo);
        }
    }

    @BehaviorAnalyse
    @Override
    public DataInfo fromMessage(String message) throws HandlerException {
        try {
            DataInfo dataInfo = fromMessage0(message);
            if (dataInfo == null) {
                throw new IllegalStateException("反序列化得到的 DataInfo 不能为 null");
            }
            return dataInfo;
        } catch (Exception e) {
            throw DctiExceptionHelper.parse(e);
        }
    }

    private DataInfo fromMessage0(String message) throws Exception {
        try {
            Objects.requireNonNull(message, "参数 message 不能为 null");
            if (StringUtils.isBlank(message)) {
                throw new IllegalArgumentException("参数 message 不能为空或空白");
            }
            return DataInfoUtil.fromMessage(message);
        } catch (Exception e) {
            throw new DataInfoDeserializeFailedException(e, message);
        }
    }
}
