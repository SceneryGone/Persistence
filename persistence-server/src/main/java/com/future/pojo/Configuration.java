package com.future.pojo;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述:
 *
 * @author future
 * @date 2021-07-31 14:38
 */
@Data
public class Configuration {

    /**
     * 数据源
     */
    private DataSource dateSource;

    /**
     * key:statementId:namespace+id value:MappedStatement
     */
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

}
