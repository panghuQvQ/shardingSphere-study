package com.wang.datetimeAlgorithm;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.assertj.core.util.Lists;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @title
 * @description 分表策略
 * @author wzy
 * @updateTime 2023/5/18 12:52
 * @throws
 */
public class AssessDayShardingAlgorithm implements PreciseShardingAlgorithm<Date>, RangeShardingAlgorithm<Date> {

    // 精确算法
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        return shardingValue.getLogicTableName() + "_" + getTargetMonthStr(shardingValue.getValue());
    }

    private String getTargetMonthStr(Date date) {
        date = date == null ? new Date() : date;
        return DateUtil.format(date, "yyyyMM");
    }

    // 范围算法
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {
        String dateUpperStr = shardingValue.getValueRange().upperEndpoint().toString();
        String dateLowerStr = shardingValue.getValueRange().lowerEndpoint().toString();
        Date startTime = parseDate(dateLowerStr);
        Date endTime = parseDate(dateUpperStr);
        String prefix = shardingValue.getLogicTableName() + "_";

        if (!(check(startTime) && check(endTime))) {
            return availableTargetNames;
        }

        // 仅查当前月份分表，可拓展
        if (startTime == null) {
            return Lists.newArrayList(prefix + DateUtil.format(endTime, "yyyyMM"));
        } else if (endTime == null) {
            return Lists.newArrayList(prefix + DateUtil.format(startTime, "yyyyMM"));
        }

        List<String> monthList = Lists.newArrayList();
        Date tmp = DateUtils.truncate(startTime, Calendar.DATE);
        while (tmp.getTime() <= endTime.getTime()) {
            monthList.add(prefix + DateUtil.format(tmp, "yyyyMM"));
            tmp = DateUtils.addMonths(tmp, 1);
        }
        return monthList;
    }

    /**
     * 开始同步数据的时间
     */
    public static final Date START = parseDate("2022-08-17");

    private boolean check(Date date) {
        return date == null || date.after(START);
    }

    private static Date parseDate(String dateStr) {
        try {
            return DateUtil.parse(dateStr, "yyyy-MM-dd");
        } catch (Exception e) {
            return null;
        }
    }

}
