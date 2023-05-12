package kz.ulank.strongteamnewsportal.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Created by Ulan on 5/12/2023
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Ulan",
                        email = "ulankdt@gmail.com",
                        url = "https://github.com/ulank"
                ),
                description = "News Portal API Documentation",
                title = "News Portal API",
                version = "1.0",
                license = @License(
                        name = "Strong Team",
                        url = "https://strongte.am/"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local Environment",
                        url = "http://localhost:8089"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearer-token"
                )
        }
)
@SecurityScheme(
        name = "bearer-token",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}
