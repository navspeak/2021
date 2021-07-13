* @Autowire - if you autowire an Interface
* You can use @Qualifier to autowire the impl you want
* Or you can use @Primary and that will be picked in case of multi impl
* Or name the object as the impl class and Spring with autowire (Autowire by Name)
* setter injection vs ctor injection: ctor inj is mandatory injection. Other is not. https://www.youtube.com/watch?v=2YC5pIXR7e4
@Component annotation marks a java class as a bean so the component-scanning mechanism of spring can pick it up and pull it into the application context

@Service - It behaves just like a Component annotation. It doesn’t currently provide any additional behavior over the @Component annotation, 
but it’s a good strate to use @Service over @Component in service-layer classes because it specifies the actual intent of the class better

@Repository - provides a suitable need for DAO layer, it also makes unchecked exceptions thrown in the DAO layer eligible to be translated into Spring DataAccessException

@Controller - marks the class as a Spring Web MVC controller

@RestController combines @Controller and @ResponseBody – which eliminates the need to annotate every request handling method of the controller class with the @ResponseBody annotation

@Configuration - marks that the class contains one or more beans defined inside

@Bean - is used to explicitly declare a single bean, rather than letting Spring do it automatically for us

* @Cofiguration you can specify @componentscan so that all beans will be searched in those pagess mentioned in componentscan
* In springboot we use #SpringBootTest / @SpringBootApplication = @componentscan on package it's there
* The @SpringBootApplication annotation is equivalent to using @Configuration, @EnableAutoConfiguration, and @ComponentScan with their default attributes
* https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/using-boot-using-springbootapplication-annotation.html
* CDI - context & Dependency Injection (Java EE JSR-330)
  - @Inject = @Autowire
  - @Named = @Component & @Qualifier
  - @Singleton 
* Spring supports CDI, and it actually stays ahead of CDI