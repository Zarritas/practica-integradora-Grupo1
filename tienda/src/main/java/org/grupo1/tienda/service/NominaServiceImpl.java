package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.auxiliary.Nomina;
import org.grupo1.tienda.repository.NominaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NominaServiceImpl  implements NominaService {

    @Autowired
    private NominaRepository nominaRepository;

    public List<Nomina> devuelveNominas()  {
        return nominaRepository.findAll();
    }

    public Nomina devuelveNominaPorId(Long id) throws NoEncontradoException {
        return nominaRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("Nómina con id " + id + " no encontrada"));
    }

    public Nomina aniadeNomina(Nomina nomina) {
        return nominaRepository.save(nomina);
    }

    public Nomina actualizaNomina(Long id, Nomina nomina) throws NoEncontradoException {
        if (!nominaRepository.existsById(id)) {
            throw new NoEncontradoException("Nómina con id " + id + " no encontrada");
        }
        nomina.setId(id);
        return nominaRepository.save(nomina);
    }

    public void eliminaNomina(Long id) throws NoEncontradoException {
        if (!nominaRepository.existsById(id)) {
            throw new NoEncontradoException("Nómina con id " + id + " no encontrada");
        }
        nominaRepository.deleteById(id);
    }

}
