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
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.sistemaventas.modelo.Producto;

import com.mycompany.sistemaventas.ConexionMongoDB;

public class ProductoController {

    private MongoCollection<Document> productosCollection;

    public ProductoController() {
        this.productosCollection = ConexionMongoDB.getCollection("productos");
    }

    // Crear producto
    public void agregarProducto(Producto producto) {
        productosCollection.insertOne(producto.toDocument());
        System.out.println("Producto agregado exitosamente.");
    }

    // Leer productos (obtiene todos los productos)
    public List<Producto> obtenerProductos() {
        List<Producto> productos = new ArrayList<>();
        try (MongoCursor<Document> cursor = productosCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                productos.add(documentToProducto(doc));
            }
        }
        return productos;
    }

    // Leer un producto por ID
    public Producto obtenerProductoPorId(String id) {
        Document doc = productosCollection.find(Filters.eq("_id", new ObjectId(id))).first();
        return doc != null ? documentToProducto(doc) : null;
    }

    // Actualizar producto
    public void actualizarProducto(String id, Producto producto) {
        Document filtro = new Document("_id", new ObjectId(id));
        productosCollection.updateOne(filtro, new Document("$set", producto.toDocument()));
        System.out.println("Producto actualizado exitosamente.");
    }

    // Eliminar producto
    public void eliminarProducto(String id) {
        Document filtro = new Document("_id", new ObjectId(id));
        productosCollection.deleteOne(filtro);
        System.out.println("Producto eliminado exitosamente.");
    }

    // Buscar productos por nombre
    // Método para buscar un solo producto por nombre
       public Producto buscarProductoPorNombre(String nombre) {
        Document doc = productosCollection.find(Filters.eq("nombre", nombre)).first();
        return doc != null ? documentToProducto(doc) : null;
        }

    // Buscar productos por categoría
    public List<Producto> buscarProductosPorCategoria(String categoria) {
        List<Producto> productos = new ArrayList<>();
        try (MongoCursor<Document> cursor = productosCollection.find(Filters.eq("categoria", categoria)).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                productos.add(documentToProducto(doc));
            }
        }
        return productos;
    }

    // Buscar productos por marca
    public List<Producto> buscarProductosPorMarca(String marca) {
        List<Producto> productos = new ArrayList<>();
        try (MongoCursor<Document> cursor = productosCollection.find(Filters.eq("marca", marca)).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                productos.add(documentToProducto(doc));
            }
        }
        return productos;
    }

    // Método auxiliar para convertir Document de MongoDB a un objeto Producto
    private Producto documentToProducto(Document doc) {
        String id = doc.getObjectId("_id").toHexString();
        String nombre = doc.getString("nombre");
        String categoria = doc.getString("categoria");
        String marca = doc.getString("marca");
        int stock = doc.getInteger("stock");
        double precio = doc.getDouble("precio");
        String descripcion = doc.getString("descripcion");

        return new Producto(id, nombre, categoria, marca, stock, precio, descripcion);
    }

    // Método para actualizar el stock después de una venta
    public void actualizarStock(String id, int cantidadVendida) {
        Producto producto = obtenerProductoPorId(id);
        if (producto != null) {
            int nuevoStock = producto.getStock() - cantidadVendida;
            Document filtro = new Document("_id", new ObjectId(id));
            productosCollection.updateOne(filtro, new Document("$set", new Document("stock", nuevoStock)));
            System.out.println("Stock actualizado exitosamente.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }
}
