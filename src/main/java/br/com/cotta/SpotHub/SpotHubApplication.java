package br.com.cotta.SpotHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpotHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotHubApplication.class, args);
	}

}
