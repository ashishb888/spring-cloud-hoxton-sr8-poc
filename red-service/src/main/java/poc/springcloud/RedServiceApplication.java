package poc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RedServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedServiceApplication.class, args);
	}

}

@RestController
class RedRestController {

	@GetMapping("/")
	public String hello() {
		return "<h2 style='color:Red;'>Hello from Red service</h2>";
	}
}