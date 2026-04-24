# 使用说明

DCTI (Data Collection Transport Interface) 是一个用于定义通用数据传输接口的 Java 库。本文档将详细介绍如何使用 DCTI
进行数据传输。

## 概述

DCTI 提供了标准化的数据传输格式，使得不同系统之间可以方便地进行数据交换。主要包含以下核心组件：

- **DataInfo**: 标准的数据传输格式
- **TimedValue**: 简化的时间值结构
- **FastJsonDataInfo/FastJsonTimedValue**: JSON 序列化版本
- **DataInfoUtil/TimedValueUtil**: 数据转换与时间点转换工具类（支持 `Instant`）
- **比较器**: 基于时间的排序工具（区分毫秒级和纳秒偏移级）

## 快速开始

### 1. 添加依赖

在您的 Maven 项目中添加 DCTI 依赖：

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!--suppress MavenModelInspection, MavenModelVersionMissed -->
<project
        xmlns="http://maven.apache.org/POM/4.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
        http://maven.apache.org/xsd/maven-4.0.0.xsd"
>

    <!-- 省略其他配置 -->
    <dependencies>
        <!-- 省略其他配置 -->
        <dependency>
            <groupId>com.dwarfeng</groupId>
            <artifactId>dcti</artifactId>
            <version>${dcti.version}</version>
        </dependency>
        <!-- 省略其他配置 -->
    </dependencies>
    <!-- 省略其他配置 -->
</project>
```

### 2. 基本使用

#### 创建 DataInfo 对象

```java
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;

import java.util.Date;

public class Example {

    @SuppressWarnings("UnnecessaryModifier")
    public static void main(String[] args) {
        // 创建数据信息对象。
        DataInfo dataInfo = new DataInfo(
                12345L,                    // 数据点 ID
                "25.6",                    // 数据值（字符串格式）
                new Date(),                // 发生时间（毫秒）
                123456                     // 毫秒内纳秒偏移
        );
        // 输出数据信息。
        System.out.println(dataInfo);
    }
}
```

#### JSON 序列化和反序列化

```java
import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;

import java.time.Instant;
import java.util.Date;

public class Example {

    @SuppressWarnings("UnnecessaryModifier")
    public static void main(String[] args) {
        DataInfo dataInfo = new DataInfo(12345L, "25.6", new Date(), 456789);
        // 将 DataInfo 转换为 JSON 字符串.
        String jsonMessage = DataInfoUtil.toMessage(dataInfo);
        // 输出: {"point_long_id":12345,"value":"25.6","happened_date":"2024-01-15T10:30:00.123+08:00",
        // "happened_date_nano_offset":456789}。
        System.out.println(jsonMessage);
        // 将 JSON 字符串转换回 DataInfo 对象。
        DataInfo parsedDataInfo = DataInfoUtil.fromMessage(jsonMessage);
        // 输出: 2024-01-15T02:30:00.123456789Z。
        System.out.println(DataInfoUtil.getHappenedInstant(parsedDataInfo));
    }
}
```

`happened_date_nano_offset` 为新增字段，旧消息未携带该字段时会按 `0` 处理，兼容历史格式。

## 实际应用场景

### Kafka 消息处理

以下是一个真实的 Kafka 消费者示例，展示了如何在消息队列中使用 DCTI：

```java
import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;

import java.util.List;
import java.util.function.Consumer;

@KafkaListener(
        id = "${kafka.listener.id}",
        containerFactory = "kafkaListenerContainerFactory",
        topics = "${kafka.topic}"
)
public void handleConsumerRecordsPolled(
        List<ConsumerRecord<String, String>> consumerRecords,
        Consumer<String, String> consumer,
        Acknowledgment ack
) {
    for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
        String message = consumerRecord.value();
        try {
            // 使用 DCTI 工具类将 JSON 消息转换为 DataInfo 对象
            DataInfo dataInfo = DataInfoUtil.fromMessage(message);

            // 转换为业务层对象
            // ... 业务逻辑处理 ...

        } catch (Exception e) {
            // ... 异常处理逻辑 ...
        }
    }
    ack.acknowledge();
}
```

### 数据收集和传输

```java
import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;

import java.util.Date;

// 模拟传感器数据收集
public class SensorDataCollector {

    public void collectAndSendData() {
        // 收集传感器数据。
        long sensorId = 1001L;
        String temperature = "23.5";
        Date timestamp = new Date();

        // 创建 DataInfo 对象。
        DataInfo dataInfo = new DataInfo(sensorId, temperature, timestamp);

        // 转换为 JSON 并发送到消息队列。
        String jsonMessage = DataInfoUtil.toMessage(dataInfo);
        // ... 发送到消息队列 ...
    }
}
```

### 数据排序和比较

```java
import com.dwarfeng.dcti.sdk.util.DataInfoHappenedInstantComparator;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DataProcessor {

    @SuppressWarnings({"Java8ListSort", "SimplifyStreamApiCallChains"})
    public void sortDataByTime(List<DataInfo> dataList) {
        // 使用 DCTI 提供的时间点比较器进行排序（日期 + 毫秒内纳秒偏移）。
        Collections.sort(dataList, DataInfoHappenedInstantComparator.INSTANCE);

        // 或者使用 Java 8 的 Stream API
        List<DataInfo> sortedData = dataList.stream()
                .sorted(DataInfoHappenedInstantComparator.INSTANCE)
                .collect(Collectors.toList());
    }
}
```

`DataInfoHappenedDateComparator` 仅用于历史兼容，已标记为过时，不推荐在任何场景继续使用。

## 高级用法

### 批量数据处理

```java
import com.dwarfeng.dcti.sdk.util.DataInfoHappenedInstantComparator;
import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("Java8ListSort")
public class BatchDataProcessor {

    public void processBatchData(List<String> jsonMessages) {
        List<DataInfo> dataInfos = new ArrayList<>();

        for (String message : jsonMessages) {
            try {
                DataInfo dataInfo = DataInfoUtil.fromMessage(message);
                dataInfos.add(dataInfo);
            } catch (Exception e) {
                LOGGER.error("解析消息失败: {}", message, e);
            }
        }

        // 按时间点排序（日期 + 毫秒内纳秒偏移）。
        Collections.sort(dataInfos, DataInfoHappenedInstantComparator.INSTANCE);

        // 批量处理
        processDataInfos(dataInfos);
    }
}
```

### 使用 TimedValue

```java
import com.dwarfeng.dcti.sdk.util.TimedValueHappenedInstantComparator;
import com.dwarfeng.dcti.sdk.util.TimedValueUtil;
import com.dwarfeng.dcti.stack.bean.dto.TimedValue;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Example {

    @SuppressWarnings({"UnnecessaryModifier", "ArraysAsListWithZeroOrOneArgument", "Java8ListSort"})
    public static void main(String[] args) {
        // 创建 TimedValue 对象（不包含数据点 ID）。
        TimedValue timedValue = new TimedValue("42.0", new Date(), 123456);
        // 通过工具类将时间设置为完整 Instant。
        TimedValueUtil.setHappenedInstant(timedValue, Instant.parse("2024-01-15T02:30:00.123456789Z"));
        // 使用 TimedValue 时间点比较器。
        List<TimedValue> timedValues = Arrays.asList(timedValue);
        Collections.sort(timedValues, TimedValueHappenedInstantComparator.INSTANCE);
    }
}
```

### 自定义数据转换

```java
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;

public class CustomDataConverter {

    public DataInfo convertFromBusinessObject(BusinessData businessData) {
        return new DataInfo(
                businessData.getPointId(),
                String.valueOf(businessData.getValue()),
                businessData.getTimestamp(),
                businessData.getTimestampNanoOffset()
        );
    }

    public BusinessData convertToBusinessObject(DataInfo dataInfo) {
        // ... 业务对象转换逻辑 ...
        return new BusinessData(
                dataInfo.getPointLongId(),
                Double.parseDouble(dataInfo.getValue()),
                dataInfo.getHappenedDate(),
                dataInfo.getHappenedDateNanoOffset()
        );
    }
}
```

## 最佳实践

### 1. 错误处理

```java
import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;

public class Example {

    public DataInfo safeParseMessage(String message) {
        try {
            return DataInfoUtil.fromMessage(message);
        } catch (Exception e) {
            LOGGER.error("解析 DataInfo 失败: {}", message, e);
            return null; // 或者抛出业务异常
        }
    }
}
```

### 2. 性能优化

对于大量数据的处理，考虑使用批量操作：

```java
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Example {

    public void processLargeDataset(List<String> messages) {
        List<DataInfo> validData = messages.parallelStream()
                .map(this::safeParseMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // 批量处理有效数据
        batchProcessor.process(validData);
    }
}
```

### 3. 数据验证

```java
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dutil.basic.time.TimeUtil;

public class Example {

    public boolean isValidDataInfo(DataInfo dataInfo) {
        return dataInfo != null
                && dataInfo.getPointLongId() > 0
                && dataInfo.getValue() != null
                && !dataInfo.getValue().trim().isEmpty()
                && dataInfo.getHappenedDate() != null
                && isNanoOffsetValid(dataInfo.getHappenedDateNanoOffset());
    }

    private boolean isNanoOffsetValid(int nanoOffset) {
        try {
            TimeUtil.checkNanoOffset(nanoOffset);
            return true;
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }
}
```

## 注意事项

1. **数据格式**: DataInfo 中的 value 字段始终为字符串类型，需要根据业务需求进行类型转换
2. **时间处理**: `happenedDate` 与 `happenedDateNanoOffset` 共同组成完整时间点，
   跨系统建议统一使用 `DataInfoUtil/TimedValueUtil` 与 `Instant` 进行转换
3. **排序策略**: 使用 `DataInfoHappenedInstantComparator` / `TimedValueHappenedInstantComparator`；
   `HappenedDateComparator` 已过时且不推荐使用
4. **性能考虑**: 对于大量数据，考虑使用批量处理和异步处理
