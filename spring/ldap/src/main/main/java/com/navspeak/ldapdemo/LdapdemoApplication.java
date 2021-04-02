package com.navspeak.ldapdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.query.LdapQuery;

import javax.naming.directory.Attributes;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@SpringBootApplication(scanBasePackages={"com.navspeak.ldapdemo"})
public class LdapdemoApplication {

	@Autowired
	private UserService userService;
	@Autowired
	private ConfigurableApplicationContext context;

	@Autowired
	private LdapContextSource ldapContextSource;

	@Autowired
	private LdapTemplate ldapTemplate;

	public static void main(String[] args) {
		final ConfigurableApplicationContext run = SpringApplication.run(LdapdemoApplication.class, args);
		Scanner sc= new Scanner(System.in);
		System.out.println();
		System.out.print("Enter any key to exit ");
		String str= sc.nextLine();
		run.close();
	}

	@Bean
	public CommandLineRunner cmdline(LdapdemoApplication app) {
		return args -> {
			for (int i = 0; i < args.length; ++i) {
				System.out.printf("args[%d]: %s\n", i, args[i]);
			}
//
//			List<User> users = ldapTemplate.find(
//					query().base("dc=example,dc=com"),
//					User.class
//			);
//			final List<String> results = new LinkedList<>();
//			LdapQuery query = query()
//					.base("dc=example,dc=com")
//					.attributes("cn")
//					.where("objectclass").is("person")
//					.and("sn").is("Boyle");
//			ldapTemplate.lookup()
//			ldapTemplate.search(query, (Attributes att) -> {
//				results.add(att.get("cn").get().toString());
//				return null;
//			});
//			System.out.println( ((User)lookup));

						List<User> users = userService.search("ein");
			System.out.println(Arrays.toString(users.toArray()));

			//personRepository.update();
		};
	}


//	@Bean
//	public LdapContextSource contextSource(@Value("${ldap.url}") String ldapUrl,
//										   @Value("${ldap.base.dn}") String ldapBaseDn,
//										   @Value("${ldap.username}")String ldapSecurityPrincipal,
//										   @Value("${ldap.password}") String ldapPrincipalPassword ) {
//		LdapContextSource contextSource = new LdapContextSource();
//
//		contextSource.setUrl(ldapUrl);
//		contextSource.setBase(ldapBaseDn);
//		contextSource.setUserDn(ldapSecurityPrincipal);
//		contextSource.setPassword(ldapPrincipalPassword);
//
//		return contextSource;
//	}
//
//	@Bean
//	public LdapTemplate ldapTemplate() {
//		return new LdapTemplate(ldapContextSource);
//	}
}








