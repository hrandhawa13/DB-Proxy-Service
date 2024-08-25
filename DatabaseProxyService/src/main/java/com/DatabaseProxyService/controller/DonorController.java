package com.databaseproxyservice.controller;

import com.databaseproxyservice.service.ConnectionPoolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/db")
public class DatabaseOperationController {

    private final ConnectionPoolService connectionPoolService;

    public DatabaseOperationController(ConnectionPoolService connectionPoolService) {
        this.connectionPoolService = connectionPoolService;
    }

    @GetMapping("/query")
    public ResponseEntity<String> executeQuery(@RequestParam String sql, @RequestParam String tenant ) {
        StringBuilder result = new StringBuilder();
        
        try (Connection connection = connectionPoolService.getConnection(tenant);
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
            return ResponseEntity.status(500).body("Error executing query: " + e.getMessage());
        }

        return ResponseEntity.ok(result.toString());
    }
}
