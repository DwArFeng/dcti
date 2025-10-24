# ChangeLog

## Release_1.1.14_20261231_build_A

### 功能构建

- Wiki 编写。
   - docs/wiki/zh-CN/UsageGuide.md。

- 更新 README.md。

- Wiki 更新。
  - docs/wiki/zh-CN/Introduction.md。
  - docs/wiki/en-US/README.md。
  - docs/wiki/zh-CN/README.md。

- 依赖升级。
  - 升级 `dutil` 依赖版本为 `0.4.0.a-beta` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.6.2.a` 以规避漏洞。

- 优化开发环境支持。
  - 在 .gitignore 中添加 VSCode 相关文件的忽略规则。
  - 在 .gitignore 中添加 Cursor IDE 相关文件的忽略规则。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.13_20251012_build_A

### 功能构建

- 优化 `docs/wiki` 目录结构。
  - 将 `docs/wiki/en_US` 目录重命名为 `en-US`，以符合 rfc5646 规范。
  - 将 `docs/wiki/zh_CN` 目录重命名为 `zh-CN`，以符合 rfc5646 规范。
  - 更新 `docs/wiki/README.md` 中的链接指向。
  - 更新 `README.md` 中的链接指向。

- 依赖新增。
  - 增加依赖 `dutil` 以应用其新功能，版本为 `beta-0.3.2.a`。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.6.0.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.12_20250804_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/InstallBySourceCode.md。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.5.11.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.11_20250531_build_A

### 功能构建

- 更新 README.md。

- Wiki 更新。
  - docs/wiki/zh_CN/Introduction.md。

- Wiki 编写。
  - docs/wiki/zh_CN/VersionBlacklist.md。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.5.10.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.10_20250325_build_A

### 功能构建

- 更新 README.md。

- Wiki 编写。
  - 构建 wiki 目录结构。
  - docs/wiki/en_US/Contents.md。
  - docs/wiki/en_US/Introduction.md。
  - docs/wiki/zh_CN/Contents.md。
  - docs/wiki/zh_CN/Introduction.md。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.5.8.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.9_20241008_build_A

### 功能构建

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.5.6.a` 并解决兼容性问题，以应用其新功能。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.8_20240806_build_A

### 功能构建

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.5.5.a` 并解决兼容性问题，以应用其新功能。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.7_20231227_build_A

### 功能构建

- 优化文档注释。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.4.7.a` 并解决兼容性问题，以应用其新功能。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.6_20230330_build_A

### 功能构建

- 优化文件格式。
  - 优化 `pom.xml` 文件的格式。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.3.2.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.5_20221118_build_A

### 功能构建

- 优化 `README.md` 的格式。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.2.14.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- 删除不需要的依赖。
  - 删除 `annotations` 依赖。

---

## Release_1.1.4_20220915_build_A

### 功能构建

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.2.10.a`。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.3_20220903_build_A

### 功能构建

- 插件升级。
  - 升级 `maven-deploy-plugin` 插件版本为 `2.8.2`。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.2.9.a`。
  - 升级 `annotations` 依赖版本为 `3.0.1` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.2_20220525_build_A

### 功能构建

- 增加比较器。
  - com.dwarfeng.dcti.sdk.util.DataInfoHappenedDateComparator。
  - com.dwarfeng.dcti.sdk.util.TimedValueHappenedDateComparator。

- 依赖升级。
  - 升级 `fastjson` 依赖版本为 `1.2.83` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.1_20220521_build_A

### 功能构建

- 排除项目不需要的依赖。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.0_20200403_build_A

### 功能构建

- 新增DTO com.dwarfeng.dcti.stack.bean.dto.TimedValue
- 新增DTO com.dwarfeng.dcti.sdk.bean.dto.FastJsonTimedValue

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.0.0_20200326_build_C

### 功能构建

- 更新subgrade依赖至beta-0.3.2.b。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.0.0_20200316_build_B

### 功能构建

- (无)

### Bug 修复

- 修复 pom.xml 中的配置错误。

### 功能移除

- (无)

---

## Release_1.0.0_20200316_build_A

### 功能构建

- 整体功能实现。

### Bug 修复

- (无)

### 功能移除

- (无)
