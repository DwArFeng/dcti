# 使用说明

DCTI (Data Collection Transport Interface) 是一个用于定义通用数据传输接口的 Java 库。本文档将详细介绍如何使用 DCTI
进行数据传输。

## 概述

DCTI 提供了标准化的数据传输格式，使得不同系统之间可以方便地进行数据交换。主要包含以下核心组件：

- **DataInfo**: 标准的数据传输格式
- **TimedValue**: 简化的时间值结构
- **FastJsonDataInfo/FastJsonTimedValue**: JSON 序列化版本
- **DataInfoUtil**: 数据转换工具类
- **比较器**: 基于时间的排序工具

## 快速开始

### 1. 添加依赖

在您的 Maven 项目中添加 DCTI 依赖：

```xml
<dependency>
    <groupId>com.dwarfeng</groupId>
    <artifactId>dcti</artifactId>
    <version>${dcti.version}</version>
</dependency>
```

### 2. 基本使用

#### 创建 DataInfo 对象

```java
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import java.util.Date;

// 创建数据信息对象
DataInfo dataInfo = new DataInfo(
    12345L,                    // 数据点ID
    "25.6",                    // 数据值（字符串格式）
    new Date()                 // 发生时间
);
```

#### JSON 序列化和反序列化

```java
import com.dwarfeng.dcti.sdk.util.DataInfoUtil;

// 将 DataInfo 转换为 JSON 字符串
String jsonMessage = DataInfoUtil.toMessage(dataInfo);
System.out.println(jsonMessage);
// 输出: {"point_long_id":12345,"value":"25.6","happened_date":"2024-01-15T10:30:00.000+08:00"}

// 将 JSON 字符串转换回 DataInfo 对象
DataInfo parsedDataInfo = DataInfoUtil.fromMessage(jsonMessage);
System.out.println(parsedDataInfo.getPointLongId()); // 输出: 12345
```

## 实际应用场景

### Kafka 消息处理

以下是一个真实的 Kafka 消费者示例，展示了如何在消息队列中使用 DCTI：

```java
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
// 模拟传感器数据收集
public class SensorDataCollector {
    
    public void collectAndSendData() {
        // 收集传感器数据
        long sensorId = 1001L;
        String temperature = "23.5";
        Date timestamp = new Date();
        
        // 创建DataInfo对象
        DataInfo dataInfo = new DataInfo(sensorId, temperature, timestamp);
        
        // 转换为 JSON 并发送到消息队列
        String jsonMessage = DataInfoUtil.toMessage(dataInfo);
        // ... 发送到消息队列 ...
    }
}
```

### 数据排序和比较

```java
import com.dwarfeng.dcti.sdk.util.DataInfoHappenedDateComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataProcessor {
    
    public void sortDataByTime(List<DataInfo> dataList) {
        // 使用 DCTI 提供的时间比较器进行排序
        Collections.sort(dataList, DataInfoHappenedDateComparator.INSTANCE);
        
        // 或者使用 Java 8 的 Stream API
        List<DataInfo> sortedData = dataList.stream()
            .sorted(DataInfoHappenedDateComparator.INSTANCE)
            .collect(Collectors.toList());
    }
}
```

## 高级用法

### 批量数据处理

```java
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
        
        // 按时间排序
        Collections.sort(dataInfos, DataInfoHappenedDateComparator.INSTANCE);
        
        // 批量处理
        processDataInfos(dataInfos);
    }
}
```

### 使用 TimedValue

```java
import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.dcti.sdk.util.TimedValueHappenedDateComparator;

// 创建 TimedValue 对象（不包含数据点 ID）
TimedValue timedValue = new TimedValue("42.0", new Date());

// 使用 TimedValue 比较器
List<TimedValue> timedValues = Arrays.asList(timedValue);
Collections.sort(timedValues, TimedValueHappenedDateComparator.INSTANCE);
```

### 自定义数据转换

```java
public class CustomDataConverter {
    
    public DataInfo convertFromBusinessObject(BusinessData businessData) {
        return new DataInfo(
            businessData.getPointId(),
            String.valueOf(businessData.getValue()),
            businessData.getTimestamp()
        );
    }
    
    public BusinessData convertToBusinessObject(DataInfo dataInfo) {
        // ... 业务对象转换逻辑 ...
        return new BusinessData(
            dataInfo.getPointLongId(),
            Double.parseDouble(dataInfo.getValue()),
            dataInfo.getHappenedDate()
        );
    }
}
```

## 最佳实践

### 1. 错误处理

```java
public DataInfo safeParseMessage(String message) {
    try {
        return DataInfoUtil.fromMessage(message);
    } catch (Exception e) {
        LOGGER.error("解析DataInfo失败: {}", message, e);
        return null; // 或者抛出业务异常
    }
}
```

### 2. 性能优化

```java
// 对于大量数据的处理，考虑使用批量操作
public void processLargeDataset(List<String> messages) {
    List<DataInfo> validData = messages.parallelStream()
        .map(this::safeParseMessage)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
    
    // 批量处理有效数据
    batchProcessor.process(validData);
}
```

### 3. 数据验证

```java
public boolean isValidDataInfo(DataInfo dataInfo) {
    return dataInfo != null 
        && dataInfo.getPointLongId() > 0 
        && dataInfo.getValue() != null 
        && !dataInfo.getValue().trim().isEmpty()
        && dataInfo.getHappenedDate() != null;
}
```

## 注意事项

1. **数据格式**: DataInfo 中的 value 字段始终为字符串类型，需要根据业务需求进行类型转换
2. **时间处理**: 确保时间字段的正确性和时区处理
3. **异常处理**: 在 JSON 解析过程中要妥善处理可能的异常
4. **性能考虑**: 对于大量数据，考虑使用批量处理和异步处理
