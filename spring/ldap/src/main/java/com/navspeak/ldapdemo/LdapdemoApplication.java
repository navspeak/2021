package com.navspeak.ldapdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@SpringBootApplication
public class LdapdemoApplication {

	@Autowired
	private PersonRepository personRepository;

	public static void main(String[] args) {
		SpringApplication.run(LdapdemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(LdapdemoApplication app) {
		return args -> {
			System.out.println(args);
			List<String> names = personRepository.getAllPersonNames();
			System.out.println("names: " + names);
		};
	}

//	@PostConstruct
//	public void setup(){
//		System.out.println("Spring LDAP + Spring Boot Configuration Example");
//
//		List<String> names = personRepository.getAllPersonNames();
//		System.out.println("names: " + names);
//
//		System.exit(-1);
//	}
}

@Service
class PersonRepository {
	@Autowired
	private LdapTemplate ldapTemplate;
	/**
	 * Retrieves all the persons in the ldap server
	 * @return list of person names
	 */
	public List<String> getAllPersonNames() {
		return ldapTemplate.search(
				query().where("objectclass").is("person"),
				new AttributesMapper<String>() {
					public String mapFromAttributes(Attributes attrs)
							throws NamingException {
						return (String) attrs.get("cn").get();
					}
				});
	}

}
