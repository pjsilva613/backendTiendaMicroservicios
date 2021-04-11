package com.tiendaVentaOnline.tiendaOnlineService.repository;

import com.tiendaVentaOnline.tiendaOnlineService.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    public List<Factura> findByPersonaId(Long personaId);
    public Factura findByNumeroFactura(String numeroFactura);
}
