# Quick Start - 快速开始

本文档帮助您用最少的步骤体验 `dcti` 的 `DataInfo` 消息编解码能力。

## 确认环境

- JDK 1.8 或更高版本。
- Maven 3.x。

如果您的项目无法直接从中央仓库解析本项目依赖，请先参阅 [Install by Source Code](./InstallBySourceCode.md) 安装本项目及其依赖。

## 引入依赖

在项目的 `pom.xml` 中添加如下依赖：

```xml
<dependency>
    <groupId>com.dwarfeng</groupId>
    <artifactId>dcti</artifactId>
    <version>${dcti.version}</version>
</dependency>
```

## 最小示例

完成依赖引入后，即可使用 `DataInfo` 与 `DataInfoUtil` 完成最小消息编解码链路。

```java
import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;

import java.util.Date;

public class QuickStartExample {

    @SuppressWarnings("UnnecessaryModifier")
    public static void main(String[] args) {
        // 构造一条原始数据。
        DataInfo dataInfo = new DataInfo(12345L, "25.6", new Date());

        // 编码为消息文本。
        String message = DataInfoUtil.toMessage(dataInfo);
        System.out.println("编码结果: " + message);

        // 从消息文本解码为对象。
        DataInfo decoded = DataInfoUtil.fromMessage(message);
        System.out.println("解码结果: " + decoded);
        System.out.println("解码 pointLongId: " + decoded.getPointLongId());
    }
}
```

运行后，您会看到类似如下风格的文本：

```text
{"point_long_id":12345,"value":"25.6","happened_date":...,"happened_date_nano_offset":0}
```

这说明 `dcti` 已经完成了如下工作：

1. 将 `DataInfo` 按默认规则编码为 JSON 文本。
2. 使用 `point_long_id`、`value`、`happened_date`、`happened_date_nano_offset` 作为消息字段名。
3. 在解码时将文本恢复为 `DataInfo` 对象。

## 下一步

- [Usage Guide](./UsageGuide.md) - DCTI 的详细使用指南，包含实际应用场景和代码示例。
- [Message Format](./MessageFormat.md) - DCTI 默认消息格式说明，介绍 JSON 字段结构、字段含义和兼容性注意事项。
