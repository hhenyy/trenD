package com.td.TrenD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.td.TrenD.model")
public class TrenDApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrenDApplication.class, args);
	}

}
