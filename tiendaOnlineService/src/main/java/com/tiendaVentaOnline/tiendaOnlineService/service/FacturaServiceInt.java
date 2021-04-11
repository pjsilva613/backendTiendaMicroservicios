package com.tiendaVentaOnline.tiendaOnlineService.service;

import com.tiendaVentaOnline.tiendaOnlineService.entity.Factura;

import java.util.List;

public interface FacturaServiceInt {

    public List<Factura> findAllFacturas();
    public Factura createFactura(Factura factura);
    public Factura updateFactura(Factura factura);
    public Factura deleteFactura(Factura factura);
    public Factura getFactura(Long id);
}
