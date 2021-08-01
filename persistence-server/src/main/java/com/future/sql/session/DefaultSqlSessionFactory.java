package com.future.sql.session;

import com.future.pojo.Configuration;

/**
 * 功能描述:
 *
 * @author future
 * @date 2021-07-31 15:57
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }

}
