package com.vansisto.logosshop;

import com.vansisto.logosshop.domain.UserDTO;
import com.vansisto.logosshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class LogosShopApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(LogosShopApplication.class, args);

//		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//		System.out.println(passwordEncoder.encode("pass"));
	}

	@Override
	public void run(String... args) throws Exception {
		UserDTO user = new UserDTO();
		user.setEmail("user");
		user.setPassword(passwordEncoder.encode("user"));
		userService.create(user);

		UserDTO admin = new UserDTO();
		admin.setEmail("admin");
		admin.setPassword(passwordEncoder.encode("admin"));
		userService.create(admin);
	}
}
