package com.tiendaVentaOnline.tiendaOnlineService.service;

import com.tiendaVentaOnline.tiendaOnlineService.entity.Producto;
import com.tiendaVentaOnline.tiendaOnlineService.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImp implements ProductoServiceInt{

    @Autowired
    private final ProductoRepository productoRepository;
    private final LocalDate localDate= LocalDate.now();

    @Override
    public List<Producto> listAllProduct() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getProduct(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto createProduct(Producto producto) {
        producto.setEstado("CREADO");
        producto.setFechaCreacion(localDate);
        return productoRepository.save(producto);
    }

    @Override
    public Producto updateProduct(Producto producto) {
        Producto productoDb = getProduct(producto.getId());
        if (null==productoDb) {
            return null;
        }
        productoDb.setNombre(producto.getNombre());
        productoDb.setDescripcion(producto.getDescripcion());
        productoDb.setPrecio(producto.getPrecio());
        return productoRepository.save(productoDb);
    }

    @Override
    public Producto deleteProduct(Long id) {
        Producto productoDb = getProduct(id);
        if (null==productoDb) {
            return null;
        }
        productoDb.setEstado("ELIMINADO");
        return productoRepository.save(productoDb);
    }

    @Override
    public Producto updateExistencia(Long id, Double cantidad) {
        Producto productoDb = getProduct(id);
        if (null==productoDb) {
            return null;
        }
        Double nuevaCantidad= productoDb.getExistencia()+cantidad;
        productoDb.setExistencia(nuevaCantidad);
        return productoRepository.save(productoDb);
    }
}
