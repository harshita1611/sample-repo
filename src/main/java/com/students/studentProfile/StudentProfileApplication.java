package com.students.studentProfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration.class
})
public class StudentProfileApplication {
	public static void main(String[] args) {
		SpringApplication.run(StudentProfileApplication.class, args);
	}
}

