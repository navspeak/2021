package guru.springframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootMongodbApplication {

	public static void main(String[] args) {

		final ApplicationContext ctx = SpringApplication.run(SpringBootMongodbApplication.class, args);
//		for(String name : ctx.getBeanDefinitionNames()){
//			System.out.println(name);
//		}
		System.out.println(ctx.getBeanDefinitionCount());
	}
}

