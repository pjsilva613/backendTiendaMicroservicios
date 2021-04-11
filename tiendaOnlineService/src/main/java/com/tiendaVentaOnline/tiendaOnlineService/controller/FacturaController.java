package com.tiendaVentaOnline.tiendaOnlineService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendaVentaOnline.tiendaOnlineService.entity.Factura;
import com.tiendaVentaOnline.tiendaOnlineService.service.FacturaServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaServiceInt facturaServiceInt;

    @GetMapping
    public ResponseEntity<List<Factura>> listAllFacturas() {
        List<Factura> facturas = facturaServiceInt.findAllFacturas();
        if (facturas.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(facturas);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Factura> getFactura(@PathVariable("id") long id) {
        Factura factura  = facturaServiceInt.getFactura(id);
        if (null == factura) {
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(factura);
    }

    @PostMapping
    public ResponseEntity<Factura> createFactura(@Valid @RequestBody Factura factura, BindingResult result) {
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Factura facturaDB = facturaServiceInt.createFactura (factura);
        return  ResponseEntity.status( HttpStatus.CREATED).body(facturaDB);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateInvoice(@PathVariable("id") long id, @RequestBody Factura factura) {

        factura.setId(id);
        System.out.println("factura que llega al controller:::::"+factura.toString());
        Factura facturaDb=facturaServiceInt.updateFactura(factura);
        System.out.println(facturaDb.toString());
        if (facturaDb == null) {
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(facturaDb);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Factura> deleteInvoice(@PathVariable("id") long id) {

        Factura factura = facturaServiceInt.getFactura(id);
        if (factura == null) {
            return  ResponseEntity.notFound().build();
        }
        factura = facturaServiceInt.deleteFactura(factura);
        return ResponseEntity.ok(factura);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
