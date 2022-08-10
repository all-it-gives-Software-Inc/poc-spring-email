package test.api.email.email.docs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "My API", version = "v1"),
        servers = {@Server(url = "https://api-email-jj.herokuapp.com", description = "used with load balancer + heroku"),
                @Server(url = "http://api-email-jj.herokuapp.com", description = "used heroku without load balancer and localhost"),
                @Server(url = "/", description = "used heroku without load balancer and localhost"),
        })
public class DocsConfig {
}

