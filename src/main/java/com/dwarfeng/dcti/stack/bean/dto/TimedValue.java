package com.dwarfeng.dcti.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;

/**
 * 拥有发生时间的数据值。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class TimedValue implements Dto {

    private static final long serialVersionUID = 1904737116172014591L;

    private String value;
    private Date happenedDate;

    public TimedValue() {
    }

    public TimedValue(String value, Date happenedDate) {
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
        return "TimedValue{" +
                "value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
