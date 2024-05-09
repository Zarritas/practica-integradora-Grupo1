package org.grupo1.tienda.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.grupo1.tienda.model.auxiliary.RecuperacionClave;
import org.grupo1.tienda.model.entity.Usuario;
import org.grupo1.tienda.service.ServicioSesion;
import org.springframework.beans.factory.annotation.Autowired;

public class PreguntaClaveValidator implements ConstraintValidator<PreguntaClave, RecuperacionClave> {
    @Autowired
    private ServicioSesion servicioSesion;

    public boolean isValid(RecuperacionClave recuperacionClave, ConstraintValidatorContext constraintValidatorContext) {
        return servicioSesion.getListaPreguntasRecuperacion().contains(recuperacionClave);
    }
}
