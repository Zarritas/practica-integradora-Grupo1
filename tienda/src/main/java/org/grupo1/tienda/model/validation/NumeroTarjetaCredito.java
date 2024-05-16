package org.grupo1.tienda.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumeroTarjetaCreditoValidator.class)
public @interface NumeroTarjetaCredito {
    String message() default "{NumeroTarjetaCredito.cliente}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}