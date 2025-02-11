package com.sidn.metruyenchu.novelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NovelServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NovelServiceApplication.class, args);
	}

}
