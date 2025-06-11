package com.students.studentProfile.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;

@Configuration
public class DotenvConfig {

    @PostConstruct
    public void loadEnvVariables() {
        Dotenv dotenv = Dotenv.load();
        // Set environment variables from .env file into the Spring environment
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD", "defaultPassword"));
        // Optionally log or handle the loaded values
        System.out.println("DB_PASSWORD loaded from .env file: " + dotenv.get("DB_PASSWORD"));
    }
}
