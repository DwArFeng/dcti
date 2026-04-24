# ChangeLog

## Release_2.0.0_20260414_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/TimeSemantics.md。
  - docs/wiki/zh-CN/QuickStart.md。

- Wiki 更新。
  - docs/wiki/zh-CN/MessageFormat.md。
  - docs/wiki/zh-CN/UsageGuide.md。

- 增强数据时间精度支持。
  - 增加 `com.dwarfeng.dcti.stack.bean.dto.DataInfo.happenedDateNanoOffset` 字段。
  - 增加 `com.dwarfeng.dcti.stack.bean.dto.TimedValue.happenedDateNanoOffset` 字段。
  - 增加 `com.dwarfeng.dcti.sdk.bean.dto.FastJsonDataInfo.happenedDateNanoOffset` 字段。
  - 增加 `com.dwarfeng.dcti.sdk.bean.dto.FastJsonTimedValue.happenedDateNanoOffset` 字段。
  - 修改 `com.dwarfeng.dcti.sdk.util.DataInfoUtil` 工具类，为相应的对象提供时间操作工具。
  - 修改 `com.dwarfeng.dcti.sdk.util.TimedValueUtil` 工具类，为相应的对象提供时间操作工具。
  - 新增 `com.dwarfeng.dcti.sdk.util.DataInfoHappenedInstantComparator` 比较器，支持纳秒级时间比较。
  - 新增 `com.dwarfeng.dcti.sdk.util.TimedValueHappenedInstantComparator` 比较器，支持纳秒级时间比较。

- 依赖升级。
  - 升级 `dutil` 依赖版本为 `0.4.2.a-beta` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.8.2.a` 以规避漏洞。

- 优化开发环境支持。
  - 在 .gitignore 中添加 Vibe Coding 相关文件的忽略规则。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## 更早的版本

[View all changelogs](./changelogs)
