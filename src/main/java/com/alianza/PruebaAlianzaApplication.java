package com.alianza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.alianza.client")
public class PruebaAlianzaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaAlianzaApplication.class, args);
	}

}
