package com.example.springmvcrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.springmvcrest.api.v1.mapper"})
public class SpringMvcRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcRestApplication.class, args);
	}

}
