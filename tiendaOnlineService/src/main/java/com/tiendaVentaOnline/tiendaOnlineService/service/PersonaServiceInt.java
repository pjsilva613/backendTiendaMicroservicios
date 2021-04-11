package com.tiendaVentaOnline.tiendaOnlineService.service;

import com.tiendaVentaOnline.tiendaOnlineService.entity.Persona;

import java.util.List;

public interface PersonaServiceInt {

    public List<Persona> findAllPerson();
    public Persona createPerson(Persona persona);
    public Persona updatePerson(Persona persona);
    public Persona deletePerson(Persona persona);
    public Persona getPerson(Long id);

}
