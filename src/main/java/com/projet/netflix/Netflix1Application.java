package com.projet.netflix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.projet.netflix.entities.User;

@SpringBootApplication
public class Netflix1Application implements CommandLineRunner{
	
	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(Netflix1Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		repositoryRestConfiguration.exposeIdsFor(User.class);//pour que l'id soit afficher pour chaque response

	}
	
//	@Bean
//	public ModelMapper modelMapper()
//	{
//		return new ModelMapper();
//	}
//	
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }


}
