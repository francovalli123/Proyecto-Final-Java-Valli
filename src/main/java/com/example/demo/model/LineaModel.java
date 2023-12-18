package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Table(name="linea")
@NamedQuery(name="LineaModel.findAll", query="SELECT l FROM LineaModel l")
public class LineaModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    //Atributos
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer lineaid;

    private Integer cantidad;

    @Column(name="descripcion", nullable = true)
    private String descripcion;

    private BigDecimal precio;

    @ManyToOne
    @JoinColumn(name="facturaid")
    private FacturaModel factura;

    @ManyToOne
    @JoinColumn(name="productoid")
    private ProductoModel producto;

    //Constuctor vacío
    public LineaModel() {
    }

    //Getters & Setters
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

    public FacturaModel getFactura() {
        return factura;
    }

    public void setFactura(FacturaModel factura) {
        this.factura = factura;
    }

    public ProductoModel getProducto() {
        return producto;
    }

    public void setProducto(ProductoModel producto) {
        this.producto = producto;
    }

    //StringBuilder
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Linea [");

        appendField(builder, "lineaid", lineaid);
        appendField(builder, "cantidad", cantidad);
        appendField(builder, "descripcion", descripcion);
        appendField(builder, "precio", precio);

        builder.append("]");

        return builder.toString();
    }

    //El metodo se encarga de agregar el nombre del campo y su valor al StringBuilder solo si el valor no es nulo.
    private void appendField(StringBuilder builder, String fieldName, Object fieldValue) {
        if (fieldValue != null) {
            builder.append(fieldName).append("=").append(fieldValue);
        }
        builder.append(", ");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lineaid == null) ? 0 : lineaid.hashCode());
        return result;
    }

}
