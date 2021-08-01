package com.future.sql.session;

import com.future.pojo.Configuration;
import com.future.pojo.MappedStatement;
import com.future.utils.GenericTokenParser;
import com.future.utils.ParameterMapping;
import com.future.utils.ParameterMappingTokenHandler;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.List;

/**
 * 功能描述:
 *
 * @author future
 * @date 2021-07-31 16:30
 */
public class SimpleExecutor implements Executor {

    @Override
    public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // 注册驱动 获取链接
            DataSource dateSource = configuration.getDateSource();
            connection = dateSource.getConnection();
            // 获取sql语句:select * from user where id = #{id} username = #{username} ==》 select * from user where id = ? username = ?
            String sql = mappedStatement.getSql();
            BoundSql boundSql = boundSql(sql);

            // 获取预处理对象 preparedStatement
            preparedStatement = connection.prepareStatement(boundSql.getSqlText());

            // 设置参数
            String paramType = mappedStatement.getParamType();
            Class<?> paramClass = getClassType(paramType);
            if (paramClass != null) {
                List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
                for (int i = 0; i < parameterMappingList.size(); i++) {
                    ParameterMapping parameterMapping = parameterMappingList.get(i);
                    String content = parameterMapping.getContent();

                    // 反射获取属性值
                    Field declaredField = paramClass.getDeclaredField(content);
                    declaredField.setAccessible(true);
                    Object o = declaredField.get(params[0]);
                    preparedStatement.setObject(i + 1, o);
                }
            }

            // 执行SQL
            ResultSet resultSet = preparedStatement.executeQuery();

            // 封装返回结果集
            String resultType = mappedStatement.getResultType();
            Class<?> resultTypeClass = getClassType(resultType);
            List<Object> objects = Lists.newArrayList();
            if (resultTypeClass != null) {
                while (resultSet.next()) {
                    Object object = resultTypeClass.newInstance();
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        // 获取到字段名以及对应的值
                        String columnName = metaData.getColumnName(i);
                        Object value = resultSet.getObject(columnName);

                        // 反射，根据数据库表和实体对应关系完成封装
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                        Method writeMethod = propertyDescriptor.getWriteMethod();
                        writeMethod.invoke(object, value);
                    }
                    objects.add(object);
                }
            }

            // 结果返回
            return (List<T>) objects;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * 获取参数的类对象
     */
    private Class<?> getClassType(String paramType) throws ClassNotFoundException {
        if (StringUtils.isNotBlank(paramType)) {
            return Class.forName(paramType);
        }
        return null;
    }

    /**
     * 1. 对#{}的解析
     * 2. 使用?代替
     * 3. 解析出{}里面的值进行存储
     */
    private BoundSql boundSql(String sql) {
        // 解析SQL 带?的
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", tokenHandler);
        String parseSql = genericTokenParser.parse(sql);

        // 解析出来的参数名称 id 、 username
        List<ParameterMapping> parameterMappings = tokenHandler.getParameterMappings();

        // 返回BoundSql
        return new BoundSql(parseSql, parameterMappings);
    }

}
