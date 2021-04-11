package com.tiendaVentaOnline.tiendaOnlineService.service;

import com.tiendaVentaOnline.tiendaOnlineService.entity.Persona;
import com.tiendaVentaOnline.tiendaOnlineService.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PersonaServiceImp implements PersonaServiceInt{

    private LocalDate localDate= LocalDate.now();

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public List<Persona> findAllPerson() {
        return personaRepository.findAll();
    }

    @Override
    public Persona createPerson(Persona persona) {
        Persona personaDb = personaRepository.findByNumeroDocumento(persona.getNumeroDocumento());
        if (personaDb != null) {
            return personaDb;
        }
        persona.setEstado("CREADO");
        persona.setFechaCreacion(localDate);
        return personaRepository.save(persona);
    }

    @Override
    public Persona updatePerson(Persona persona) {
        Persona personaDb = personaRepository.findByNumeroDocumento(persona.getNumeroDocumento());
        if (personaDb == null) {
            return null;
        }
        personaDb.setNombres(persona.getNombres());
        personaDb.setApellidos(persona.getApellidos());
        personaDb.setDireccion(persona.getDireccion());
        return personaRepository.save(personaDb);
    }

    @Override
    public Persona deletePerson(Persona persona) {
        Persona personaDb = personaRepository.findByNumeroDocumento(persona.getNumeroDocumento());
        if (personaDb == null) {
            return null;
        }
        personaDb.setEstado("ELIMINADO");
        return personaRepository.save(personaDb);
    }

    @Override
    public Persona getPerson(Long id) {
        return personaRepository.findById(id).orElse(null);
    }
}
