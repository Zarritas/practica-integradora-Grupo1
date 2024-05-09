package org.grupo1.tienda.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PreguntaClaveValidator.class)
@Documented
public @interface PreguntaClave {
    String message() default "{PreguntaClave.usuario}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
