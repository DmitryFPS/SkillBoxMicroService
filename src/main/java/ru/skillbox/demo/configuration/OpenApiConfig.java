package ru.skillbox.demo.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "SkillBoxAppi Swagger",
                description = "Some kind of description", version = "1.0.0",
                contact = @Contact(
                        name = "Orlov Dmitriy",
                        email = "ddd12@mail.ru",
                        url = "https://other.net"
                )
        )
)
public class OpenApiConfig {
}
