package com.example.tgbot21;

import org.springframework.boot.SpringApplication;

public class TestTgbot21Application {

	public static void main(String[] args) {
		SpringApplication.from(Tgbot21Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
