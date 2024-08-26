package ifg.midas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"ifg.midas.domain.client", "ifg.midas.domain.commodity", "ifg.midas.domain.token",
							"ifg.midas.domain.site", "ifg.midas.domain.strategy"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
