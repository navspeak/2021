* https://www.youtube.com/watch?v=xlWwMSu5I70&list=PLC97BDEFDCDD169D7&index=3
* DI, IoC and BeanFactory are basic spring concepts
* What can an IoC Container do?
    * Find Beans, Wire depenencies, manage lifecycle of the beans
    * Types of IoC Container: 
        * BeanFactory  - Very Basic. Lazy Loading. Supports only Prototype & Singleton [Read](https://www.baeldung.com/spring-beanfactory-vs-applicationcontext)
        * ApplicationContext - Eager Loading (so heavy IOC). However recommended. Supports messaging, i18n etc
 ``` java
// using XML
    BeanFactory factory = new XmlBeanFactory(new FileSystemResource("spring.xml"));
    ((StringEncoder)factory.getBean("StringEncoder")).encode("TEST");

    ApplicationContext ctx = new ClasssPathXmlApplicationContext("spring.xml");
    ((StringEncoder)ctx.getBean("StringEncoder")).encode("TEST");

// using Config
@Configuration 
class Test{};
ApplicationContext ctx = new AnnotationConfigApplicationContext(Test.class);

//In unit Test
@RunWith(SpringRunner.class)
@ContextConfiguration(class = Test.class) 
public class XYZ {}

ApplicationContext ctx = SpringApplication.run(SpringmvcApplication.class, args);

//        for (String name : ctx.getBeanDefinitionNames()){
//            System.out.println(name);
//        }
//        System.out.println("******* Bean Count *******");
//        System.out.println(ctx.getBeanDefinitionCount());

 ```
* https://zetcode.com/springboot/applicationcontext/


 
 * BeanPostProcessor: runs before init of ALL or EACH classes 
   
     - @component class DisplayNameBeanPostProcessor implements BeanPostProcessor {
           - Object postProcessBeforeInitialization(Object bean, String beanName ) {return obj after processing}
           - Object postProcessBeforeInitialization(Object bean, String beanName ){ return obj after processing}
           }
           
* @Component (and @Service and @Repository) are used to auto-detect and auto-configure beans using classpath scanning. There's an implicit one-to-one mapping between the annotated class and the bean (i.e. one bean per class). Control of wiring is quite limited with this approach, since it's purely declarative.
* @Bean is used to explicitly declare a single bean, rather than letting Spring do it automatically as above. It decouples the declaration of the bean from the class definition, and lets you create and configure beans exactly how you choose.
* @Component annotation was introduced in spring 2.5 in order to get rid of xml bean definition by using classpath scanning.       
* @Bean was introduced in spring 3.0 and can be used with @Configuration in order to fully get rid of xml file and use java config instead.
* https://stackoverflow.com/questions/10604298/spring-component-versus-bean
* https://www.baeldung.com/spring-component-repository-service

    