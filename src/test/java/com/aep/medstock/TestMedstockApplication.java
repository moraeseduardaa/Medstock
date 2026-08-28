package com.aep.medstock;

import org.springframework.boot.SpringApplication;

public class TestMedstockApplication {

	public static void main(String[] args) {
		SpringApplication.from(MedstockApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
