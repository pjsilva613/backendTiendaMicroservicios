package com.tiendaVentaOnline.tiendaOnlineService.service;

import com.tiendaVentaOnline.tiendaOnlineService.entity.Producto;
import com.tiendaVentaOnline.tiendaOnlineService.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductoServiceInt {

    public List<Producto> listAllProduct();
    public Producto getProduct(Long id);
    public Producto createProduct(Producto producto);
    public Producto updateProduct(Producto product);
    public  Producto deleteProduct(Long id);
    public Producto updateExistencia(Long id, Double quantity);



}
