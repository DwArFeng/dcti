# Time Semantics - 时间语义

本文档说明 `dcti` 当前实现的时间精度、排序语义和协议边界，帮助集成方避免误用。

## 当前时间模型

`dcti` 当前核心时间字段由 `Date happenedDate` 与 `int happenedDateNanoOffset` 组成，存在于以下 DTO：

- `DataInfo`
- `TimedValue`
- `FastJsonDataInfo`
- `FastJsonTimedValue`

该模型以毫秒时间基线 + 毫秒内纳秒偏移的组合表达发生时间。

## 消息协议边界

默认 JSON 协议中，时间相关字段为：

- `happened_date`
- `happened_date_nano_offset`

其中 `happened_date_nano_offset` 对应 Java 字段 `happenedDateNanoOffset`。

## 排序语义

过时的时间比较器如下：

- `DataInfoHappenedDateComparator`
- `TimedValueHappenedDateComparator`

两者仅基于 `happenedDate` 进行比较。

如果需要将 `happenedDateNanoOffset` 纳入排序，请使用：

- `DataInfoHappenedInstantComparator`
- `TimedValueHappenedInstantComparator`

## 集成建议

1. 在任何情况下，使用 `HappenedInstantComparator` 系列。
2. 如果仅需要毫秒级语义，保持 `happenedDateNanoOffset` 为 `0`，并使用即可。
3. 做时间点读写时，可选用 `DataInfoUtil` 与 `TimedValueUtil` 实现纳秒级时间精度与 `Instant` 对象的转换。

## 参考

- [Usage Guide](./UsageGuide.md)
- [Message Format](./MessageFormat.md)
