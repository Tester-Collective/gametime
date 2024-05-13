package id.ac.ui.cs.advprog.gametime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "id.ac.ui.cs.advprog.gametime")
@EnableCaching
public class GametimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GametimeApplication.class, args);
	}

}
