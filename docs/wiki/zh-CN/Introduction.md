# DCTI

数据收集传输接口。

Data Collection Transport Interface for DwArFeng

该项目定义了通用的数据传输接口，使用此接口的项目可以互相进行数据传输通信，项目基于 `subgrade` 构建。

---

## 特性

1. 标准数据传输 DTO：`DataInfo`、`TimedValue`（毫秒时间基线 + 毫秒内纳秒偏移）。
2. 基于 FastJson 的默认 JSON 消息格式（`point_long_id`、`value`、`happened_date`、`happened_date_nano_offset`）。
3. 数据转换与排序工具：`DataInfoUtil`、`TimedValueUtil`、`CompareUtil`，支持 `Instant` 时间语义。
4. Subgrade 架构支持：`DctiHandler`、`DctiQosService` 及对应实现。
5. Spring 单例配置：`com.dwarfeng.dcti.node.configuration.SingletonHandlerConfiguration`。
6. XSD 命名空间装配（`3.0.0.a` 起）。
7. `dcti-api` 模块提供 spring-telqos 集成（`DctiCommand`）。

运行下列示例以观察主要特性：

| 所在模块      | 示例类名                                                      | 说明          |
|-----------|-----------------------------------------------------------|-------------|
| dcti-api  | `com.dwarfeng.dcti.api.integration.example.TelqosExample` | Telqos 集成示例 |
| dcti-core | `com.dwarfeng.dcti.node.example.EncodeExample`            | 编码示例        |
| dcti-core | `com.dwarfeng.dcti.node.example.DecodeExample`            | 解码示例        |

## 文档

该项目的文档位于 [docs](../../../docs) 目录下，包括：

### wiki

wiki 为项目的开发人员为本项目编写的详细文档，包含不同语言的版本，主要入口为：

1. [简介](./Introduction.md) - 即本文件。
2. [目录](./Contents.md) - 文档目录。

## 安装说明

1. 下载源码。

   使用 git 进行源码下载。

   ```shell
   git clone git@github.com:DwArFeng/dcti.git
   ```

   对于中国用户，可以使用 gitee 进行高速下载。

   ```shell
   git clone git@gitee.com:dwarfeng/dcti.git
   ```

2. 项目安装。

   进入项目根目录，执行 maven 命令：

   ```shell
   mvn clean source:jar install
   ```

3. 项目引入。

   在项目的 `pom.xml` 中添加如下依赖：

   `dcti-core` 提供核心 DTO、工具类与 Handler 实现，为大多数场景的必选依赖：

   ```xml
   <dependency>
       <groupId>com.dwarfeng</groupId>
       <artifactId>dcti-core</artifactId>
       <version>${dcti.version}</version>
   </dependency>
   ```

   如需使用 spring-telqos 命令行集成，可额外引入 `dcti-api`：

   ```xml
   <dependency>
       <groupId>com.dwarfeng</groupId>
       <artifactId>dcti-api</artifactId>
       <version>${dcti.version}</version>
   </dependency>
   ```

4. enjoy it.

## 如何使用

1. 运行 `dcti-api/src/test` 或 `dcti-core/src/test` 下的示例与测试以观察主要特性。
2. 观察项目结构，将其中的配置运用到其它的 subgrade 项目中。

### 单例模式

加载 `com.dwarfeng.dcti.node.configuration.SingletonHandlerConfiguration`，即可获得单例模式的 `DctiHandler`、
`DctiQosHandler` 与 `DctiQosService`。  
在项目的 `application-context-scan.xml` 中追加 `com.dwarfeng.dcti.node.configuration` 包中全部 bean 的扫描，示例如下:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描 configuration 包中的全部 bean。 -->
    <context:component-scan base-package="com.dwarfeng.dcti.node.configuration"/>
</beans>
```

或者只扫描 `com.dwarfeng.dcti.node.configuration` 包中的 `SingletonHandlerConfiguration`，示例如下:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描 configuration 包中的 SingletonHandlerConfiguration -->
    <context:component-scan base-package="com.dwarfeng.dcti.node.configuration" use-default-filters="false">
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.dcti.node.configuration.SingletonHandlerConfiguration"
        />
    </context:component-scan>
</beans>
```

### 多实例模式

不使用包扫描，使用 xml 或者配置类生成多个 `DctiHandlerImpl` 实例。  
在项目的 `bean-definition.xml` 中追加配置，示例如下:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringBeanConstructorArgInspection, SpringXmlModelInspection -->
<beans
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd"
>

    <!-- 第 1 个处理器实例。 -->
    <bean name="dctiHandler1" class="com.dwarfeng.dcti.impl.handler.DctiHandlerImpl"/>

    <!-- 第 2 个处理器实例。 -->
    <bean name="dctiHandler2" class="com.dwarfeng.dcti.impl.handler.DctiHandlerImpl"/>

    <!-- QoS 处理器：通过构造器注入容器中全部 DctiHandler Bean 组成的 Map。 -->
    <bean name="dctiQosHandler" class="com.dwarfeng.dcti.impl.handler.DctiQosHandlerImpl" autowire="constructor"/>

    <!-- QoS 服务。 -->
    <bean name="dctiQosService" class="com.dwarfeng.dcti.impl.service.DctiQosServiceImpl">
        <constructor-arg ref="dctiQosHandler"/>
        <constructor-arg ref="mapServiceExceptionMapper"/>
    </bean>
</beans>
```

### XSD 配置

从 `3.0.0.a` 版本开始，可以使用 `dcti` 命名空间装配 `DctiHandler` 与 `DctiQosService`。  
在项目的 `application-context-dcti.xml` 中追加配置，示例如下:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringPlaceholdersInspection -->
<beans
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:dcti="http://dwarfeng.com/schema/dcti"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://dwarfeng.com/schema/dcti
        http://dwarfeng.com/schema/dcti/dcti.xsd"
>

    <dcti:handler/>
    <dcti:qos/>
</beans>
```

### 任意数量的实例模式

自行设计 `DctiHandler` 的工厂类，调用相关工厂方法生成 `DctiHandlerImpl` 实例，并按需注册到 Spring 容器中。
当前默认实现 `DctiHandlerImpl` 为无状态处理器，无需额外的启动或关闭生命周期管理。
