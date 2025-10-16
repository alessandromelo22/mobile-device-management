package com.alessandromelo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo(){

        return new OpenAPI()
                .info(new Info()
                        .title("API Sistema de Gerenciamento de Dispositivos Móveis (MDM)")
                        .version("1.0.0")
                        .description("API que permite o gerenciamento de Departamentos, Usuarios, Dispositivos, Agentes e Comandos")
                        .contact(new Contact()
                                .name("Alessandro Melo")
                                .url("https://www.linkedin.com/in/alessandro-melo-dev/")));
    }
}
