package com.wang.datetimeAlgorithm;

import com.google.common.collect.Maps;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.assertj.core.util.Lists;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @title
 * @description 分库策略
 * @author wzy
 * @updateTime 2023/5/17 16:53
 * @throws
 */
public class DkShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

    /**
     * hash算法
     */
    public static int clacHash(String str) {
        return 0;
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<String> shardingValue) {

        System.out.println(availableTargetNames);
        System.out.println(shardingValue);

        Map<String, Collection<String>> shardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();
        Map<Integer, String> datasourceMap = Maps.newHashMap();
        for (String availableTargetName : availableTargetNames) {
            String key = availableTargetName.substring(availableTargetName.length() - 1);
            datasourceMap.put(Integer.parseInt(key), availableTargetName);
        }
        List<String> resultList = Lists.newArrayList();
        for (Collection<String> value : shardingValuesMap.values()) {
            for (Object id : value) {
                int i = clacHash(String.valueOf(id)) % availableTargetNames.size();
                String dataSource = datasourceMap.get(i);
                if (!resultList.contains(dataSource)) {
                    resultList.add(dataSource);
                }
            }
        }
        if (resultList.size() == 0) {
            return availableTargetNames;
        }
        return resultList;
    }
}
