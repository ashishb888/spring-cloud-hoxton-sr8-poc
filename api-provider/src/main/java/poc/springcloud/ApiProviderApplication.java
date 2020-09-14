package poc.springcloud;

import java.time.Duration;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class ApiProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiProviderApplication.class, args);
	}

}

@RestController
class RandomNumberRestController {

	Random random = new Random();

	@GetMapping(path = "/random-number", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<Integer> randomNumber() {
		return Flux.interval(Duration.ofSeconds(3)).map(i -> random.nextInt(100));
	}
}