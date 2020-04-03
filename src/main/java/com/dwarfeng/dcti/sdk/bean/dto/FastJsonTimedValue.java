package com.dwarfeng.dcti.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * 适用于FastJson的TimedValue。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class FastJsonTimedValue implements Dto {

    private static final long serialVersionUID = 5518358988835360188L;

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
                dataInfo.getHappenedDate()
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
                fastJsonTimedValue.getHappenedDate()
        );
    }

    @JSONField(name = "value", ordinal = 1)
    private String value;

    @JSONField(name = "happened_date", ordinal = 2)
    private Date happenedDate;

    public FastJsonTimedValue() {
    }

    public FastJsonTimedValue(String value, Date happenedDate) {
        this.value = value;
        this.happenedDate = happenedDate;
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
        return "FastJsonTimedValue{" +
                "value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
