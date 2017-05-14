package ca.technologieslsm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableConfigurationProperties
public class PollyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollyApplication.class, args);
	}
}
