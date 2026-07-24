package velaire.ecommmerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EcommmerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommmerceApplication.class, args);
	}

}
