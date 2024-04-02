package np.com.bigbracktes.jobhive.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenAPIConfig {

    @Value("${csangharsha.openapi.local-url}")
    private String localUrl;

    @Bean
    public OpenAPI myOpenAPI() {

        Server localServer = new Server();
        localServer.setUrl(localUrl);
        localServer.setDescription("Server URL in Local environment");

        Contact contact = new Contact();
        contact.setEmail("sangharsha.chaulagain@gmail.com");
        contact.setName("Sangharsha Chaulagain");
        contact.setUrl("https://csangharsha.github.io");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Job Portal API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints for job portal application.")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(localServer));
    }
}
