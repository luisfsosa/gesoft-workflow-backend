package cl.com.gesoft.security.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) // can use in method only.
public @interface GesoftAcl {
    public String name() default "";

    public String expression() default "";

    public boolean ignore() default false;
}
