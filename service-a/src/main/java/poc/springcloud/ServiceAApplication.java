package poc.springcloud;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.Builder;
import lombok.Data;
import poc.springcloud.SomeData.SomeDataBuilder;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ServiceAApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceAApplication.class, args);
	}

}

@RestController
class ServiceARestController {

	@Autowired
	private DiscoveryClient dc;
	@Autowired
	private WebClient.Builder webClientBuilder;
	@Value("${random.value}")
	private String cityCode;

	@GetMapping("/instances")
	List<?> instances() {
		return dc.getServices().stream().map(serviceId -> dc.getInstances(serviceId)).collect(Collectors.toList());
	}

	@CircuitBreaker(fallbackMethod = "defaultData", name = "fallback")
	@GetMapping(path = "/somedata", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<SomeData> someData() {
		return webClientBuilder.build().get().uri("http://api-provider/random-number").retrieve()
				.bodyToFlux(Integer.class).map(randomNumer -> new SomeDataBuilder().id(randomNumer).cityCode(cityCode)
						.message("All ok!").build());
	}

	@GetMapping(path = "/default-data", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<SomeData> defaultData(Exception e) {
		return Flux.just(new SomeDataBuilder().id(0).cityCode(cityCode).message("Something went wrong!").build());
	}
}

@Configuration
class BeansConfig {

	@Bean
	@LoadBalanced
	WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}

}

@Builder
@Data
class SomeData {
	private int id;
	private String cityCode;
	private String message;
}