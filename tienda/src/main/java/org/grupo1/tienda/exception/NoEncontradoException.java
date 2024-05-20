package org.grupo1.tienda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoEncontradoException extends Exception {
    public NoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
