package ch.uzh.ifi.seal.ase.mrs.freeservice;

import ch.uzh.ifi.seal.ase.mrs.freeservice.client.TmdbClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class FreeServiceApplication {

	@Autowired
	TmdbClient tmdbClient;

	public static void main(String[] args) {
		SpringApplication.run(FreeServiceApplication.class, args);
	}

	@PostConstruct
	void test() {
		log.info(tmdbClient.getMovie("61537","a5145ac122f91fd68d0a29a7b75822b1").toString());
	}
}
