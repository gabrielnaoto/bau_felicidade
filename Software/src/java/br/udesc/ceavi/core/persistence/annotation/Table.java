package br.udesc.ceavi.core.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)

/**
 *
 * @author Samuel Felício Adriano
 */
public @interface Table {

    public String schema() default "";

    public String name();

}