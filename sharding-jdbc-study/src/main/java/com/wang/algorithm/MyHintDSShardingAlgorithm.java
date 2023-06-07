package com.wang.algorithm;

import com.alibaba.druid.util.StringUtils;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @title
 * @description 自定义扩展hint分片算法。Hint分片的分片键从SQL语句中抽离出来，由程序自行指定。
 * 通过HintManager来指定。注意这个HintManager设置的分片键都是线程安全的。
 * @author wzy
 * @updateTime 2023/5/12 13:51
 * @throws
 */

public class MyHintDSShardingAlgorithm implements HintShardingAlgorithm<Integer> {
    /**
     *
     * @param availableTargetNames 可选 数据源 和 表 的名称
     * @param shardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, HintShardingValue<Integer> shardingValue) {

        // shardingValue=HintShardingValue(logicTableName=course, columnName=, values=[1])
        // availableTargetNames=[m1, m2]
        System.out.println("shardingValue=" + shardingValue);
        System.out.println("availableTargetNames=" + availableTargetNames);
        List<String> shardingResult = new ArrayList<>();
        for (String targetName : availableTargetNames) {
            String suffix = targetName.substring(targetName.length() - 1);
            if (StringUtils.isNumber(suffix)) {
                // hint分片算法的ShardingValue有两种具体类型:
                // ListShardingValue和RangeShardingValue
                // 使用哪种取决于HintManager.addDatabaseShardingValue(String, String, ShardingOperator,...),ShardingOperator的类型
                List<Integer> list = shardingValue.getValues().stream().collect(Collectors.toList());
                for (Integer value : list) {
                    if (value == Integer.parseInt(suffix)) {
                        shardingResult.add(targetName);
                    }
                }
            }
        }
        return shardingResult;

        // =======旧版本==========================================================

        // 对SQL的零侵入分片方案。shardingValue是通过HintManager.
        // 比如我们要实现将 select * from t_user where user_id in {1,2,3,4,5,.....}; 按照in的第一个值，全部路由到course_1表中。
        // 注意他使用时有非常多的限制。
//        String key = "m"+shardingValue.getValues().toArray()[0];
//        if(availableTargetNames.contains(key)){
//            return Arrays.asList(key);
//        }
//        throw new UnsupportedOperationException(" route "+key+" is not supported. please check your config");
//        return Arrays.asList("m1","m2");
    }
}
