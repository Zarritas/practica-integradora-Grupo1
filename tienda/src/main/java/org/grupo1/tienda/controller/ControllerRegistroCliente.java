package org.grupo1.tienda.controller;

import jakarta.servlet.http.HttpSession;
import org.grupo1.tienda.model.catalog.*;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.model.entity.grupovalidacion.DatosPersonales;
import org.grupo1.tienda.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControllerRegistroCliente {
    @Autowired
    PaisRepository paisRepository;
    @Autowired
    TipoDocumentoRepository documentoRepository;
    @Autowired
    GeneroRepository generoRepository;
    @Autowired
    DireccionRepository direccionRepository;
    @Autowired
    TipoViaRepository tipoViaRepository;
    @Autowired
    TipoTarjetaRepository tipoTarjetaRepository;

    @ModelAttribute("paises")
    public List<Pais> paises() {
        return paisRepository.findAll();
    }
    @ModelAttribute("tipodocumentos")
    public List<TipoDocumentoCliente> tipoDocumentoClientes() {
        return documentoRepository.findAll();
    }
    @ModelAttribute("generos")
    public List<Genero> tipoGenero() {
    return generoRepository.findAll();
}
    @ModelAttribute("vias")
        public List<TipoVia> tipoVias() {
        return tipoViaRepository.findAll();
    }
    @ModelAttribute("tipotarjetas")
    public List<TipoTarjetaCredito> tipoTarjetas() {
        return tipoTarjetaRepository.findAll();
    }

@GetMapping("/datos-personales")
    public ModelAndView datosPersonales(ModelAndView modelAndView, HttpSession sesionRegistro,
                                        @ModelAttribute("cliente") Cliente cliente) {

    Cliente clienteRegistro = (Cliente) sesionRegistro.getAttribute("cliente");


        modelAndView.setViewName("registro-datos-personales");
        return modelAndView;
    }

    @PostMapping("/datos-personales")
    public ModelAndView datosPersonalesPost(ModelAndView modelAndView, HttpSession sesionRegistro,
                                @Validated(DatosPersonales.class) @ModelAttribute("cliente") Cliente cliente,
                                            BindingResult resultadoVinculacion) {
    if(resultadoVinculacion.hasErrors()){
        modelAndView.setViewName("registro-datos-personales");
    }else{
    modelAndView.setViewName("redirect:/datos-contacto");
    }
        return modelAndView;
    }
    @GetMapping("/datos-contacto")
    public ModelAndView datosContacto(ModelAndView modelAndView, HttpSession sesionRegistro,
                                       @ModelAttribute("cliente") Cliente cliente,
                                      @ModelAttribute("direccion") Direccion direccion
                                     ) {
        modelAndView.setViewName("registro-datos-contacto");
        return modelAndView;
    }
    @PostMapping("/datos-contacto")
    public ModelAndView datosContactoPost(ModelAndView modelAndView, HttpSession sesionRegistro){
        modelAndView.setViewName("redirect:/datos-cliente");
        return modelAndView;
    }
    @GetMapping("/datos-cliente")
    public ModelAndView datosCliente(ModelAndView modelAndView, HttpSession sesionRegistro,
                                        @ModelAttribute("cliente") Cliente cliente,
                                     @ModelAttribute("direccion") Direccion direccion,
                                     @ModelAttribute("tarjeta") TarjetaCredito tarjeta) {
       // Cliente clienteRegistro = (Cliente) sesionRegistro.getAttribute("cliente");
        modelAndView.setViewName("registro-datos-cliente");
        return modelAndView;
    }
    @PostMapping("/datos-cliente")
    public ModelAndView datosClientePost(ModelAndView modelAndView, HttpSession sesionRegistro){
        modelAndView.setViewName("redirect:/confirmar-registro");
        return modelAndView;
    }

    @GetMapping("/confirmar-registro")
    public ModelAndView confirmarRegistro(ModelAndView modelAndView, HttpSession sesionRegistro,
                                        @ModelAttribute("cliente") Cliente cliente) {
       // Cliente clienteRegistro = (Cliente) sesionRegistro.getAttribute("cliente");
        modelAndView.setViewName("registro-datos-resumen");
        return modelAndView;
    }
}
