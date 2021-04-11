package com.tiendaVentaOnline.tiendaOnlineService.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Data
@Table(name = "tbl_factura_items")
public class FacturaItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Positive(message = "El stock debe ser mayor que cero")
    private Double cantidad;
    private Double precio;
    private Double iva;
    @Column(name = "producto_id")
    private Long productoId;
    @Column(name = "sub_Total")
    private Double subTotal;

    @Transient
    private Producto producto;

   // public Double getSubTotal(){
     //   if (this.precio >0  && this.cantidad >0 ){
       //     return (this.cantidad * this.precio)+this.iva;
        //}else {
          //  return (double) 0;
        //}
    //}
    public FacturaItem(){
        this.cantidad=(double) 0;
        this.precio=(double) 0;

    }
}
