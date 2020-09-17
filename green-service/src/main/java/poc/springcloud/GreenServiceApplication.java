package poc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class GreenServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenServiceApplication.class, args);
	}

}

@RestController
class GreenRestController {

	@GetMapping("/")
	public String hello() {
		return "<h2 style='color: Green;'>Default page</h2>";
	}

	@GetMapping("/green-service")
	public String helloGreen() {
		return "<h2 style='color: Green;'>Hello from Green service</h2>";
	}
}