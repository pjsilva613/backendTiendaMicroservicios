package com.tiendaVentaOnline.tiendaOnlineService.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "tbl_productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no debe ser vacío")
    private String nombre;
    private String descripcion;
    @Positive(message = "El stock debe ser mayor que cero")
    private Double existencia;
    private Double precio;
    private String estado;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;
}
