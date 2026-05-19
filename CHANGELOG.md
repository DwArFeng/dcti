# ChangeLog

## Release_3.0.0_20260519_build_A

### 功能构建

- 更新 README.md。

- Wiki 更新。
  - docs/wiki/zh-CN/QuickStart.md。
  - docs/wiki/zh-CN/UsageGuide.md。
  - docs/wiki/zh-CN/Introduction.md。

- `dcti-core` 模块增加示例代码。
  - com.dwarfeng.dcti.node.example.DecodeExample。
  - com.dwarfeng.dcti.node.example.EncodeExample。

- 新增 spring-telqos 框架集成指令。
  - com.dwarfeng.ftp.api.integration.springtelqos.FtpCommand。

- 为项目增加 xsd 配置机制。
  - 增加 `META-INF/dcti.xsd` 文件。
  - 增加 `com.dwarfeng.dcti.node.configuration.DctiNamespaceHandlerSupport` 及对应的定义解析器。
  - 调整测试目录的相关配置文件，以使用新的 xsd 配置机制。

- 新增 QoS 服务。
  - com.dwarfeng.dcti.stack.service.DctiQosService。

- 为项目增加单例配置机制。
  - 增加 `com.dwarfeng.dcti.node.configuration.SingletonHandlerConfiguration` 单例配置类。

- 重构项目模块。
  - 新增 `dcti-core` 子模块，并迁移原有代码至该模块。
  - 新增 `dcti-api` 子模块。

- 重构项目结构。
  - 与 subgrade 集成，处理器层与 subgrade 对齐。
  - 与 subgrade 集成，栈异常与 subgrade 对齐。

- 增加依赖。
  - 增加依赖 `spring-telqos` 以应用其新功能，版本为 `2.0.1.a`。
  - 增加插件 `maven-clean-plugin` 以应用其新功能，版本为 `2.5`。
  - 增加依赖 `spring` 以应用其新功能，版本为 `5.3.39`。
  - 增加依赖 `commons-lang3` 以应用其新功能，版本为 `3.18.0`。
  - 增加依赖 `log4j2` 以应用其新功能，版本为 `2.25.4`。
  - 增加依赖 `slf4j` 以应用其新功能，版本为 `1.7.36`。

- 优化 `pom.xml` 文件的配置。

- 优化文件格式。
  - 优化 `pom.xml` 文件的格式。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## 更早的版本

[View all changelogs](./changelogs)
