package com.wang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName UserMapper.java
 * @Description TODO
 * @createTime 2023年05月22日 13:43:00
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 其中t_order在FROM的最左侧，ShardingSphere将会以它作为整个绑定表的主表。 所有路由计算将会只使用主表的策略，那么t_order_item表的分片计算将会使用t_order的条件。故绑定表之间的分区键要完全相同。
     */
    @Select("select u.user_id,u.username,d.value ustatus from t_user u left join t_dict d on u.ustatus = d.status")
    public List<User> selectUserList();
}
