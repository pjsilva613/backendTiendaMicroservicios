package com.tiendaVentaOnline.tiendaOnlineService.service;

import com.tiendaVentaOnline.tiendaOnlineService.entity.Factura;
import com.tiendaVentaOnline.tiendaOnlineService.entity.FacturaItem;
import com.tiendaVentaOnline.tiendaOnlineService.entity.Persona;
import com.tiendaVentaOnline.tiendaOnlineService.entity.Producto;
import com.tiendaVentaOnline.tiendaOnlineService.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaServiceImp implements FacturaServiceInt {

    //se debe crear una funcion administrativa de configuracion para el porcentaje del iva,
    public static final Double PORCENTAJEIVA = 0.19;
    //se debe crear una funcion administrativa de configuracion para el tope del cobro del domicilio,
    public static final Double VALORTOPEDOMICILIO = 100000.00;
    //se debe crear una funcion administrativa de configuracion para el tope del cobro del domicilio,
    public static final Double VALORDOMICILIO = 20000.00;
    //se debe crear una funcion administrativa de configuracion para las horas permitidas para la actualizacion,
    public static final int HORASPERMITIDASACTUALIZAR = 5;
    //se debe crear una funcion administrativa de configuracion para las horas permitidas para la eliminacion,
    public static final int HORASPERMITIDASELIMINAR = 12;
    //se debe crear una funcion administrativa de configuracion para el mensaje cuando supera las horas permitidas,
    public static final String MSGSUPERAHORAS = "Ha superado el tiempo permitido";
    //se debe crear una funcion administrativa de configuracion para el mensaje cuando el total es menor que el nuevo valor,
    public static final String MSGVALORMENOR = "El valor de la factura debe ser superior al anterior";
    //se debe crear una funcion administrativa de configuracion para el mensaje cuando el total es menor que el nuevo valor,
    public static final String MSGPEDIDOCANCELADO = "Pedido Cancelado despues del tiempo limite";
    LocalDateTime localDateTime = LocalDateTime.of(2021, 04, 10, 12, 00);

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ProductoServiceInt productoServiceInt;

    @Autowired
    private PersonaServiceInt personaServiceInt;

    @Override
    public List<Factura> findAllFacturas() {
        return facturaRepository.findAll();
    }

    @Override
    public Factura createFactura(Factura factura) {
        Double total = 0.00;
        Factura facturaDB = facturaRepository.findByNumeroFactura(factura.getNumeroFactura());
        if (facturaDB != null) {
            return facturaDB;
        }
        factura.setEstado("CREADO");
        factura.setFechaCreacion(localDateTime);
        factura.getItems().forEach(facturaItem -> {
            facturaItem.setIva((facturaItem.getPrecio() * facturaItem.getCantidad()) * PORCENTAJEIVA);
            facturaItem.setSubTotal((facturaItem.getPrecio() * facturaItem.getCantidad()) + facturaItem.getIva());
        });

        for (FacturaItem facturaItem : factura.getItems()) {
            total += facturaItem.getSubTotal();
        }
        if (total < VALORTOPEDOMICILIO) {
            factura.setDomicilio(VALORDOMICILIO);
        } else {
            factura.setDomicilio(0.0);
        }
        factura.setTotalFactura(total + factura.getDomicilio());
        facturaDB = facturaRepository.save(factura);
        facturaDB.getItems().forEach(facturaItem -> {
            productoServiceInt.updateExistencia(facturaItem.getProductoId(), facturaItem.getCantidad() * -1);
        });

        return facturaDB;
    }

    @Override
    public Factura updateFactura(Factura factura) {
        Double total = 0.00;
        Double totalFacturaAnterior =0.0;
        int horacreacion = 0;
        LocalDateTime fechaActual = LocalDateTime.now();
        int horaActual = fechaActual.getHour();

        Factura facturaDB = getFactura(factura.getId());
        if (facturaDB == null) {
            return null;
        }
        horacreacion = facturaDB.getFechaCreacion().getHour();
        if (((horaActual-horacreacion) > HORASPERMITIDASACTUALIZAR)) {
            System.out.println(MSGSUPERAHORAS);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSGSUPERAHORAS);
        }
        totalFacturaAnterior = facturaDB.getTotalFactura();
        facturaDB.setPersonaId(factura.getPersonaId());
        facturaDB.setDescripcion(factura.getDescripcion());
        facturaDB.setNumeroFactura(factura.getNumeroFactura());
        facturaDB.getItems().clear();
        facturaDB.setItems(factura.getItems());
        facturaDB.getItems().forEach(facturaItem -> {
            facturaItem.setIva((facturaItem.getPrecio() * facturaItem.getCantidad()) * PORCENTAJEIVA);
            facturaItem.setSubTotal((facturaItem.getPrecio() * facturaItem.getCantidad()) + facturaItem.getIva());
        });

        for (FacturaItem facturaItem : facturaDB.getItems()) {
            total += facturaItem.getSubTotal();
        }
        if (total < VALORTOPEDOMICILIO) {
            facturaDB.setDomicilio(VALORDOMICILIO);
        } else {
            facturaDB.setDomicilio(0.0);
        }
        facturaDB.setTotalFactura(total + facturaDB.getDomicilio());

        if (totalFacturaAnterior > facturaDB.getTotalFactura()) {
            System.out.println(MSGVALORMENOR);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSGVALORMENOR);
        }

        return facturaRepository.save(facturaDB);
    }

    @Override
    public Factura deleteFactura(Factura factura) {
        int horacreacion = 0;
        Double nuevoValorfactura=0.0;
        LocalDateTime fechaActual = LocalDateTime.now();
        int horaActual = fechaActual.getHour();
        Factura facturaDb = getFactura(factura.getId());
        if (facturaDb == null) {
            return null;
        }
        horacreacion = facturaDb.getFechaCreacion().getHour();
        if (((horaActual-horacreacion) > HORASPERMITIDASELIMINAR)) {
            System.out.println(MSGPEDIDOCANCELADO);
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSGSUPERAHORAS);
            nuevoValorfactura=facturaDb.getTotalFactura()*0.10;
            facturaDb.setTotalFactura(nuevoValorfactura);
            facturaDb.setDescripcion(MSGPEDIDOCANCELADO);
            facturaDb.setEstado("CANCELADO");
        }

        facturaDb.setEstado("ELIMINADA");
        return facturaRepository.save(facturaDb);
    }

    @Override
    public Factura getFactura(Long id) {
        System.out.println("llega al getFactura:::::::::::::::::" + id);
        Factura factura = facturaRepository.findById(id).orElse(null);
        System.out.println("llega al service consulta factura= " + factura.toString());
        if (null != factura) {
            Persona persona = personaServiceInt.getPerson(factura.getPersonaId());
            System.out.println(persona.toString());
            factura.setPersona(persona);
            List<FacturaItem> listItem = factura.getItems().stream().map(facturaItem -> {
                Producto producto = productoServiceInt.getProduct(facturaItem.getProductoId());
                facturaItem.setProducto(producto);
                return facturaItem;
            }).collect(Collectors.toList());
            factura.setItems(listItem);
        }
        return factura;
    }
}
