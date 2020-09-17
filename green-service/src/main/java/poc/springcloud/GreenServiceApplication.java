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
		return "<b>Hello from Green service</b>";
	}
}