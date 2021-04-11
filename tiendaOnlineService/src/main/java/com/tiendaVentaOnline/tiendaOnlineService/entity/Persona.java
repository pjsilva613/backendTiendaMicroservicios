package com.tiendaVentaOnline.tiendaOnlineService.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Entity
@Table(name="tbl_personas")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El número de documento no puede ser vacío")
    @Size( min = 8 , max = 12, message = "El tamaño del número de documento es 8")
    @Column(name = "numero_documento" , unique = true ,length = 8, nullable = false)
    private String numeroDocumento;

    @NotEmpty(message = "El nombre no puede ser vacío")
    @Column(name="nombres", nullable=false)
    private String nombres;

    @NotEmpty(message = "El apellido no puede ser vacío")
    @Column(name="apellidos", nullable=false)
    private String apellidos;

    @NotEmpty(message = "la direccion no puede estar vacío")
    @Column(name="direccion" , nullable=false)
    private String direccion;

    private String estado;
    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;
}
