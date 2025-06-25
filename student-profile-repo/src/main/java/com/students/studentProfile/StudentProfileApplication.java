package com.students.studentProfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration.class,
		org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
		org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration.class
})
//@ComponentScan(basePackages = "com.students.studentProfile.config")
public class StudentProfileApplication {
	public static void main(String[] args) {
		SpringApplication.run(StudentProfileApplication.class, args);
	}
}
