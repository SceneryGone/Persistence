package com.future.config;

import com.future.pojo.Configuration;
import com.future.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * 功能描述:
 *
 * @author future
 * @date 2021-07-31 15:38
 */
public class XmlMapperBuilder {

    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parseConfig(InputStream inputStream) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);

        // 解析sqlMapConfig.xml
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> elementList = rootElement.selectNodes("//select");
        for (Element element : elementList) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String paramType = element.attributeValue("paramType");
            String sqlText = element.getTextTrim();

            final MappedStatement mappedStatement = MappedStatement.builder()
                    .id(id)
                    .resultType(resultType)
                    .paramType(paramType)
                    .sql(sqlText)
                    .build();

            String statementId = namespace + "." + id;
            configuration.getMappedStatementMap().put(statementId, mappedStatement);
        }
    }
}
