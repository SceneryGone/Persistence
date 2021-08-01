package com.future.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述:
 *
 * @author future
 * @date 2021-07-31 14:38
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MappedStatement {

    /**
     * id
     */
    private String id;

    /**
     * 返回值类型
     */
    private String resultType;

    /**
     * 参数类型
     */
    private String paramType;

    /**
     * sql 语句
     */
    private String sql;
}
