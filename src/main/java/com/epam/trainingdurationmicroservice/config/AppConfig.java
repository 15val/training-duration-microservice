package com.epam.trainingdurationmicroservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.tools.Server;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages = "com.epam.trainingdurationmicroservice")
@EnableTransactionManagement
public class AppConfig {

	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2Server() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9095");
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
