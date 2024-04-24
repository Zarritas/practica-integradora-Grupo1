package org.grupo1.tienda.controller;

import jakarta.servlet.http.HttpSession;
import org.grupo1.tienda.model.catalog.Direccion;
import org.grupo1.tienda.model.catalog.Genero;
import org.grupo1.tienda.model.catalog.Pais;
import org.grupo1.tienda.model.catalog.TipoDocumentoCliente;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.model.entity.grupovalidacion.DatosPersonales;
import org.grupo1.tienda.repository.DireccionRepository;
import org.grupo1.tienda.repository.GeneroRepository;
import org.grupo1.tienda.repository.PaisRepository;
import org.grupo1.tienda.repository.TipoDocumentoRepository;
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
@GetMapping("/datos-personales")
    public ModelAndView datosPersonales(ModelAndView modelAndView, HttpSession sesionRegistro,
                                        @ModelAttribute("cliente") Cliente cliente) {
        List<Pais> paisLista = paisRepository.findAll();
        List<TipoDocumentoCliente> documentoLista = documentoRepository.findAll();
        List<Genero> generoLista = generoRepository.findAll();
        Cliente clienteRegistro = (Cliente) sesionRegistro.getAttribute("cliente");
        modelAndView.addObject("paises", paisLista);
        modelAndView.addObject("tipodocumentos", documentoLista);
        modelAndView.addObject("generos", generoLista);

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
                                      @ModelAttribute("cliente") Cliente cliente) {
        List<Direccion> direcciones = direccionRepository.findAll();
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
                                        @ModelAttribute("cliente") Cliente cliente) {
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
