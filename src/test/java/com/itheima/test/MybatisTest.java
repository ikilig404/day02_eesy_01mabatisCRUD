package com.itheima.test;

import com.itheima.dao.IUserDao;
import com.itheima.domain.QueryVo;
import com.itheima.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * mybatis的入门案例
 */
public class MybatisTest {

    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao userDao;

    @Before  // 用于在测试方法执行之前执行
    public void init() throws Exception{
        //1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //3.使用工厂生产SqlSession对象
        sqlSession = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After // 用于在测试方法执行之后执行
    public void destroy() throws Exception {

        // 提交事物
        sqlSession.commit();

        //6.释放资源
        sqlSession.close();
        in.close();
    }

    /**
     * 测试查询所有
     *
     * @param
     */
    @Test
    public void testFindAll() throws Exception {

        //5.使用代理对象执行方法
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }

    }

    /**
     * 测试保存操作
     */
    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("mybatis last insertid");
        user.setAddress("北京市顺义区");
        user.setSex("男");
        user.setBirthday(new Date());
        System.out.println("保存操作之前"+user);

        //5.使用代理对象执行方法
        userDao.saveUser(user);

        System.out.println("保存操作之后"+user);

    }

    /**
     * 测试更新操作
     */
    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(50);
        user.setUsername("mybatis Update user");
        user.setAddress("北京市顺义区");
        user.setSex("女");
        user.setBirthday(new Date());

        //5.使用代理对象执行方法
        userDao.updateUser(user);
    }

    /**
     * 根据id删除
     */
    @Test
    public void testDelete() {
        //5.使用代理对象执行方法
        userDao.deleteUser(51);
    }

    /**
     * 根据id查询用户
     */
    @Test
    public void testFindOne() {
        //5.使用代理对象执行方法
        User user = userDao.findById(50);
        System.out.println(user);
    }

    /**
     * 测试模糊查询操作
     */
    @Test
    public void testFindByName() {
        //5.使用代理对象执行方法
        List<User> users = userDao.findByName("%王%");
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 测试查询总记录条数
     */
    @Test
    public void testFindTotal() {
        //5.使用代理对象执行方法
        int count = userDao.findTotal();
        System.out.println(count);
    }

    /**
     * 测试使用QueryVo作为查询条件
     */
    @Test
    public void testFindByVo() {
        QueryVo vo = new QueryVo();
        User user = new User();
        user.setUsername("%王%");
        vo.setUser(user);
        //5.使用代理对象执行方法
        List<User> users = userDao.findUserByVo(vo);
        for (User u : users) {
            System.out.println(u);
        }
    }
}
