package com.codeWithhHemant.blog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI blogifyOpenAPI() {

        // Define Security Scheme for JWT
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // Apply Security Requirement (global for all APIs)
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearerAuth");

        // Build OpenAPI object with security + info section
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .addSecurityItem(securityRequirement)
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Blogify API")
                        .version("1.0")
                        .description("Detailed API documentation for Blogify And this Application is Developed by Hemant Sharma")
                        .contact(new Contact()
                                .name("Hemant Sharma")
                                .email("hemant@example.com")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")
                        )
                );
    }
}