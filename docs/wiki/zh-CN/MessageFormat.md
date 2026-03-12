# Message Format - 消息格式

本文档介绍 `dcti` 默认消息格式的结构、字段含义以及兼容性注意事项。

## 概述

`dcti` 的核心 DTO 是 `DataInfo`，默认通过 `DataInfoUtil` 与 `FastJsonDataInfo` 在对象与文本消息之间进行转换。

默认实现的消息格式是一个 JSON 对象，包含如下字段：

- `point_long_id`
- `value`
- `happened_date`

## DataInfo 结构

`DataInfo` 包含三个核心字段：

- `pointLongId` - 数据点的长整型主键。
- `value` - 文本格式的数据值。
- `happenedDate` - 数据的发生时间。

对应的 Java 结构如下：

```java
public class DataInfo {
    private long pointLongId;
    private String value;
    private Date happenedDate;
}
```

## 默认 JSON 字段映射

默认消息格式由 `FastJsonDataInfo` 定义，字段映射关系如下：

| Java 字段        | JSON 字段         |
|----------------|-----------------|
| `pointLongId`  | `point_long_id` |
| `value`        | `value`         |
| `happenedDate` | `happened_date` |

这意味着，当您使用 `DataInfoUtil.toMessage(dataInfo)` 时，得到的文本会遵循上述字段命名规则；
当您使用 `DataInfoUtil.fromMessage(message)` 时，程序也会按上述字段名进行解析。

## 消息示例

下面是一个典型的消息结构示例：

```json
{
  "point_long_id": 12345,
  "value": "25.6",
  "happened_date": 1710000000000
}
```

其中：

- `point_long_id` 表示数据点主键。
- `value` 是字符串类型的数据值。
- `happened_date` 表示数据发生时间。

## 字段说明

### point_long_id

`point_long_id` 是数据点的唯一标识，对应 `DataInfo.pointLongId`。

该字段类型为整数，通常由业务系统中的点位主键直接映射而来。

### value

`value` 是文本格式的数据值，对应 `DataInfo.value`。

需要注意的是，`dcti` 不负责解释该值的业务类型，它只负责传输该字符串。
如果您希望表达温度、状态码、报警文本等信息，应由上层业务自行约定其格式。

### happened_date

`happened_date` 是数据发生时间，对应 `DataInfo.happenedDate`。

默认实现直接使用 Fastjson 对 `Date` 进行序列化与反序列化，因此该字段的具体文本形式取决于实际序列化配置。
在默认使用场景下，建议上下游统一约定日期格式；如果您需要固定格式，应在业务层进行封装，或实现自定义的消息转换工具。

## 编解码流程

默认编解码流程如下：

1. 调用 `DataInfoUtil.toMessage(dataInfo)`。
2. `DataInfo` 被转换为 `FastJsonDataInfo`。
3. `FastJsonDataInfo` 被序列化为 JSON 文本。

反向流程如下：

1. 调用 `DataInfoUtil.fromMessage(message)`。
2. JSON 文本被反序列化为 `FastJsonDataInfo`。
3. `FastJsonDataInfo` 被转换回 `DataInfo`。

## 兼容性说明

### 字段命名

默认实现要求消息中的字段名与 `FastJsonDataInfo` 保持一致。如果消息来自外部系统，且字段名不是
`point_long_id`、`value`、`happened_date`，则不能直接使用 `DataInfoUtil`，应在业务层自行适配。

### value 的业务语义

`dcti` 仅保证 `value` 是字符串，不保证其单位、精度、编码方式或枚举语义。
这些内容应由消息的生产方与消费方事先约定。

### 空消息与非法消息

默认 `DataInfoUtil` 不会对空消息、非法 JSON 或缺失字段做额外兜底处理。
如果您的系统需要更温和的错误处理方式，应在调用 `DataInfoUtil` 之前增加校验或封装。

## 使用建议

- 若上下游都使用 `dcti` 默认实现，建议直接复用 `DataInfoUtil`。
- 若需要与外部协议对接，建议在业务层封装一层“消息对象 ↔ DataInfo”的转换器。
- 若希望固定日期格式、字段名或协议结构，建议使用自定义消息工具，而不是直接修改 `DataInfoUtil`。

## 参考

- [Usage Guide](./UsageGuide.md) - DCTI 的基本使用说明。
