package com.dwarfeng.dcti.impl.handler;

import com.dwarfeng.dcti.sdk.util.DctiQosExceptionHelper;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dcti.stack.exception.AmbiguousDctiHandlerException;
import com.dwarfeng.dcti.stack.exception.DctiHandlerNotFoundException;
import com.dwarfeng.dcti.stack.exception.NoDctiHandlerPresentException;
import com.dwarfeng.dcti.stack.handler.DctiHandler;
import com.dwarfeng.dcti.stack.handler.DctiQosHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * DCTI QoS 处理器实现。
 *
 * <p>
 * 以 Spring 容器中 <code>DctiHandler</code> Bean 的名称为键维护路由表；当 <code>handlerName</code> 为
 * <code>null</code> 且仅存在一个处理器时自动选择该处理器。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DctiQosHandlerImpl implements DctiQosHandler {

    private final Map<String, DctiHandler> dctiHandlerMap;

    public DctiQosHandlerImpl(Map<String, DctiHandler> dctiHandlerMap) {
        this.dctiHandlerMap = Optional.ofNullable(dctiHandlerMap).orElse(Collections.emptyMap());
    }

    @Override
    public List<String> listHandlerNames() throws HandlerException {
        try {
            List<String> handlerNames = dctiHandlerMap.keySet().stream().sorted().collect(Collectors.toList());
            return Collections.unmodifiableList(handlerNames);
        } catch (Exception e) {
            throw DctiQosExceptionHelper.parse(e);
        }
    }

    @Override
    public String toMessage(@Nullable String handlerName, DataInfo dataInfo) throws HandlerException {
        try {
            return determineHandler(handlerName).toMessage(dataInfo);
        } catch (Exception e) {
            throw DctiQosExceptionHelper.parse(e);
        }
    }

    @Override
    public DataInfo fromMessage(@Nullable String handlerName, String message) throws HandlerException {
        try {
            return determineHandler(handlerName).fromMessage(message);
        } catch (Exception e) {
            throw DctiQosExceptionHelper.parse(e);
        }
    }

    private DctiHandler determineHandler(@Nullable String handlerName) throws Exception {
        if (dctiHandlerMap.isEmpty()) {
            throw new NoDctiHandlerPresentException();
        }
        if (handlerName == null) {
            if (dctiHandlerMap.size() == 1) {
                return dctiHandlerMap.values().iterator().next();
            } else {
                throw new AmbiguousDctiHandlerException();
            }
        } else {
            if (!dctiHandlerMap.containsKey(handlerName)) {
                throw new DctiHandlerNotFoundException(handlerName);
            }
            return dctiHandlerMap.get(handlerName);
        }
    }

    @Override
    public String toString() {
        return "DctiQosHandlerImpl{" +
                "dctiHandlerMap=" + dctiHandlerMap +
                '}';
    }
}
