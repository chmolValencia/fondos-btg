// src/main/java/com/fondos_btg/config/SwaggerConfig.java
package com.example.fondos_btg.fondos_btg.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración para habilitar Swagger UI (OpenAPI 3) en la aplicación.
 * Permite documentar y probar los endpoints REST desde una interfaz web.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Fondos BTG Pactual")
                        .version("1.0.0")
                        .description("Sistema para gestionar suscripciones, cancelaciones y historial de fondos de inversión (FPV y FIC).")
                        .termsOfService("http://btgpactual.com/terms")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Soporte BTG Pactual")
                                .email("soporte@btgpactual.com"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Licencia MIT")
                                .url("http://opensource.org/licenses/MIT")));
    }
}