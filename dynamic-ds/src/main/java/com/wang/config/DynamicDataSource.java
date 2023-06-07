package com.wang.config;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @title
 * @description 使用AbstractRoutingDataSource创建两个库，R表示读库，W表示写库。
 * @author wzy
 * @updateTime 2023/5/17 10:53
 * @throws
 */
@Component("dynamicDataSource")
@Primary
public class DynamicDataSource extends AbstractRoutingDataSource {

    public static ThreadLocal<String> name = new ThreadLocal<>();

    @Override
    protected Object determineCurrentLookupKey() {
        return name.get();
    }

    @Resource(name = "dataSource1")
    DataSource dataSource1;
    @Resource(name = "dataSource2")
    DataSource dataSource2;

    @Override
    public void afterPropertiesSet() {
        // 为targetDataSources初始化所有数据源
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("R", dataSource1);
        targetDataSources.put("W", dataSource2);

        super.setTargetDataSources(targetDataSources);

        // 为defaultTargetDataSource 设置默认的数据源
        super.setDefaultTargetDataSource(dataSource1);

        super.afterPropertiesSet();
    }
}
