package com.future.test;

import com.future.dao.UserDao;
import com.future.db.User;
import com.future.io.Resources;
import com.future.sql.session.SqlSession;
import com.future.sql.session.SqlSessionFactory;
import com.future.sql.session.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Assert;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

/**
 * 功能描述:
 *
 * @author future
 * @date 2021-07-31 14:32
 */
public class PersistenceTest {

    @Test
    public void testDao() throws PropertyVetoException, DocumentException {
        // 读取xml文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        Assert.assertNotNull(resourceAsStream);

        // 生成SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        Assert.assertNotNull(sqlSessionFactory);

        // 获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Assert.assertNotNull(sqlSession);

        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> users = userDao.selectAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testPersistence() throws Exception {

        // 读取xml文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        Assert.assertNotNull(resourceAsStream);

        // 生成SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        Assert.assertNotNull(sqlSessionFactory);

        // 获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Assert.assertNotNull(sqlSession);

        // 执行逻辑
        User user = new User();
        user.setId(1);
        user.setUsername("future");
        List<User> users = sqlSession.selectList("user.selectAll", user);
        for (User u : users) {
            System.out.println(u);
        }
    }

}
