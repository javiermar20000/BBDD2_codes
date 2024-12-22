/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaventas.modelo;

/**
 *
 * @author javiermar2000
 */
import org.bson.Document;

public class Producto {
    private String id;
    private String nombre;
    private String categoria;
    private String marca;
    private int stock;
    private double precio;
    private String descripcion;

    // Constructor aceptando todos los parámetros
    public Producto(String id, String nombre, String categoria, String marca, int stock, double precio, String descripcion) {
        this.id = id; // Puede ser null si es autogenerado o no requerido
        this.nombre = nombre;
        this.categoria = categoria;
        this.marca = marca;
        this.stock = stock;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    // Getter para el stock
    public int getStock() {
        return stock;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getMarca() {
        return marca;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Método para convertir el producto en un documento de MongoDB
    public Document toDocument() {
        return new Document("nombre", nombre)
                .append("categoria", categoria)
                .append("marca", marca)
                .append("stock", stock)
                .append("precio", precio)
                .append("descripcion", descripcion);
    }

    // Sobrescribir toString() para mostrar el nombre del producto
    @Override
    public String toString() {
        return nombre; // Muestra el nombre del producto en el JComboBox
    }
}
