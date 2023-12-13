package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "cliente")
@NamedQuery(name="ClienteModel.findAll", query="SELECT c FROM ClienteModel c")
public class ClienteModel implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clienteid;

    @Column(name="dni", nullable = false)
    private Integer dni;

    @Column(name="nombre", nullable = false)
    private String nombre;

    private String apellido;

    //Constructor vacío
    public ClienteModel() {
    }

    //Getters y Setters
    public Integer getClienteid() {
        return clienteid;
    }

    public void setClienteid(Integer clienteid) {
        this.clienteid = clienteid;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    //StringBuilder
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Cliente [");

        appendField(builder, "clienteid", clienteid);
        appendField(builder, "dni", dni);

        builder.append("]");

        return builder.toString();
    }

    private void appendField(StringBuilder builder, String fieldName, Object fieldValue) {
        if (fieldValue != null) {
            builder.append(fieldName).append("=").append(fieldValue);
        }
        builder.append(", ");
    }


}
