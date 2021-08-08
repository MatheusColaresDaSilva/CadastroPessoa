package com.elotech.cadastropessoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.elotech.*")
public class CadastropessoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastropessoaApplication.class, args);
		System.out.println("Iniciado");
	}

}
