package com.wang.algorithm;

import cn.hutool.json.JSONUtil;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @title
 * @description 表分片：实现根据多个分片列进行综合分片的算法
 * @author wzy
 * @updateTime 2023/5/11 16:08
 * @throws
 */
public class MyComplexTableShardingAlgorithm implements ComplexKeysShardingAlgorithm<Long> {
    /**
     *
     * @param availableTargetNames 目标数据源 或者 表 的值。
     * @param shardingValue logicTableName逻辑表名 columnNameAndShardingValuesMap 分片列的精确值集合。 columnNameAndRangeValuesMap 分片列的范围值集合
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Long> shardingValue) {

        // availableTargetNames:["course_1","course_2"]
        // shardingValues:{"logicTableName":"course","columnNameAndRangeValuesMap":{"user_id":{}},"columnNameAndShardingValuesMap":{"cid":[1656568092007374849,1656568094419099649]}}
        System.out.println("availableTargetNames:" + JSONUtil.toJsonStr(availableTargetNames) + ",shardingValues:" + JSONUtil.toJsonStr(shardingValue));


        //实现按照 Between 进行范围分片。
        //例如 select * from course where cid in (1,3,5) and userid Between 200 and 300;


        Collection<Long> cidCol = shardingValue.getColumnNameAndShardingValuesMap().get("cid");
        Collection<Long> uageRange = shardingValue.getColumnNameAndShardingValuesMap().get("user_id");
//        Range<Long> uageRange = shardingValue.getColumnNameAndRangeValuesMap().get("user_id");

        List<String> result = new ArrayList<>();

//        Long lowerEndpoint = uageRange.lowerEndpoint();//200
//        Long upperEndpoint = uageRange.upperEndpoint();//300

        //实现自定义分片逻辑 例如可以自己实现 course_$->{cid%2+1 + (30-20)+1} 这样的复杂分片逻辑
        for(Long cid : cidCol){
            BigInteger cidI = BigInteger.valueOf(cid);
            BigInteger target = (cidI.add(new BigInteger("1")).mod(new BigInteger("4"))).divide(new BigInteger("2")).add(new BigInteger("1"));
            result.add("course_"+target);
        }
        return result;
    }
}
