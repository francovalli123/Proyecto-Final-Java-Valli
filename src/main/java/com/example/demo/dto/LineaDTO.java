package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LineaDTO {

    //Atributos
    private Integer lineaid;
    private Integer cantidad;
    private String descripcion;
    private BigDecimal precio;

    private ProductoDTO producto;

    private Integer facturaid;


    //Getters y Setters

    public Integer getFacturaid() {
        return facturaid;
    }

    public void setFacturaid(Integer facturaid) {
        this.facturaid = facturaid;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    public Integer getLineaid() {
        return lineaid;
    }

    public void setLineaid(Integer lineaid) {
        this.lineaid = lineaid;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

}
