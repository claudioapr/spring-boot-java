package br.com.claudio.rest.client;

import br.com.claudio.rest.client.service.CVApplicationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestClientApplication {

	private CVApplicationService cvApplicationService;

	public RestClientApplication(
			CVApplicationService cvApplicationService) {
		this.cvApplicationService = cvApplicationService;
	}

	public static void main(String[] args) {
		SpringApplication.run(RestClientApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {

			cvApplicationService.sendCV(restTemplate);
		};
	}


}
