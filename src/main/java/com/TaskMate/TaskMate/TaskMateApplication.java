package com.TaskMate.TaskMate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskMateApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskMateApplication.class, args);
	}

}
