package com.tiendaVentaOnline.tiendaOnlineService.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tlb_facturas")
public class Factura {

    LocalDateTime localDateTime = LocalDateTime.of(2021, 04, 10, 12, 00);;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_factura")
    private String numeroFactura;

    private String descripcion;

    @Column(name = "persona_id")
    private Long personaId;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Valid
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "factura_id")
    private List<FacturaItem> items;

    private String estado;
    private Double domicilio;

    @Column(name = "total_factura")
    private Double totalFactura;
    @Transient
    private Persona persona;

    public Factura(){
        items = new ArrayList<>();
    }

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = localDateTime;
    }
}
