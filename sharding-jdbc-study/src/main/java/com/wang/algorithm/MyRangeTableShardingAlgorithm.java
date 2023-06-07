package com.wang.algorithm;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName MyRangeTableShardingAlgorithm.java
 * @Description 自定义扩展的范围分片算法， RangeShardingAlgorithm<分片键的类型>
 * @createTime 2023年05月10日 16:06:00
 */
public class MyRangeTableShardingAlgorithm implements RangeShardingAlgorithm<Long> {
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        //实现按照 Between 进行范围分片。
        //例如 select * from                                                         course where cid between 2000 and 3000;
        Long lowerEndpoint = rangeShardingValue.getValueRange().lowerEndpoint(); // 范围下限
        Long upperEndpoint = rangeShardingValue.getValueRange().upperEndpoint(); // 范围上限

        //实现course_$->{(3000 -2000 )%2+1} 分片策略
//        return Arrays.asList(shardingValue.getLogicTableName()+"_"+shardingValue.getLogicTableName() + ((upperEndpoint - lowerEndpoint) % 2 + 1));
        //对于我们这个奇偶分离的场景，大部分范围查询都是要两张表都查。
        return Arrays.asList(rangeShardingValue.getLogicTableName() + "_1", rangeShardingValue.getLogicTableName() + "_2");
    }
}
