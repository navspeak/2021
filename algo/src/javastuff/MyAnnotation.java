package java;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
//@Target(ElementType.PARAMETER)
//@Target(ElementType.CONSTRUCTOR)
//@Target(ElementType.METHOD)
//@Target(ElementType.FIELD)
@Inherited // if we want subclass of annotated class to inherit the annotation
@Documented // JavaDoc will include all classes annotated with this
@Retention(RetentionPolicy.SOURCE) // info of the usage is not available in runtime or in class file
//@Retention(RetentionPolicy.CLASS) // info is also available in class file. You will see it using a decomipler also
                                    // but will not be available at runtime
//@Retention(RetentionPolicy.RUNTIME) // can access via Reglection

//@Retention(RetentionPolicy.RUNTIME)
//@Target(value={CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, MODULE, PARAMETER, TYPE})
public @interface MyAnnotation {
    String name() default "";
    int count() default 1;
    String[] tags() default {"proactive","expert"};
}

@MyAnnotation(name="Navneet", count = 1, tags= {"procrastinator", "dabbler"})
class MyAnnotationExample {
    //@MyAnnotation // compiler error if @Target(ElementType.TYPE)
    void doIt(/*@MyAnnotation - valid if @Target(ElementType.PARAMETER) */
    String message) {

    }
}