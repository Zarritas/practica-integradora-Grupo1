package org.grupo1.tienda.model.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.grupo1.tienda.model.auxiliary.TarjetaCredito;
import org.grupo1.tienda.model.entity.Cliente;

public class NumeroTarjetaCreditoValidator implements ConstraintValidator<NumeroTarjetaCredito, TarjetaCredito> {

    @Override
    public boolean isValid(TarjetaCredito tarjetaCredito, ConstraintValidatorContext context) {
       switch (tarjetaCredito.getTipoTarjetaCredito().getDenominacion()){
           case "VISA":
              if(tarjetaCredito.getNumeroTarjeta().matches("^4[0-9]{16}$")){
                  return false;
              }
               break;

           case"Master Card":
               if(tarjetaCredito.getNumeroTarjeta().matches("^(5[1-5]|222[1-9]|22[3-9]|2[3-6]|27[01]|2720)[0-9]{16}$")){
                   return false;
               }
               break;
           case "American Express":
               if(tarjetaCredito.getNumeroTarjeta().matches("^3[47][0-9]{16}$")){
                   return false;
               }
               break;
       }
        return true;
    }
}