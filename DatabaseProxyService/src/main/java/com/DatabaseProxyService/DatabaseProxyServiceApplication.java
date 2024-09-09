package com.databaseproxyservice;

import com.databaseproxyservice.properties.DatabaseProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DatabaseProperties.class)
public class DatabaseProxyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseProxyServiceApplication.class, args);
	}

}
