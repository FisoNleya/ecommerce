package com.manica.productscatalogue;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;

@EnableFeignClients
@SpringBootApplication
public class ProductsCatalogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsCatalogueApplication.class, args);
	}

	@PostConstruct
	public void init(){
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone( "GMT+2"));
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
