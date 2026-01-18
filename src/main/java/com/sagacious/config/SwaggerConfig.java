package com.sagacious.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Define JWT security scheme
        SecurityScheme securityScheme = new SecurityScheme()
                .name("BearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Paste your JWT token here (without 'Bearer ')");

        // Apply security requirement globally
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("BearerAuth");

        return new OpenAPI()
                .info(new Info()
                        .title("ToDo Application API")
                        .version("1.0.0")
                        .description("Reactive ToDoApp backend with MongoDB Atlas")
                        .contact(new Contact()
                                .name("Satya Yadav")
                                .email("satya@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .addSecurityItem(securityRequirement)
                .schemaRequirement("BearerAuth", securityScheme);
    }
}