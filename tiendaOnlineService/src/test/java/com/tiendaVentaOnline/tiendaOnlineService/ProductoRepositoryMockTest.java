package com.tiendaVentaOnline.tiendaOnlineService;

import com.tiendaVentaOnline.tiendaOnlineService.entity.Producto;
import com.tiendaVentaOnline.tiendaOnlineService.repository.ProductoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
public class ProductoRepositoryMockTest {

    LocalDate localDate = LocalDate.now();
    @Autowired
    private ProductoRepository productoRepository;

    @Test
    public void whenFindAllProducts_ThenReturnAListProduct(){
        Producto producto= Producto.builder()
                .nombre("medias Adidas")
                .descripcion("medias en material de algodon marca Adidas")
                .existencia(20.0)
                .estado("CREADO")
                .fechaCreacion(localDate)
                .precio(15000.00)
                .build();
        productoRepository.save(producto);

        List<Producto> productosList = productoRepository.findAll();

        Assertions.assertThat(productosList.size()).isEqualTo(4);
    }

}
