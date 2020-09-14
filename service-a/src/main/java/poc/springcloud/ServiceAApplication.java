package poc.springcloud;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/instances")
	List<?> instances() {
		return dc.getServices().stream().map(serviceId -> dc.getInstances(serviceId)).collect(Collectors.toList());
	}
}