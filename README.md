# DCTI

数据收集传输接口。

Data Collection Transport Interface for DwArFeng

该项目是一个工具，定义了通用的数据传输接口，使用此接口的项目可以互相进行数据传输通信。

## 接口内容

1. 定义了标准的数据传输的格式

   `com.dwarfeng.dcti.stack.bean.dto.DataInfo`
   ```java
   /**
    * 数据值。
    *
    * @author DwArFeng
    * @since 1.0.0
    */
   public class DataInfo implements Dto {
   
       private static final long serialVersionUID = 6360037498642277881L;
   
       /**
        * 数据点的UUID。
        */
       private long pointLongId;
   
       /**
        * 数据的文本格式值。
        */
       private String value;
   
       /**
        * 数据值的发生日期。
        */
       private Date happenedDate;
   
       public DataInfo() {
       }
   
       public DataInfo(long pointLongId, String value, Date happenedDate) {
           this.pointLongId = pointLongId;
           this.value = value;
           this.happenedDate = happenedDate;
       }
   
       public long getPointLongId() {
           return pointLongId;
       }
   
       public void setPointLongId(long pointLongId) {
           this.pointLongId = pointLongId;
       }
   
       public String getValue() {
           return value;
       }
   
       public void setValue(String value) {
           this.value = value;
       }
   
       public Date getHappenedDate() {
           return happenedDate;
       }
   
       public void setHappenedDate(Date happenedDate) {
           this.happenedDate = happenedDate;
       }
   
       @Override
       public String toString() {
           return "DataInfo{" +
                   "pointLongId=" + pointLongId +
                   ", value='" + value + '\'' +
                   ", happenedDate=" + happenedDate +
                   '}';
       }
   }
   ```
   
2. 提供了 `DataInfo` 转换成 JSON 对象时的 bean `com.dwarfeng.dcti.sdk.bean.dto.FastJsonDataInfo`。

   请留意 `DataInfo` 转换成 JSON 是通过 FastJson 实现的。
   
   ```java
   /**
    * 适用于FastJson的DataInfo。
    *
    * @author DwArFeng
    * @since 1.0.0
    */
   public class FastJsonDataInfo implements Dto {
   
       private static final long serialVersionUID = -195639022270422852L;
   
       /**
        * 通过指定的 DataInfo 生成对应的 FastJsonDataInfo。
        *
        * @param dataInfo 指定的 DataInfo.
        * @return 通过指定的 DataInfo 生成对应的 FastJsonDataInfo。
        */
       public static FastJsonDataInfo of(DataInfo dataInfo) {
           if (Objects.isNull(dataInfo)) {
               return null;
           }
           return new FastJsonDataInfo(
                   dataInfo.getPointLongId(),
                   dataInfo.getValue(),
                   dataInfo.getHappenedDate()
           );
       }
   
       /**
        * 通过指定的 FastJsonDataInfo 生成对应的 DataInfo。
        *
        * @param fastJsonDataInfo 指定的 FastJsonDataInfo。
        * @return 通过指定的 FastJsonDataInfo 生成的 DataInfo。
        */
       public static DataInfo toStackBean(FastJsonDataInfo fastJsonDataInfo) {
           if (Objects.isNull(fastJsonDataInfo)) {
               return null;
           }
           return new DataInfo(
                   fastJsonDataInfo.getPointLongId(),
                   fastJsonDataInfo.getValue(),
                   fastJsonDataInfo.getHappenedDate()
           );
       }
   
       @JSONField(name = "point_long_id", ordinal = 1)
       private long pointLongId;
   
       @JSONField(name = "value", ordinal = 2)
       private String value;
   
       @JSONField(name = "happened_date", ordinal = 3)
       private Date happenedDate;
   
       public FastJsonDataInfo() {
       }
   
       public FastJsonDataInfo(long pointLongId, String value, Date happenedDate) {
           this.pointLongId = pointLongId;
           this.value = value;
           this.happenedDate = happenedDate;
       }
   
       public long getPointLongId() {
           return pointLongId;
       }
   
       public void setPointLongId(long pointLongId) {
           this.pointLongId = pointLongId;
       }
   
       public String getValue() {
           return value;
       }
   
       public void setValue(String value) {
           this.value = value;
       }
   
       public Date getHappenedDate() {
           return happenedDate;
       }
   
       public void setHappenedDate(Date happenedDate) {
           this.happenedDate = happenedDate;
       }
   
       @Override
       public String toString() {
           return "FastJsonDataInfo{" +
                   "pointLongId=" + pointLongId +
                   ", value='" + value + '\'' +
                   ", happenedDate=" + happenedDate +
                   '}';
       }
   }
   ```
   
3. 提供了 `DataInfo` 与文本相互转换的工具类 `com.dwarfeng.dcti.sdk.util.DataInfoUtil`

   ```java
   /**
    * 数据点工具类。
    *
    * @author DwArFeng
    * @since 1.0.0
    */
   public class DataInfoUtil {
   
       /**
        * 数据信息转换为文本。
        *
        * @param dataInfo 指定的数据信息。
        * @return 指定的数据信息转换成的文本。
        */
       public static String toMessage(@NonNull DataInfo dataInfo) {
           FastJsonDataInfo fastJsonDataInfo = FastJsonDataInfo.of(dataInfo);
           return JSON.toJSONString(dataInfo);
       }
   
       /**
        * 文本转换成数据信息。
        *
        * @param message 指定的文本。
        * @return 指定的文本转换成的数据信息。
        */
       public static DataInfo fromMessage(@NonNull String message) {
           FastJsonDataInfo fastJsonDataInfo = JSON.parseObject(message, FastJsonDataInfo.class);
           return FastJsonDataInfo.toStackBean(fastJsonDataInfo);
       }
   
       private DataInfoUtil() {
           throw new IllegalStateException("禁止外部实例化");
       }
   }
   ```