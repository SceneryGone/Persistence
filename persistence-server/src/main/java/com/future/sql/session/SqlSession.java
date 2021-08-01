package com.future.sql.session;

import java.util.List;

/**
 * @author future
 */
public interface SqlSession {

    <T> List<T> selectList(String statementId, Object... params) throws Exception;

    <T> T selectOne(String statementId, Object... params) throws Exception;

    /**
     * 为Dao接口生成代理实现类
     */
    <T> T getMapper(Class<?> mapperClass);
}
