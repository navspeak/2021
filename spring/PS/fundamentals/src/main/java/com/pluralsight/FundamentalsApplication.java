package com.pluralsight;

import com.pluralsight.actuator.PeopleHealthIndicator;
import com.pluralsight.entity.Application;
import com.pluralsight.repository.ApplicationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan()
public class FundamentalsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundamentalsApplication.class, args);
		System.out.println("Hello PS");
	}

	@Bean
	public HealthIndicator getPeopleHealthIndicator(){
		return new PeopleHealthIndicator();
	}

//	@Bean
//	public CommandLineRunner demo(ApplicationRepository repository){
//		return args -> {
//			repository.save(new Application("Trackzilla","kesha.williams","Application for tracking bugs."));
//			repository.save(new Application("Expenses","mary.jones","Application to track expense reports."));
//			repository.save(new Application("Notifications","karen.kane","Application to send alerts and notifications to users."));
//
//			repository.findAll().forEach(System.out::println);
//		};
//	}

}
