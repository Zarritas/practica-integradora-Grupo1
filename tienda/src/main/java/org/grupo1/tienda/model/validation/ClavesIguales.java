package org.grupo1.tienda.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ContrasenaValidator.class)
@Documented
public @interface ClavesIguales {
    String message() default "{ClavesIguales.usuario}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
