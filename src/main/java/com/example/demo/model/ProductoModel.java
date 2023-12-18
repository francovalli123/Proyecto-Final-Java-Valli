package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Data
@Table(name="producto")
@NamedQuery(name="ProductoModel.findAll", query="SELECT p FROM ProductoModel p")
public class ProductoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productoid;

    private Integer codigo;

    private String descripcion;

    private Integer cantidad;

    private BigDecimal precio;

    //Constructor vacío
    public ProductoModel() {
    }

    //Getters & Setters
    public Integer getProductoId() {
        return productoid;
    }

    public void setProductoId(Integer productoId) {
        this.productoid = productoId;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    //StringBuilder
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Producto [");

        appendField(builder, "productoId", productoid);
        appendField(builder, "cantidad", cantidad);
        appendField(builder, "codigo", codigo);

        builder.append("]");

        return builder.toString();
    }

    //El metodo auxiliar en este caso agrega el nombre y el valor del campo solo si el valor no es nulo.
    private void appendField(StringBuilder builder, String fieldName, Object fieldValue) {
        if (fieldValue != null) {
            builder.append(fieldName).append("=").append(fieldValue).append(", ");
        }
    }




}