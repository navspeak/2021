* Singleton - only one bean per spring container. Default
* Prototype - New bean created with request (getBean) or reference
* Web-aware Context Bean Scopes:
     - Request - New bean per servlet request
     - Session - New bean per session
     - Global Session - New bean per global HTTP session (portlet context)
     
* ApplicationContextAware  - 
    - MyBean implements ApplicationContextAware { private ApplicationContext ctx = null;
        - void setApplicationContext(Application context) { this.ctx = context; }
        - void myMethod(){ ctx.getbean()...}
    - Similar xxxAware Interface: BeanNameAware (setBeanName)

* Injecting a Prototype in a Singleton - Prototype loses its behavior. Is init'd once only
  1. @service class BeanPrototype (has method getXYZ() {print currentTime} ) autowired in a BeanSingleton
  2. @Service class BeanSingleton { @Autowired BeanPrototype protobean; void getXYZ(){ protobean.getXYZ() }
  3. @Controller class Controller { @Autowired BeanSingleton stonbean
  someMethod() { calls stonbean.getMethod() twice}
  5. Q1: in both calls - same datetime. __So prototype is init'd only once__
* Injecting a Prototype in a Singleton - How to have new Prototype instance?
    1. autowire an applicationContext and getBean() directly - Bad we are bypassing Inversion Of Control
    2. Use @LookUp - https://www.baeldung.com/spring-lookup
    3. Use javax.inject.Provider
  