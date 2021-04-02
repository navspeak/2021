package com.navspeak.ldapdemo;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.directory.Attributes;
import java.util.List;
import java.util.Scanner;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

//@SpringBootApplication
public class LdapdemoApplication_old {

	//@Autowired
	private PersonRepository_old personRepository;
	//@Autowired
	private ConfigurableApplicationContext context;

	public static void main(String[] args) {
		final ConfigurableApplicationContext run = SpringApplication.run(LdapdemoApplication_old.class, args);
		Scanner sc= new Scanner(System.in);
		System.out.println();
		System.out.print("Enter any key to exit ");
		String str= sc.nextLine();
		run.close();
	}

	//@Bean
	public CommandLineRunner cmdline(LdapdemoApplication_old app) {
		return args -> {
			for (int i = 0; i < args.length; ++i) {
				System.out.printf("args[%d]: %s\n", i, args[i]);
			}
			List<Person_old> persons = personRepository.getAllPersons();
			persons.forEach(p-> System.out.printf("First Name: %s Last Name %s\n",
					p.getFullName(), p.getLastName()));

			//personRepository.update();
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
class PersonRepository_old {
	@Autowired
	private LdapTemplate ldapTemplate;
	/**
	 * Retrieves all the persons in the ldap server
	 * @return list of person names
	 */
	List<String> getAllPersonNames() {
		return ldapTemplate.search(
				query().where("objectclass").is("person"),
				(Attributes attributes) -> {
					return (String) attributes.get("cn").get();
				});
	}

	List<Person_old> getAllPersons() {
		return ldapTemplate.search(
				query().where("objectclass").is("person"),
				(Attributes attributes) -> {
					return Person_old.builder()
							.fullName((String)attributes.get("cn").get())
							.lastName((String)attributes.get("sn").get())
							.build();
				});

	}

	void update() {
		Name dn = LdapNameBuilder.newInstance()
				.add("dc", "com")
				.add("dc", "navspeak")
				.add("ou", "people")
				.add("uid", "cersei")
				.build();
		DirContextOperations context
				= ldapTemplate.lookupContext(dn);
//https://www.baeldung.com/spring-ldap
//		context.setAttributeValues
//				("objectclass",
//						new String[]
//								{ "top",
//										"person",
//										"organizationalPerson",
//										"inetOrgPerson" });
		context.setAttributeValue("cn", "Cersei");
		ldapTemplate.modifyAttributes(context);
	}
}


@Builder
@Data
class Person_old {
	String fullName;
	String lastName;
	String uid;
}


