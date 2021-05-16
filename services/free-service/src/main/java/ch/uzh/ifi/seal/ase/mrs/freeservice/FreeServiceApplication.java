package ch.uzh.ifi.seal.ase.mrs.freeservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class FreeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreeServiceApplication.class, args);
	}

}
