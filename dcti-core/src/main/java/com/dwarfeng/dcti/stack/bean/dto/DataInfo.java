package com.dwarfeng.dcti.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;

/**
 * 数据值。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DataInfo implements Dto {

    private static final long serialVersionUID = 5133102465235824196L;

    /**
     * 数据点的 UUID。
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
    /**
     * 数据值发生日期对应毫秒中的纳秒偏移。
     */
    private int happenedDateNanoOffset;

    public DataInfo() {
    }

    public DataInfo(long pointLongId, String value, Date happenedDate) {
        this(pointLongId, value, happenedDate, 0);
    }

    public DataInfo(long pointLongId, String value, Date happenedDate, int happenedDateNanoOffset) {
        this.pointLongId = pointLongId;
        this.value = value;
        this.happenedDate = happenedDate;
        this.happenedDateNanoOffset = happenedDateNanoOffset;
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

    public int getHappenedDateNanoOffset() {
        return happenedDateNanoOffset;
    }

    public void setHappenedDateNanoOffset(int happenedDateNanoOffset) {
        this.happenedDateNanoOffset = happenedDateNanoOffset;
    }

    @Override
    public String toString() {
        return "DataInfo{" +
                "pointLongId=" + pointLongId +
                ", value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                ", happenedDateNanoOffset=" + happenedDateNanoOffset +
                '}';
    }
}
