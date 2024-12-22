/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaventas.modelo;

/**
 *
 * @author javiermar2000
 */
import java.util.Date;
import java.util.List;

public class Venta {
    private String id;
    private Cliente cliente;
    private List<Producto> productosVendidos;
    private List<Integer> cantidades;
    private double montoTotal;
    private Date fechaVenta;

    public Venta(String id, Cliente cliente, List<Producto> productosVendidos, List<Integer> cantidades, double montoTotal, Date fechaVenta) {
        this.id = id;
        this.cliente = cliente;
        this.productosVendidos = productosVendidos;
        this.cantidades = cantidades;
        this.montoTotal = montoTotal;
        this.fechaVenta = fechaVenta;
    }

    // Getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Producto> getProductosVendidos() {
        return productosVendidos;
    }

    public void setProductosVendidos(List<Producto> productosVendidos) {
        this.productosVendidos = productosVendidos;
    }

    public List<Integer> getCantidades() {
        return cantidades;
    }

    public void setCantidades(List<Integer> cantidades) {
        this.cantidades = cantidades;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    // Método para obtener un producto por índice
    public Producto getProducto(int index) {
        if (index >= 0 && index < productosVendidos.size()) {
            return productosVendidos.get(index);
        }
        return null; // Devuelve null si el índice no es válido
    }

    // Método para obtener la cantidad de un producto por índice
    public int getCantidad(int index) {
        if (index >= 0 && index < cantidades.size()) {
            return cantidades.get(index);
        }
        return -1; // Devuelve -1 si el índice no es válido
    }
}


