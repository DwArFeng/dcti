package com.dwarfeng.dcti.api.integration.springtelqos;

import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dcti.stack.service.DctiQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DCTI Telqos 指令。
 *
 * <p>
 * 该指令提供 DCTI QoS 服务的命令行入口，覆盖处理器枚举与 <code>DataInfo</code> 序列化、反序列化能力。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
@TelqosCommand
public class DctiCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "dcti";

    // region 指令选项

    private static final String COMMAND_OPTION_LIST_HANDLERS = "lh";
    private static final String COMMAND_OPTION_LIST_HANDLERS_LONG_OPT = "list-handlers";
    private static final String COMMAND_OPTION_TO_MESSAGE = "tm";
    private static final String COMMAND_OPTION_TO_MESSAGE_LONG_OPT = "to-message";
    private static final String COMMAND_OPTION_FROM_MESSAGE = "fm";
    private static final String COMMAND_OPTION_FROM_MESSAGE_LONG_OPT = "from-message";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LIST_HANDLERS,
            COMMAND_OPTION_TO_MESSAGE,
            COMMAND_OPTION_FROM_MESSAGE
    };

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_SUB_OPTION_HANDLER_NAME = "hn";
    private static final String COMMAND_SUB_OPTION_HANDLER_NAME_LONG_OPT = "handler-name";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_SUB_OPTION_POINT_LONG_ID = "pli";
    private static final String COMMAND_SUB_OPTION_POINT_LONG_ID_LONG_OPT = "point-long-id";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_SUB_OPTION_VALUE = "v";
    private static final String COMMAND_SUB_OPTION_VALUE_LONG_OPT = "value";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_SUB_OPTION_HAPPENED_DATE = "hd";
    private static final String COMMAND_SUB_OPTION_HAPPENED_DATE_LONG_OPT = "happened-date";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_SUB_OPTION_HAPPENED_DATE_NANO_OFFSET = "hdno";
    private static final String COMMAND_SUB_OPTION_HAPPENED_DATE_NANO_OFFSET_LONG_OPT = "happened-date-nano-offset";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_SUB_OPTION_MESSAGE = "m";
    private static final String COMMAND_SUB_OPTION_MESSAGE_LONG_OPT = "message";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_SUB_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT = "json-file";

    // endregion

    private final DctiQosService dctiQosService;

    public DctiCommand(DctiQosService dctiQosService) {
        super(IDENTITY);
        this.dctiQosService = dctiQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "DCTI QoS 服务";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LIST_HANDLERS),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_TO_MESSAGE) +
                        " [" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_HANDLER_NAME) + " handler-name] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_POINT_LONG_ID) + " point-long-id] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_VALUE) + " value] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_HAPPENED_DATE) + " happened-date] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_HAPPENED_DATE_NANO_OFFSET) +
                        " happened-date-nano-offset]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_FROM_MESSAGE) +
                        " [" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_HANDLER_NAME) + " handler-name] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_MESSAGE) + " message]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();

        list.add(
                Option.builder(COMMAND_OPTION_LIST_HANDLERS).longOpt(COMMAND_OPTION_LIST_HANDLERS_LONG_OPT)
                        .optionalArg(true).hasArg(false).desc("列出所有可用的 DCTI 处理器").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_TO_MESSAGE).longOpt(COMMAND_OPTION_TO_MESSAGE_LONG_OPT)
                        .optionalArg(true).hasArg(false).desc("将 DataInfo 序列化为消息文本").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_FROM_MESSAGE).longOpt(COMMAND_OPTION_FROM_MESSAGE_LONG_OPT)
                        .optionalArg(true).hasArg(false).desc("将消息文本反序列化为 DataInfo").build()
        );

        list.add(
                Option.builder(COMMAND_SUB_OPTION_HANDLER_NAME).longOpt(COMMAND_SUB_OPTION_HANDLER_NAME_LONG_OPT)
                        .hasArg(true).type(String.class).desc("DCTI 处理器名称").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_POINT_LONG_ID).longOpt(COMMAND_SUB_OPTION_POINT_LONG_ID_LONG_OPT)
                        .hasArg(true).type(String.class).desc("数据点长整型 ID").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_VALUE).longOpt(COMMAND_SUB_OPTION_VALUE_LONG_OPT).hasArg(true)
                        .type(String.class).desc("数据值").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_HAPPENED_DATE).longOpt(COMMAND_SUB_OPTION_HAPPENED_DATE_LONG_OPT)
                        .hasArg(true).type(String.class).desc("发生时间毫秒时间戳").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_HAPPENED_DATE_NANO_OFFSET)
                        .longOpt(COMMAND_SUB_OPTION_HAPPENED_DATE_NANO_OFFSET_LONG_OPT).hasArg(true).type(String.class)
                        .desc("发生时间对应毫秒中的纳秒偏移").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_MESSAGE).longOpt(COMMAND_SUB_OPTION_MESSAGE_LONG_OPT).hasArg(true)
                        .type(String.class).desc("消息文本").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_JSON_FILE).longOpt(COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT)
                        .hasArg(true).type(String.class).desc("JSON 文件路径").build()
        );

        return list;
    }

    @Override
    protected void executeWithCmd(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Pair<String, Integer> pair = CliCommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
        if (pair.getRight() != 1) {
            context.sendMessage(CliCommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
            return;
        }

        switch (pair.getLeft()) {
            case COMMAND_OPTION_LIST_HANDLERS:
                handleListHandlers(context);
                break;
            case COMMAND_OPTION_TO_MESSAGE:
                handleToMessage(context, cmd);
                break;
            case COMMAND_OPTION_FROM_MESSAGE:
                handleFromMessage(context, cmd);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    private void handleListHandlers(CommandExecutor.Context context) throws Exception {
        List<String> handlerNames = dctiQosService.listHandlerNames();
        context.sendMessage("可用的处理器名称:");
        if (handlerNames.isEmpty()) {
            context.sendMessage("  (Empty)");
            return;
        }
        for (int i = 0; i < handlerNames.size(); i++) {
            String handlerName = handlerNames.get(i);
            context.sendMessage(String.format("  %3d: %s", i + 1, handlerName));
        }
    }

    private void handleToMessage(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        String handlerName = parseHandlerName(context, cmd);
        DataInfo dataInfo = parseDataInfoForToMessage(context, cmd);
        String message = dctiQosService.toMessage(handlerName, dataInfo);
        context.sendMessage("处理器名称: " + normalizeHandlerNameForOutput(handlerName));
        context.sendMessage("消息文本:");
        context.sendMessage(message);
    }

    private void handleFromMessage(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        String handlerName = parseHandlerName(context, cmd);
        String message = parseMessageForFromMessage(context, cmd);
        DataInfo dataInfo = dctiQosService.fromMessage(handlerName, message);
        context.sendMessage("处理器名称: " + normalizeHandlerNameForOutput(handlerName));
        context.sendMessage("反序列化结果:");
        context.sendMessage("  pointLongId: " + dataInfo.getPointLongId());
        context.sendMessage("  value: " + dataInfo.getValue());
        context.sendMessage("  happenedDate: " + dataInfo.getHappenedDate());
        context.sendMessage("  happenedDateNanoOffset: " + dataInfo.getHappenedDateNanoOffset());
    }

    private DataInfo parseDataInfoForToMessage(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            String pathText = requireJsonFilePath(cmd);
            String json = readTextFile(pathText, true);
            try {
                return DataInfoUtil.fromMessage(json);
            } catch (Exception e) {
                throw new IllegalArgumentException("JSON 文件内容无法解析为 DataInfo: " + pathText, e);
            }
        }

        long pointLongId = parsePointLongId(context, cmd);
        String value = parseValue(context, cmd);
        Date happenedDate = parseHappenedDate(context, cmd);
        int nanoOffset = parseHappenedDateNanoOffset(cmd);
        return new DataInfo(pointLongId, value, happenedDate, nanoOffset);
    }

    private String parseMessageForFromMessage(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            String pathText = requireJsonFilePath(cmd);
            return readTextFile(pathText, false);
        }
        if (cmd.hasOption(COMMAND_SUB_OPTION_MESSAGE)) {
            String value = StringUtils.trimToNull(cmd.getOptionValue(COMMAND_SUB_OPTION_MESSAGE));
            if (StringUtils.isNotEmpty(value)) {
                return value;
            }
        }
        context.sendMessage("请输入消息文本:");
        String value = StringUtils.trimToNull(context.receiveMessage());
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("消息文本不能为空");
        }
        return value;
    }

    private String requireJsonFilePath(CommandLine cmd) {
        String pathText = StringUtils.trimToNull(cmd.getOptionValue(COMMAND_SUB_OPTION_JSON_FILE));
        if (StringUtils.isEmpty(pathText)) {
            throw new IllegalArgumentException("json-file 路径不能为空");
        }
        return pathText;
    }

    private String readTextFile(String pathText, boolean trimContent) throws Exception {
        Path path = Paths.get(pathText);
        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("文件不存在或不是普通文件: " + pathText);
        }
        if (!Files.isReadable(path)) {
            throw new IllegalArgumentException("文件不可读: " + pathText);
        }
        byte[] bytes = Files.readAllBytes(path);
        String text = new String(bytes, StandardCharsets.UTF_8);
        return trimContent ? text.trim() : text;
    }

    @Nullable
    private String parseHandlerName(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_HANDLER_NAME)) {
            return StringUtils.trimToNull(cmd.getOptionValue(COMMAND_SUB_OPTION_HANDLER_NAME));
        }

        List<String> handlerNames = dctiQosService.listHandlerNames();
        if (handlerNames.size() <= 1) {
            return null;
        }

        context.sendMessage("可用的处理器名称:");
        for (int i = 0; i < handlerNames.size(); i++) {
            context.sendMessage(String.format("  %3d: %s", i + 1, handlerNames.get(i)));
        }
        context.sendMessage("请输入处理器名称:");
        return StringUtils.trimToNull(context.receiveMessage());
    }

    private long parsePointLongId(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_POINT_LONG_ID)) {
            String raw = StringUtils.trimToNull(cmd.getOptionValue(COMMAND_SUB_OPTION_POINT_LONG_ID));
            if (StringUtils.isNotEmpty(raw)) {
                return parseLongParameter("point-long-id", raw);
            }
        }
        context.sendMessage("请输入数据点长整型 ID:");
        String raw = StringUtils.trimToNull(context.receiveMessage());
        if (StringUtils.isEmpty(raw)) {
            throw new IllegalArgumentException("数据点长整型 ID 不能为空");
        }
        return parseLongParameter("point-long-id", raw);
    }

    private String parseValue(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_VALUE)) {
            String value = cmd.getOptionValue(COMMAND_SUB_OPTION_VALUE);
            if (value != null && StringUtils.isNotEmpty(StringUtils.trimToNull(value))) {
                return value;
            }
        }
        context.sendMessage("请输入数据值:");
        String value = context.receiveMessage();
        if (StringUtils.isEmpty(StringUtils.trimToNull(value))) {
            throw new IllegalArgumentException("数据值不能为空");
        }
        return value;
    }

    private Date parseHappenedDate(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_HAPPENED_DATE)) {
            String raw = StringUtils.trimToNull(cmd.getOptionValue(COMMAND_SUB_OPTION_HAPPENED_DATE));
            if (StringUtils.isNotEmpty(raw)) {
                long ms = parseLongParameter("happened-date", raw);
                return new Date(ms);
            }
        }
        context.sendMessage("请输入发生时间毫秒时间戳:");
        String raw = StringUtils.trimToNull(context.receiveMessage());
        if (StringUtils.isEmpty(raw)) {
            throw new IllegalArgumentException("发生时间毫秒时间戳不能为空");
        }
        long ms = parseLongParameter("happened-date", raw);
        return new Date(ms);
    }

    private int parseHappenedDateNanoOffset(CommandLine cmd) throws Exception {
        if (!cmd.hasOption(COMMAND_SUB_OPTION_HAPPENED_DATE_NANO_OFFSET)) {
            return 0;
        }
        String raw = StringUtils.trimToNull(cmd.getOptionValue(COMMAND_SUB_OPTION_HAPPENED_DATE_NANO_OFFSET));
        if (StringUtils.isEmpty(raw)) {
            return 0;
        }
        return parseIntParameter("happened-date-nano-offset", raw);
    }

    private long parseLongParameter(String name, String raw) {
        try {
            return Long.parseLong(raw);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("参数 " + name + " 无法解析为整数: " + raw, e);
        }
    }

    // 为了代码的一致性和可扩展性，此处不做简化。
    @SuppressWarnings({"SameParameterValue", "RedundantThrows"})
    private int parseIntParameter(String name, String raw) throws Exception {
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("参数 " + name + " 无法解析为整数: " + raw, e);
        }
    }

    private String normalizeHandlerNameForOutput(@Nullable String handlerName) {
        return StringUtils.defaultIfBlank(handlerName, "<default>");
    }
}
