package com.dwarfeng.dcti.sdk.util;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dcti.stack.bean.dto.TimedValue;

import java.time.Instant;
import java.util.Comparator;
import java.util.function.Function;

/**
 * 比较工具类。
 *
 * <p>
 * 该工具类提供了一些比较器。
 *
 * @author DwArFeng
 * @since 2.0.1
 */
public final class CompareUtil {

    // region 主键比较

    /**
     * 长整型主键比较器，按照主键的升序进行比较。
     */
    public static final Comparator<Long> LONG_ID_ASC_COMPARATOR = Comparator.comparing(Function.identity());

    /**
     * 长整型主键比较器，按照主键的降序进行比较。
     */
    public static final Comparator<Long> LONG_ID_DESC_COMPARATOR = LONG_ID_ASC_COMPARATOR.reversed();

    // endregion

    // region 日期比较

    /**
     * Instant 比较器，按照 Instant 的升序进行比较。
     */
    public static final Comparator<Instant> INSTANT_ASC_COMPARATOR = Comparator.comparing(Function.identity());

    /**
     * Instant 比较器，按照 Instant 的降序进行比较。
     */
    public static final Comparator<Instant> INSTANT_DESC_COMPARATOR = INSTANT_ASC_COMPARATOR.reversed();

    // endregion

    // region 数据信息比较

    /**
     * 数据信息比较器，按照数据信息的默认顺序进行比较。
     *
     * <p>
     * 默认顺序是：
     * <ol>
     *     <li>先按照 pointLongId 升序。</li>
     *     <li>pointLongId 相等时，按照发生 Instant 升序。</li>
     * </ol>
     */
    public static final Comparator<DataInfo> DATA_INFO_DEFAULT_COMPARATOR = Comparator
            .comparing(DataInfo::getPointLongId, LONG_ID_ASC_COMPARATOR)
            .thenComparing(DataInfoUtil::getHappenedInstant, INSTANT_ASC_COMPARATOR);

    /**
     * 数据信息比较器，按照数据信息的发生 Instant 的升序进行比较。
     */
    public static final Comparator<DataInfo> DATA_INFO_HAPPENED_INSTANT_ASC_COMPARATOR =
            Comparator.comparing(DataInfoUtil::getHappenedInstant, INSTANT_ASC_COMPARATOR);

    /**
     * 数据信息比较器，按照数据信息的发生 Instant 的降序进行比较。
     */
    public static final Comparator<DataInfo> DATA_INFO_HAPPENED_INSTANT_DESC_COMPARATOR =
            Comparator.comparing(DataInfoUtil::getHappenedInstant, INSTANT_DESC_COMPARATOR);

    // endregion

    // region 拥有发生时间的数据值比较

    /**
     * 拥有发生时间的数据值比较器，按照默认顺序进行比较。
     *
     * <p>
     * 默认顺序为发生 Instant 升序。
     */
    public static final Comparator<TimedValue> TIMED_VALUE_DEFAULT_COMPARATOR =
            Comparator.comparing(TimedValueUtil::getHappenedInstant, INSTANT_ASC_COMPARATOR);

    /**
     * 拥有发生时间的数据值比较器，按照发生 Instant 的升序进行比较。
     */
    public static final Comparator<TimedValue> TIMED_VALUE_HAPPENED_INSTANT_ASC_COMPARATOR =
            Comparator.comparing(TimedValueUtil::getHappenedInstant, INSTANT_ASC_COMPARATOR);

    /**
     * 拥有发生时间的数据值比较器，按照发生 Instant 的降序进行比较。
     */
    public static final Comparator<TimedValue> TIMED_VALUE_HAPPENED_INSTANT_DESC_COMPARATOR =
            Comparator.comparing(TimedValueUtil::getHappenedInstant, INSTANT_DESC_COMPARATOR);

    // endregion

    private CompareUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
