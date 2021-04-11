package com.tiendaVentaOnline.tiendaOnlineService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendaVentaOnline.tiendaOnlineService.entity.Producto;
import com.tiendaVentaOnline.tiendaOnlineService.service.ProductoServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/productos")
public class ProductoController {

    @Autowired
    private ProductoServiceInt productoServiceInt ;

    @GetMapping
    public ResponseEntity<List<Producto>> listProduct(){
        List<Producto> productos = new ArrayList<Producto>();
        productos = productoServiceInt.listAllProduct();
        return ResponseEntity.ok(productos);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Producto> getProduct(@PathVariable("id") Long id) {
        Producto product =  productoServiceInt.getProduct(id);
        if (null==product){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }


    @PostMapping
    public ResponseEntity<Producto> createProduct(@Valid @RequestBody Producto producto, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Producto productoCreado =  productoServiceInt.createProduct(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Producto> updateProduct(@PathVariable("id") Long id, @RequestBody Producto producto){
        producto.setId(id);
        Producto productDB =  productoServiceInt.updateProduct(producto);
        if (productDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Producto> deleteProduct(@PathVariable("id") Long id){
        Producto productDelete = productoServiceInt.deleteProduct(id);
        if (productDelete == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDelete);
    }

    @PutMapping (value = "/{id}/cantidad")
    public ResponseEntity<Producto> updateStockProduct(@PathVariable  Long id ,@RequestParam(name = "cantidad", required = true) Double cantidad){
        Producto producto = productoServiceInt.updateExistencia(id, cantidad);
        if (producto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
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
        } catch ( JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}


