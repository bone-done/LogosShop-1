package com.vansisto.logosshop;

import com.vansisto.logosshop.service.RoleService;
import com.vansisto.logosshop.service.UserService;
import com.vansisto.logosshop.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class LogosShopApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ModelMapperUtil mapper;

	public static void main(String[] args) {
		SpringApplication.run(LogosShopApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
////		TODO: Remove it
//		RoleDTO adminRole = new RoleDTO();
//		adminRole.setName("ADMIN");
//		roleService.create(adminRole);
//
//		RoleDTO userRole = new RoleDTO();
//		userRole.setName("USER");
//		roleService.create(userRole);
//
//		UserDTO user = new UserDTO();
//		user.setEmail("user");
//		user.setPassword("user");
//		UserDTO saved = userService.create(user);
//
//		UserDTO admin = new UserDTO();
//		admin.setEmail("admin");
//		admin.setPassword("admin");
//		UserDTO createdUserAdmin = userService.create(admin);
//		userService.attachRoleToUserById("ADMIN", createdUserAdmin.getId());
	}
}
