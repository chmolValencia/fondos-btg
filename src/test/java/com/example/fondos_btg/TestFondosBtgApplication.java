package com.example.fondos_btg;

import org.springframework.boot.SpringApplication;

public class TestFondosBtgApplication {

	public static void main(String[] args) {
		SpringApplication.from(FondosBtgApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
