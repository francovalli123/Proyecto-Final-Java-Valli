package com.example.demo.dto;

import com.example.demo.model.ClienteModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class FacturaDTO {

    //Atributos
    private Integer facturaid;
    private Integer cantidad;
    private Date fecha;
    private BigDecimal total;
    private ClienteModel cliente;
    private Set<LineaDTO> lineas;

    //Getters y Setters

    public Integer getFacturaid() {
        return facturaid;
    }

    public void setFacturaid(Integer facturaid) {
        this.facturaid = facturaid;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

    public Set<LineaDTO> getLineas() {
        return lineas;
    }

    public void setLineas(Set<LineaDTO> lineas) {
        this.lineas = lineas;
    }
}