/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaventas.modelo;

/**
 *
 * @author javiermar2000
 */

import org.bson.types.ObjectId;

public class Cliente {
    private ObjectId id; // MongoDB usa ObjectId como identificador
    private String nombreCompleto;
    private String correoElectronico;
    private String telefono;
    private String direccion;

    // Constructor
    public Cliente(String nombreCompleto, String correoElectronico, String telefono, String direccion) {
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Getters y Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Sobrescribir toString() para mostrar el nombre completo
    @Override
    public String toString() {
        return nombreCompleto; // Muestra el nombre completo del cliente en el JComboBox
    }
}