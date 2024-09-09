package com.databaseproxyservice.config;

import com.databaseproxyservice.properties.DatabaseProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DynamicDataSourceConfig {

    private final DatabaseProperties databaseProperties;

    public DynamicDataSourceConfig(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    @Bean
    @Primary
    public Map<String, DataSource> dataSources() {
        Map<String, DataSource> dataSources = new HashMap<>();
        databaseProperties.getDb().forEach((key, props) -> {
            BasicDataSource  dataSource = new BasicDataSource ();
            dataSource.setUrl(props.getUrl());
            dataSource.setUsername(props.getUsername());
            dataSource.setPassword(props.getPassword());
            dataSource.setDriverClassName(props.getDriverClassName());
            dataSource.setInitialSize(props.getInitialSize());
            dataSource.setMaxTotal(props.getMaxTotal());
            dataSource.setMaxIdle(props.getMaxIdle());
            dataSource.setMinIdle(props.getMinIdle());

            dataSources.put(key, dataSource);
        });
        return dataSources;
    }
}
