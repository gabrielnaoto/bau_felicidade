package br.udesc.ceavi.core.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

/**
 *
 * @author Samuel Felício Adriano
 */
public @interface DataBaseContainer {

    DataBaseInfo[] value() default{};

}