package com.projetoFinalWeb.projetoWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProjetoWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoWebApplication.class, args);
	}

}
