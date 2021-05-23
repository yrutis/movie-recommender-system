package ch.uzh.ifi.seal.ase.mrs.freeservice;

import ch.uzh.ifi.seal.ase.mrs.freeservice.client.TmdbClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableFeignClients
@Slf4j
@EnableCaching
public class FreeServiceApplication {
	@Autowired
	private TmdbClient tmdbClient;

	public static void main(String[] args) {
		SpringApplication.run(FreeServiceApplication.class, args);
	}

	@PostConstruct
	void test() {
		this.tmdbClient.getWatchProvider("512200", "a5145ac122f91fd68d0a29a7b75822b1");
	}
}
