/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaventas.controlador;

/**
 *
 * @author javiermar2000
 */
import com.mongodb.client.MongoCollection;
import com.mycompany.sistemaventas.modelo.Venta;
import com.mycompany.sistemaventas.modelo.Producto;
import com.mycompany.sistemaventas.modelo.Cliente;
import com.mycompany.sistemaventas.ConexionMongoDB;
import com.mycompany.sistemaventas.controlador.ClienteController;
import com.mycompany.sistemaventas.controlador.ProductoController;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VentaController {

    private MongoCollection<Document> coleccionVentas;
    private ClienteController clienteController;
    private ProductoController productoController;

    public VentaController() {
        coleccionVentas = ConexionMongoDB.getCollection("ventas");
        clienteController = new ClienteController();
        productoController = new ProductoController();
    }

    public void registrarVenta(Cliente cliente, List<Producto> productos, List<Integer> cantidades) {
        double montoTotal = 0.0;

        for (int i = 0; i < productos.size(); i++) {
        Producto producto = productos.get(i);
        int cantidadVendida = cantidades.get(i);

        // Verificar si hay stock suficiente
        if (producto.getStock() < cantidadVendida) {
            throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        // Calcular el total para cada producto (precio * cantidad)
        montoTotal += producto.getPrecio() * cantidadVendida;
    }

        // Crear la venta
        Venta venta = new Venta("0", cliente, productos, cantidades, montoTotal, new Date());

        // Insertar en MongoDB
        Document ventaDoc = new Document("cliente", cliente.getId().toString())
                .append("productos", obtenerDetalleProductos(productos, cantidades))
                .append("montoTotal", montoTotal)
                .append("fechaVenta", venta.getFechaVenta());
        coleccionVentas.insertOne(ventaDoc);

        System.out.println("Venta registrada con éxito.");
    }

    private List<Document> obtenerDetalleProductos(List<Producto> productos, List<Integer> cantidades) {
        List<Document> detalleProductos = new ArrayList<>();
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            int cantidad = cantidades.get(i);
            Document detalle = new Document("nombre", producto.getNombre())
                    .append("cantidad", cantidad)
                    .append("precioUnitario", producto.getPrecio());
            detalleProductos.add(detalle);
        }
        return detalleProductos;
    }

    public List<Venta> obtenerHistorialVentas() {
    List<Venta> historial = new ArrayList<>();
    for (Document doc : coleccionVentas.find()) {
        String id = doc.getObjectId("_id").toString();
        String idCliente = doc.getString("cliente");
        double montoTotal = doc.getDouble("montoTotal");
        Date fechaVenta = doc.getDate("fechaVenta");

        // Obtener el cliente relacionado
        Cliente cliente = clienteController.buscarCliente(idCliente);
        
        if (cliente != null) {
            // Obtener los productos vendidos y sus cantidades
            List<Producto> productosVendidos = new ArrayList<>();
            List<Integer> cantidades = new ArrayList<>();
            
            List<Document> productosDoc = (List<Document>) doc.get("productos");
            for (Document productoDoc : productosDoc) {
                String nombreProducto = productoDoc.getString("nombre");
                int cantidad = productoDoc.getInteger("cantidad");

                // Busca el producto por su nombre (o utiliza un identificador si lo tienes)
                Producto producto = productoController.buscarProductoPorNombre(nombreProducto);
                
                if (producto != null) {
                    productosVendidos.add(producto);
                    cantidades.add(cantidad);
                }
            }

            // Crear el objeto Venta con la información completa
            Venta venta = new Venta(id, cliente, productosVendidos, cantidades, montoTotal, fechaVenta);
            historial.add(venta);
        }
    }
    return historial;
    }
}
