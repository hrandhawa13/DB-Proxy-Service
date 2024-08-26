package com.databaseproxyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseClientService {

    private final ConnectionPoolService connectionPoolService;

    @Autowired
    public DatabaseClientService(ConnectionPoolService connectionPoolService) {
        this.connectionPoolService = connectionPoolService;
    }
// TODO Log the sql, tenantId, Result for monitoring/logging
    public StringBuilder executeReadQuery(String sql, String tenantId){
        StringBuilder result = new StringBuilder();
// TODO Extract the functionality of prepping and executing sql into 1 method
// TODO Extract the functionality of parsing result into 1 method
// TODO This method should return JSON instead of string builder
        try (Connection connection = connectionPoolService.getConnection(tenantId);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Process each row of the result set
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    result.append(columnName).append(": ").append(columnValue).append("\n");
                }
                result.append("\n"); // Separate rows by a newline
            }
        } catch (SQLException e) {
            return new StringBuilder("Error executing query: " + e.getMessage());
        }
        return result;
    }

    public void executeReadQueryForSingleResult(String sql, String tenantId){
        //TODO This method should only return 1 row from Database
    }
}
