package org.grupo1.tienda.model.validation;

import org.grupo1.tienda.model.entity.Usuario;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContrasenaValidator implements ConstraintValidator<ClavesIguales, Usuario> {


    @Override
    public void initialize(ClavesIguales clavesIguales) {

    }

    public boolean isValid(Usuario usuario, ConstraintValidatorContext constraintValidatorContext) {
        return usuario.getClave().equals(usuario.getConfirmarClave());
    }
}
