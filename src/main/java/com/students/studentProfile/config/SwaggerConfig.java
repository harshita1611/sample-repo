package com.students.studentProfile.config;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Student Profile API")
                        .version("1.0")
                        .description("API for managing student profiles"));
    }
    @Bean
    public OpenApiCustomizer removePageSchema() {
        return openApi -> {
            if (openApi.getComponents() != null && openApi.getComponents().getSchemas() != null) {
                // Remove specific pageable-related schemas
                openApi.getComponents().getSchemas().remove("Page");
                openApi.getComponents().getSchemas().remove("PageReport");
                openApi.getComponents().getSchemas().remove("PageableObject");
                openApi.getComponents().getSchemas().remove("PagePatient");
                openApi.getComponents().getSchemas().remove("PageAppointment");
                openApi.getComponents().getSchemas().remove("PageDoctor");
                openApi.getComponents().getSchemas().remove("PageDepartment");
                openApi.getComponents().getSchemas().remove("SortObject");
            }
        };
    }
}