package com.blog.Blogging_Application_API;

import com.blog.Blogging_Application_API.config.AppConstants;
import com.blog.Blogging_Application_API.entities.Role;
import com.blog.Blogging_Application_API.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BloggingApplicationApiApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;




	public static void main(String[] args) {

		SpringApplication.run(BloggingApplicationApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(this.passwordEncoder.encode("xyz"));

		try {

			Role role = new Role();
			role.setId(AppConstants.ROLE_ADMIN);
			role.setName("ROLE_ADMIN");


			Role role1 = new Role();
			role1.setId(AppConstants.ROLE_NORMAL);
			role1.setName("ROLE_NORMAL");

			List<Role> roles = List.of(role , role1);

			List<Role> result = this.roleRepo.saveAll(roles);

			result.forEach(r -> {
				System.out.println(r.getName());
			});
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
