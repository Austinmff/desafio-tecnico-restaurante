package br.comdbserver.almocovotacao.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Almoço Votação API")
                        .version("1.0")
                        .description("API para votação diária de restaurantes - desafio DBServer")
                        .contact(new Contact().name("Austin Farias").email("austinmff@outlook.com")));
    }
}
