package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;


import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name="factura")
@NamedQuery(name="FacturaModel.findAll", query="SELECT f FROM FacturaModel f")
public class FacturaModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer facturaid;

    private Integer cantidad;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "clienteid", referencedColumnName = "clienteid")
    private ClienteModel cliente;

    //Se utiliza BigDecimal ya que representa numeros flotantes de forma precisa, ademas de que tiene métodos para operar al mismo
    private BigDecimal total;


    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private Set<LineaModel> lineas = new HashSet<>();

    //Constructor vacío
    public FacturaModel() {

    }

    //Getters & Setters
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

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Set<LineaModel> getLineas() {
        return lineas;
    }

    public void setLineas(Set<LineaModel> lineas) {
        this.lineas = lineas;
    }

    //Patron de gestión de relaciones bidireccionales

    public void addLinea(LineaModel linea) {
        if (linea != null && !this.lineas.contains(linea)) {
            this.lineas.add(linea);
            linea.setFactura(this);
        }
    }

    public LineaModel removeLinea(LineaModel linea) {
        getLineas().remove(linea);
        linea.setFactura(null);
        return linea;
    }

    //StringBuilder
   @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Factura [");

        appendField(builder, "facturaid", facturaid);
        appendField(builder, "cantidad", cantidad);
        appendField(builder, "fecha", fecha);
        appendField(builder, "total", total);
        appendField(builder, "cliente", cliente);
        appendField(builder, "lineas", lineas);

        builder.append("]");

        return builder.toString();
    }

    //Metodo auxiliar para agregar el nombre y el valor del campo solo si el valor no es nulo.
    private void appendField(StringBuilder builder, String fieldName, Object fieldValue) {
        if (fieldValue != null) {
            builder.append(fieldName).append("=").append(fieldValue).append(", ");
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((facturaid == null) ? 0 : facturaid.hashCode());
        return result;
    }


}