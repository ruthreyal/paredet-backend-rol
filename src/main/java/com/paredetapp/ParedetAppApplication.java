package com.paredetapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableJpaRepositories("com.paredetapp.repository")
@EntityScan("com.paredetapp.model")
@ComponentScan(basePackages = {"com.paredetapp"})  // 🔥 Asegura que se escaneen todos los componentes
public class ParedetAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(ParedetAppApplication.class, args);
	}
}



