package ch.uzh.ifi.seal.ase.mrs.memberservice;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MemberServiceApplication {

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(MemberServiceApplication.class, args);
	}

	@PostConstruct
	void setup(){
		String password = "password";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		User u = User.builder().password(hashedPassword).username("admin").build();
		userRepository.save(u);
	}
}
