package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BullhornApplication {

	public static void main(String[] args) {
		SpringApplication.run(BullhornApplication.class, args);
	}

	@Bean
	public CommandLineRunner run (UserRepository userRepository, RoleRepository roleRepository, MessageRepository messageRepository) throws Exception {
		return (String[] args) -> {
			User user = new User("bart", "bart@domain.com", "bart", "Bart", "Simpson",true);
			Role userRole = new Role("bart", "ROLE_USER");
			Message message = new Message("Not Me", "I didn't do it! Nobody saw me. You can't prove anything",user);

			userRepository.save(user);
			roleRepository.save(userRole);
			messageRepository.save(message);

			User admin = new User("admin","super@domain.com","admin","Super","Man",true);
			Role adminRole = new Role("super","ROLE_ADMIN");
			message = new Message("Mighty Mouse", "Here I come to save the day!",admin);

			userRepository.save(admin);
			roleRepository.save(adminRole);
			messageRepository.save(message);
		};
	}
}
