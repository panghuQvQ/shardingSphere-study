package com.wang;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.entity.Dict;
import com.wang.entity.Student;
import com.wang.entity.User;
import com.wang.mapper.DictMapper;
import com.wang.mapper.StudentMapper;
import com.wang.mapper.UserMapper;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.spi.algorithm.keygen.ShardingKeyGeneratorServiceLoader;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.wang.entity.Course;
import com.wang.mapper.CourseMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName com.wang.ShardingTest.java
 * @Description 重要的测试类
 * @createTime 2023年04月18日 10:35:00
 */
@SpringBootTest
public class ShardingTest {

    @Resource
    CourseMapper courseMapper;

    @Resource
    StudentMapper studentMapper;

    @Resource
    DictMapper dictMapper;

    @Resource
    UserMapper userMapper;

    @Test
    @Transactional // 在 ShardingSphere 下，同样适用，可以解决分布式事务问题
    void addCourse(){
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCname("java");
            course.setUserid(100L);
            course.setCstatus("1");
            courseMapper.insert(course);
        }
    }


    /**
     * inline 策略 =
     */
    @Test
    void queryCourseByInline(){

        List<Course> list = courseMapper.selectList(new QueryWrapper<Course>().eq("cid", 1656568092007374849L));
        for (Course course : list) {
            System.out.println(course);
        }
    }

    /**
     * 注意！！！
     * inline 策略不支持范围查询
     *
     * standard 策略，可配置范围，如下：
     * 标准分片策略,对单个字段操作。提供对SQL语句中的=, IN和BETWEEN AND的分片操作支持。
     */
    @Test
    void queryCourseByStandard(){

        List<Course> list = courseMapper.selectList(new QueryWrapper<Course>().eq("cid", 1656568092007374849L));

        // between 范围查询 ----> 参考 MyRangeDSShardingAlgorithm / MyRangeTableShardingAlgorithm
        List<Course> list2 = courseMapper.selectList(new QueryWrapper<Course>().between("cid", 1656568092007374849L,1656568094419099649L));
        // in 精确查询 ----> 参考 MyPreciseDSShardingAlgorithm / MyPreciseTableShardingAlgorithm
        List<Course> list3 = courseMapper.selectList(new QueryWrapper<Course>().in("cid", 1656568092007374849L,1656568094419099649L));

        for (Course course : list3) {
            System.out.println("course==========:"+course);
        }
    }

    /**
     * 两个字段 或 多字段组合判断，需要使用 Complex 策略
     * 复合分片策略。提供对SQL语句中的=, IN和BETWEEN AND的分片操作支持。
     */
    @Test
    void queryCourseByComplex(){

        // 复合查询 参考 MyComplexDSShardingAlgorithm / MyComplexTableShardingAlgorithm
        List<Course> list4 = courseMapper.selectList(
                new QueryWrapper<Course>()
                        .in("cid", 1656568092007374849L,1656568094419099649L)
                        .between("user_id", 80L,120L)
        );

        for (Course course : list4) {
            System.out.println(course);
        }
    }

    /**
     * hint 分片策略，不根据 SQL进行分片
     */
    @Test
    void queryCourseByHint(){
        HintManager hintManager = HintManager.getInstance();
        hintManager.addDatabaseShardingValue("course", 1 );
        hintManager.addTableShardingValue("course",  1);

        for (Course course : courseMapper.selectList(new QueryWrapper<>())) {
            System.out.println(course);
        }
    }

    /**
     * 新增学生表
     */
    @Test
    void saveStudent(){

//        for (int i = 0; i < 100; i++) {
//            Student student = new Student();
//            student.setName("test"+i);
//            student.setBirthday(new Date());
//            studentMapper.insert(student);
//        }
        Student student = new Student();
        student.setName("test");
        student.setBirthday(new Date());
        studentMapper.insert(student);
    }

    /**
     * 根据时间范围分片，查询学生信息
     */
    @Test
    void queryStudent(){

        Date date = new Date();
        DateTime start = DateUtil.parse("2023-04-17 16:25:16");
        DateTime end = DateUtil.parse("2023-06-17 16:25:16");

        QueryWrapper<Student> birthday = new QueryWrapper<Student>()
                .between("birthday", start, end);

        for (Student student : studentMapper.selectList(birthday)) {
            System.out.println(student);
        }
    }

    //========== config5, config7  ===============================================================================================

    /**
     * config5 : 广播表：向每个数据库中的t_dict同时添加了新数据
     *
     * config7: 新增至 主库 m1
     */
    @Test
    void addDict(){

        ServiceLoader<ShardingKeyGenerator> load = ServiceLoader.load(ShardingKeyGenerator.class);
        for (ShardingKeyGenerator shardingKeyGenerator : load) {
            String type = shardingKeyGenerator.getType();
            Comparable<?> comparable = shardingKeyGenerator.generateKey();
            System.out.println("================"+type+":"+comparable);
        }

        Dict dict = new Dict();
        dict.setStatus("1");
        dict.setValue("正常");
        dictMapper.insert(dict);

        Dict dict1 = new Dict();
        dict1.setStatus("2");
        dict1.setValue("异常");
        dictMapper.insert(dict1);
    }

    /**
     * config5 ：查询操作，只从一个节点获取数据
     * 随机负载均衡规则
     *
     * config7： 查询 从库数据 m2
     */
    @Test
    void queryDict(){
        List<Dict> dicts = dictMapper.selectList(new QueryWrapper<>());
        for (Dict dict : dicts) {
            System.out.println(dict);
        }
    }

    /**
     * 对于广播表(字典表)的，联表查询操作
     */
    @Test
    void queryUserInfo(){
        List<User> users = userMapper.selectUserList();
        for (User user : users) {
            System.out.println(user);
        }
    }

    //================ config6 =========================================================================================


    @Test
    void addUser(){
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("小明" + i);
            user.setUage(18+i);
            user.setUstatus(String.valueOf(i%2+1));
            userMapper.insert(user);
        }
    }

    /**
     * 关联表，联表查询
     */
    @Test
    void queryUser(){
        List<User> users = userMapper.selectUserList();
        for (User user : users) {
            System.out.println(user);
        }
    }

}

























