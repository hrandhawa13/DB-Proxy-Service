package com.databaseproxyservice.config;

import com.databaseproxyservice.properties.DatabaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

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
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl(props.getUrl());
            dataSource.setUsername(props.getUsername());
            dataSource.setPassword(props.getPassword());
            dataSource.setDriverClassName(props.getDriverClassName());

            dataSources.put(key, dataSource);
        });

        return dataSources;
    }
}
