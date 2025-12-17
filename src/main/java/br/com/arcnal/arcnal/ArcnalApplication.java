package br.com.arcnal.arcnal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ArcnalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArcnalApplication.class, args);
	}

}
