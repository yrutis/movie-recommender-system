package ch.uzh.ifi.seal.ase.mrs.memberservice;

import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MemberServiceApplication {

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(MemberServiceApplication.class, args);
	}

}
