package org.grupo1.tienda.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TelefonoMovilValidator.class)
public @interface TelefonoMovil {
    String message() default "El número de teléfono debe contener exactamente 9 dígitos.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}