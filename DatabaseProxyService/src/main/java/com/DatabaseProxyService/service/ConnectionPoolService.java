package com.databaseproxyservice.service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Service
public class ConnectionPoolService {

    private final Map<String, DataSource> dataSources;

    public ConnectionPoolService(Map<String, DataSource> dataSources) {
        this.dataSources = dataSources;
    }

    public Connection getConnection(String dbKey) throws SQLException {
        DataSource dataSource = dataSources.get(dbKey);
        if (dataSource == null) {
            throw new IllegalArgumentException("No datasource found for key: " + dbKey);
        }
        return dataSource.getConnection();
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close(); // Returns the connection to the pool
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
