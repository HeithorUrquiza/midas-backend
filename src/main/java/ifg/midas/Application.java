package ifg.midas;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@SpringBootApplication
@EntityScan(basePackages = {"ifg.midas.domain.client", "ifg.midas.domain.commodity", "ifg.midas.domain.token",
							"ifg.midas.domain.site", "ifg.midas.domain.strategy", "ifg.midas.domain.group"})
public class Application {
	sadgdsfgdsfg
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
