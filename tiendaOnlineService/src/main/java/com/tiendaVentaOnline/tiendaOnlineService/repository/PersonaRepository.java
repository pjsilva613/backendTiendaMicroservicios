package com.tiendaVentaOnline.tiendaOnlineService.repository;

import com.tiendaVentaOnline.tiendaOnlineService.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    public Persona findByNumeroDocumento(String numeroDocumento);
    public List<Persona> findByApellidos(String apellidos);

}
