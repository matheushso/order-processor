package br.com.processor.order.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI costumOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("Order Processor")
                    .description("Documentação da aplicação responsável por postar mensagens em um tópico Kafka referente a pedidos de um marketplace, " +
                            "visando gerar massa de dados e simular comunicação com uma aplicação externa. O principal objetivo da aplicação, no entanto, " +
                            "é o consumo desses pedidos gerados pelo marketplace e seu armazenamento no banco de dados, possibilitando, assim, a filtragem " +
                            "dos pedidos através de uma API para facilitar uma extração de dados eficiente.")
                    .version("v1"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
