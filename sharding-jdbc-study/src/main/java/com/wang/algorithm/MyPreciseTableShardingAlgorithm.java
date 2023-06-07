package com.wang.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.math.BigInteger;
import java.util.Collection;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName MyPreciseTableShardingAlgorithm.java
 * @Description 自定义扩展的精确分片算法
 * @createTime 2023年05月10日 16:24:00
 */
public class MyPreciseTableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    /**
     * @param: collection 有效的数据源或表的名字，这里就对应配置文件中配置的数据源信息
     * @param: preciseShardingValue 包含 逻辑表名、分片列和分片列的值。
     * @return: java.lang.String  返回目标结果
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        // 实现按照 = 或 IN 进行精确分片
        // 例如 select * from course where cid = 1 or cid in (1,3,5)
        // 实现 course_$->{cid%2+1} 分表策略

        BigInteger shardingValue = BigInteger.valueOf(preciseShardingValue.getValue());
        BigInteger res = (shardingValue.add(new BigInteger("1")).mod(new BigInteger("4"))).divide(new BigInteger("2")).add(new BigInteger("1"));
        String key = preciseShardingValue.getLogicTableName() + "_" + res;
        if (collection.contains(key)) {
            return key;
        }
        throw new UnsupportedOperationException("route" + key + "is not supported. please check your config");
    }
}
