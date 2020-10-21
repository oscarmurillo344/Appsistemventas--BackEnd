package com.tutorial.crud;

import com.tutorial.crud.security.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudApplication {

	@Autowired
	RolService rolservice;

	public static void main(String[] args) {

		SpringApplication.run(CrudApplication.class, args);

	}

}
