package com.future.sql.session;

import com.future.config.XmlConfigBuilder;
import com.future.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * 功能描述:
 *
 * @author future
 * @date 2021-07-31 14:55
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream in) throws DocumentException, PropertyVetoException {
        // 使用dom4j 解析配置文件 解析出来的内容封装到Configuration
        Configuration configuration = new XmlConfigBuilder().parseConfig(in);

        // 创建SqlSessionFactory对象,主要功能就是生产SqlSession的：会话对象。
        return new DefaultSqlSessionFactory(configuration);
    }

}
