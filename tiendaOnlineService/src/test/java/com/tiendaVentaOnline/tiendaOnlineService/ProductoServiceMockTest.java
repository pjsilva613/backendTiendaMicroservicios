package com.tiendaVentaOnline.tiendaOnlineService;

import com.tiendaVentaOnline.tiendaOnlineService.entity.Producto;
import com.tiendaVentaOnline.tiendaOnlineService.repository.ProductoRepository;
import com.tiendaVentaOnline.tiendaOnlineService.service.ProductoServiceImp;
import com.tiendaVentaOnline.tiendaOnlineService.service.ProductoServiceInt;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProductoServiceMockTest {

    @Mock
    private ProductoRepository productoRepository;

    private ProductoServiceInt productoServiceInt;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        productoServiceInt =  new ProductoServiceImp( productoRepository);
        Producto producto =  Producto.builder()
                .id(1L)
                .nombre("Interiores Adidas")
                .descripcion("interiores en material de algodon marca Adida")
                .precio(Double.parseDouble("25000.00"))
                .existencia(Double.parseDouble("50"))
                .estado("CREADO")
                .build();

        Mockito.when(productoRepository.findById(1L))
                .thenReturn(Optional.of(producto));
        Mockito.when(productoRepository.save(producto)).thenReturn(producto);

    }

    @Test
    public void whenValidGetID_ThenReturnProduct(){
        Producto productoDb = productoServiceInt.getProduct(1L);
        Assertions.assertThat(productoDb.getNombre()).isEqualTo("Interiores Adidas");

    }

    @Test
    public void whenValidUpdateStock_ThenReturnNewStock(){
        Producto updateExistencias = productoServiceInt.updateExistencia(1L,Double.parseDouble("8"));
        Assertions.assertThat(updateExistencias.getExistencia()).isEqualTo(58);
    }

}
