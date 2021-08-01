package com.future.sql.session;

import com.future.exception.TooManyResultsException;
import com.future.pojo.Configuration;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 功能描述:
 *
 * @author future
 * @date 2021-07-31 16:08
 */
public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> List<T> selectList(String statementId, Object... params) throws Exception {
        // 对Executor的query方法的调用
        Executor executor = new SimpleExecutor();
        return executor.query(configuration, configuration.getMappedStatementMap().get(statementId), params);
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if (objects.size() == 1) {
            return (T) objects.get(0);
        } else if (objects.size() > 1) {
            throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + objects.size());
        } else {
            return null;
        }
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        // 使用jdk动态代理为dao接口生成代理对象并返回
        return (T) Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, (proxy, method, args) -> {
            // 准备参数：statementId and params:statementId=接口全路径+方法名
            String methodName = method.getName();
            String className = method.getDeclaringClass().getName();
            String statementId = className + "." + methodName;

            // 获取被调用方法的返回值类型
            Type genericReturnType = method.getGenericReturnType();
            if (genericReturnType instanceof ParameterizedType) {
                return selectList(statementId, args);
            } else {
                return selectOne(statementId, args);
            }
        });

    }
}
