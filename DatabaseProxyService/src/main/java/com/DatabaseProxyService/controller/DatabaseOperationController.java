package com.databaseproxyservice.controller;

import com.databaseproxyservice.service.DatabaseClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/db")
public class DatabaseOperationController {

    private final DatabaseClientService databaseClientService;

    @Autowired
    public DatabaseOperationController(DatabaseClientService databaseClientService) {
        this.databaseClientService = databaseClientService;
    }

    @GetMapping("/readQuery")
    public ResponseEntity<String> executeReadQuery(@RequestParam String sql, @RequestParam String tenant ) {

        StringBuilder result = new StringBuilder();
        try {
            result = databaseClientService.executeReadQuery(sql, tenant);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error executing query: " + e.getMessage());
        }

        return ResponseEntity.ok(result.toString());
    }
}
