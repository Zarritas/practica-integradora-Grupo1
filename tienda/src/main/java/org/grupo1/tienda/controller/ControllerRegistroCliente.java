package org.grupo1.tienda.controller;

import jakarta.servlet.http.HttpSession;
import org.grupo1.tienda.model.auxiliary.Direccion;
import org.grupo1.tienda.model.auxiliary.TarjetaCredito;
import org.grupo1.tienda.model.catalog.*;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.model.entity.Usuario;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.model.entity.grupovalidacion.*;
import org.grupo1.tienda.repository.*;
import org.grupo1.tienda.service.ServicioSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("alta-cliente")
public class ControllerRegistroCliente {
    private String prefijoDirectory = "register/";
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
    @Autowired
    ServicioSesion servicioSesion;
    @Autowired
    TarjetaCreditoRepository tarjetaCreditoRepository;
    @Autowired
    ClienteRepository clienteRepository;

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

    if(clienteRegistro != null){
        modelAndView.addObject("cliente", clienteRegistro);
    }

    modelAndView.addObject("readOnly", false);
    modelAndView.setViewName(prefijoDirectory + "registro-datos-personales");
    return modelAndView;
    }

    @PostMapping("/datos-personales")
    public ModelAndView datosPersonalesPost(ModelAndView modelAndView,
                                            HttpSession sesionRegistro,
                                            @Validated(DatosPersonales.class) @ModelAttribute("cliente") Cliente cliente,
                                            BindingResult resultadoVinculacion) {
        modelAndView.addObject("readOnly", false);
        if(resultadoVinculacion.hasErrors()){

            modelAndView.setViewName(prefijoDirectory +"registro-datos-personales");
        }else{

            sesionRegistro.setAttribute("cliente", cliente);


            modelAndView.setViewName("redirect:datos-contacto");
        }
        return modelAndView;
    }
    @GetMapping("/datos-contacto")
    public ModelAndView datosContacto(ModelAndView modelAndView, HttpSession sesionRegistro,
                                       @ModelAttribute("cliente") Cliente cliente,
                                      @ModelAttribute("direccion") Direccion direccion
                                     ) {
        Cliente clienteRegistro = (Cliente) sesionRegistro.getAttribute("cliente");

        if(clienteRegistro != null){
            modelAndView.addObject("cliente", clienteRegistro);
            if (clienteRegistro.getDireccion() != null){
                modelAndView.addObject("direccion", clienteRegistro.getDireccion());
            }
        }
        modelAndView.setViewName( prefijoDirectory +"registro-datos-contacto");
        modelAndView.addObject("readOnly", false);

        return modelAndView;
    }
    @PostMapping("/datos-contacto")
    public ModelAndView datosContactoPost(ModelAndView modelAndView, HttpSession sesionRegistro,
                                          @Validated(DatosContacto.class) @ModelAttribute("cliente") Cliente cliente,
                                          BindingResult resultadoVinculacionCliente,
                                          @Validated(DatosContacto.class) @ModelAttribute("direccion") Direccion direccion,
                                          BindingResult resultadoVinculacionDireccion) {

        modelAndView.addObject("readOnly", false);
        if (resultadoVinculacionCliente.hasErrors() || resultadoVinculacionDireccion.hasErrors()) {

            modelAndView.setViewName(prefijoDirectory +"registro-datos-contacto");
            return modelAndView;
        }

        Cliente clienteRegistro = (Cliente) sesionRegistro.getAttribute("cliente");
        if (clienteRegistro != null) {
            clienteRegistro.setTelefonoMovil(cliente.getTelefonoMovil());
            clienteRegistro.setDireccion(direccion);
            sesionRegistro.setAttribute("cliente", clienteRegistro);
        } else {
            cliente.setDireccion(direccion);
            sesionRegistro.setAttribute("cliente", cliente);
        }

        modelAndView.setViewName("redirect:datos-cliente");
        return modelAndView;
    }
    @GetMapping("/datos-cliente")
    public ModelAndView datosCliente(ModelAndView modelAndView, HttpSession sesionRegistro,
                                     @ModelAttribute("cliente") Cliente cliente,
                                     @ModelAttribute("direccionentrega") Direccion direccion,
                                     @ModelAttribute("tarjeta") TarjetaCredito tarjeta) {

        Cliente clienteRegistro = (Cliente) sesionRegistro.getAttribute("cliente");

        if (clienteRegistro != null) {
            modelAndView.addObject("cliente", clienteRegistro);
            if (!clienteRegistro.getDireccionesEntrega().isEmpty()) {
                modelAndView.addObject("direccionentrega", clienteRegistro.getDireccionesEntrega().iterator().next());

            }
            if (!clienteRegistro.getTarjetasCredito().isEmpty()){
            modelAndView.addObject("tarjeta", clienteRegistro.getTarjetasCredito().iterator().next());
            }
        }

        modelAndView.setViewName( prefijoDirectory +"registro-datos-cliente");
        modelAndView.addObject("readOnly", false);
        modelAndView.addObject("entrega", true);
        return modelAndView;
    }
    @PostMapping("/datos-cliente")
    public ModelAndView datosClientePost(ModelAndView modelAndView, HttpSession sesionRegistro,
                                         @ModelAttribute("cliente") Cliente cliente,
                                         BindingResult resultadoVinculacionCliente,
                                         @Validated(DatosCliente.class) @ModelAttribute("direccionentrega") Direccion direccion,
                                         BindingResult resultadoVinculacionDireccion,
                                         @Validated(DatosCliente.class) @ModelAttribute("tarjeta") TarjetaCredito tarjeta,
                                         BindingResult resultadoVinculacionTarjeta) {

        modelAndView.addObject("readOnly", false);
        if (resultadoVinculacionCliente.hasErrors() || resultadoVinculacionDireccion.hasErrors() || resultadoVinculacionTarjeta.hasErrors()) {
            modelAndView.setViewName(prefijoDirectory +"registro-datos-cliente");
            return modelAndView;
        } else {
            modelAndView.setViewName("redirect:confirmar-registro");
        }
        Cliente clienteRegistro = (Cliente) sesionRegistro.getAttribute("cliente");
        if (clienteRegistro != null) {
            clienteRegistro.getDireccionesEntrega().add(direccion);
            clienteRegistro.getTarjetasCredito().add(tarjeta);
            clienteRegistro.setComentarios(cliente.getComentarios());
            modelAndView.addObject("cliente", clienteRegistro);
        }else {
            cliente.getDireccionesEntrega().add(direccion);
            cliente.getTarjetasCredito().add(tarjeta);
            cliente.setComentarios(cliente.getComentarios());
            sesionRegistro.setAttribute("cliente", cliente);
        }


        return modelAndView;
    }

    @GetMapping("/confirmar-registro")
    public ModelAndView confirmarRegistro(ModelAndView modelAndView, HttpSession sesionRegistro,
                                        @ModelAttribute("cliente") Cliente cliente,
                                        @ModelAttribute("direccion") Direccion direccion,
                                        @ModelAttribute("direccionentrega") Direccion direccionentrega,
                                        @ModelAttribute("tarjeta") TarjetaCredito tarjeta) {
        Cliente clienteRegistro = (Cliente) sesionRegistro.getAttribute("cliente");
        if (clienteRegistro != null) {

                modelAndView.addObject("cliente", clienteRegistro);
                if(clienteRegistro.getDireccion() != null){
                    modelAndView.addObject("direccion", clienteRegistro.getDireccion());
                }
                if(!clienteRegistro.getDireccionesEntrega().isEmpty()){
                    Direccion direccion1 = clienteRegistro.getDireccionesEntrega().iterator().next();
                    modelAndView.addObject("direccionentrega", direccion1);
                }
                if(!clienteRegistro.getTarjetasCredito().isEmpty()){
                    TarjetaCredito tarjeta1 = clienteRegistro.getTarjetasCredito().iterator().next();
                    modelAndView.addObject("tarjeta", tarjeta1);
                }
        }
        modelAndView.setViewName(prefijoDirectory +"registro-datos-resumen");
        modelAndView.addObject("readOnly", true);
        return modelAndView;
    }
    @PostMapping("/confirmar-registro")


    public ModelAndView confirmarRegistroPost(ModelAndView modelAndView, HttpSession sesionRegistro,
//                                              @ModelAttribute("direccion") Direccion direccion,                                              BindingResult resultadoVinculaciondireccion,
//                                              @ModelAttribute("direccionentrega") Direccion direccionentrega,                                              BindingResult resultadoVinculaciondireccionentrega,
//                                              @ModelAttribute("tarjeta") TarjetaCredito tarjeta,
                                              @Validated(DatosResumen.class)@ModelAttribute("cliente") Cliente cliente,
                                              BindingResult resultadoVinculacion) {
        modelAndView.addObject("readOnly", true);
        Cliente clienteRegistro = (Cliente) sesionRegistro.getAttribute("cliente");
        System.err.println(clienteRegistro);
        if(resultadoVinculacion.hasErrors()
                || clienteRegistro == null) {
            modelAndView.setViewName(prefijoDirectory +"registro-datos-resumen");
            return modelAndView;
        }else{
            UsuarioEmpleadoCliente usuario = servicioSesion.getUsuarioLoggeado();
            System.err.println(clienteRegistro);
            clienteRegistro.setUsuario(usuario);
            direccionRepository.save(clienteRegistro.getDireccion());
            direccionRepository.saveAll(clienteRegistro.getDireccionesEntrega());
            tarjetaCreditoRepository.saveAll(clienteRegistro.getTarjetasCredito());
            clienteRepository.save(clienteRegistro);
            modelAndView.setViewName("app/area_personal");
        }
        return modelAndView;
                                             }
    @GetMapping("masacre")
    public ModelAndView borrarDatos(ModelAndView modelAndView, HttpSession sesionRegistro) {
        sesionRegistro.removeAttribute("cliente");
        sesionRegistro.removeAttribute("direccion");
        sesionRegistro.removeAttribute("direccionentrega");
        sesionRegistro.removeAttribute("tarjeta");
        modelAndView.setViewName("redirect:datos-personales");
        return modelAndView;
    }
}
