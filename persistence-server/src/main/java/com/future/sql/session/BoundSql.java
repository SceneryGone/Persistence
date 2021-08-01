package com.future.sql.session;

import com.future.utils.ParameterMapping;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 功能描述:
 *
 * @author future
 * @date 2021-07-31 16:59
 */
@Data
public class BoundSql {

    private String sqlText;

    private List<ParameterMapping> parameterMappingList = Lists.newArrayList();

    public BoundSql(String sqlText, List<ParameterMapping> parameterMappingList) {
        this.sqlText = sqlText;
        this.parameterMappingList = parameterMappingList;
    }
}
