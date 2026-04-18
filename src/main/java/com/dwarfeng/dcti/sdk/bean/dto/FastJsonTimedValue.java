package com.dwarfeng.dcti.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 拥有发生时间的数据值。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class FastJsonTimedValue implements Dto {

    private static final long serialVersionUID = -6265590051386084423L;

    /**
     * 通过指定的 TimedValue 生成对应的 FastJsonTimedValue。
     *
     * @param dataInfo 指定的 TimedValue.
     * @return 通过指定的 TimedValue 生成对应的 FastJsonTimedValue。
     */
    public static FastJsonTimedValue of(TimedValue dataInfo) {
        if (Objects.isNull(dataInfo)) {
            return null;
        }
        return new FastJsonTimedValue(
                dataInfo.getValue(),
                dataInfo.getHappenedDate(),
                dataInfo.getHappenedDateNanoOffset()
        );
    }

    /**
     * 通过指定的 FastJsonTimedValue 生成对应的 TimedValue。
     *
     * @param fastJsonTimedValue 指定的 FastJsonTimedValue。
     * @return 通过指定的 FastJsonTimedValue 生成的 TimedValue。
     */
    public static TimedValue toStackBean(FastJsonTimedValue fastJsonTimedValue) {
        if (Objects.isNull(fastJsonTimedValue)) {
            return null;
        }
        return new TimedValue(
                fastJsonTimedValue.getValue(),
                fastJsonTimedValue.getHappenedDate(),
                fastJsonTimedValue.getHappenedDateNanoOffset()
        );
    }

    @JSONField(name = "value", ordinal = 1)
    private String value;

    @JSONField(name = "happened_date", ordinal = 2)
    private Date happenedDate;

    @JSONField(name = "happened_date_nano_offset", ordinal = 3)
    private int happenedDateNanoOffset;

    public FastJsonTimedValue() {
    }

    public FastJsonTimedValue(String value, Date happenedDate) {
        this(value, happenedDate, 0);
    }

    public FastJsonTimedValue(String value, Date happenedDate, int happenedDateNanoOffset) {
        this.value = value;
        this.happenedDate = happenedDate;
        this.happenedDateNanoOffset = happenedDateNanoOffset;
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

    public int getHappenedDateNanoOffset() {
        return happenedDateNanoOffset;
    }

    public void setHappenedDateNanoOffset(int happenedDateNanoOffset) {
        this.happenedDateNanoOffset = happenedDateNanoOffset;
    }

    @Override
    public String toString() {
        return "FastJsonTimedValue{" +
                "value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                ", happenedDateNanoOffset=" + happenedDateNanoOffset +
                '}';
    }
}
