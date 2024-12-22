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
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mycompany.sistemaventas.modelo.Cliente;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ClienteController {
    private MongoCollection<Document> clienteCollection;

    public ClienteController() {
        // Conectar a MongoDB
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("tiendaComputacion");
        clienteCollection = database.getCollection("clientes");
    }

    // Método para añadir un cliente
    public void agregarCliente(Cliente cliente) {
        Document doc = new Document("nombreCompleto", cliente.getNombreCompleto())
                .append("correoElectronico", cliente.getCorreoElectronico())
                .append("telefono", cliente.getTelefono())
                .append("direccion", cliente.getDireccion());
        clienteCollection.insertOne(doc);
    }

    // Método para actualizar un cliente
    public void actualizarCliente(String id, Cliente clienteActualizado) {
        Document doc = new Document("nombreCompleto", clienteActualizado.getNombreCompleto())
                .append("correoElectronico", clienteActualizado.getCorreoElectronico())
                .append("telefono", clienteActualizado.getTelefono())
                .append("direccion", clienteActualizado.getDireccion());
        clienteCollection.replaceOne(eq("_id", new ObjectId(id)), doc);
    }

    // Método para eliminar un cliente
    public void eliminarCliente(String id) {
        clienteCollection.deleteOne(eq("_id", new ObjectId(id)));
    }

    // Método para buscar un cliente
    public Cliente buscarCliente(String id) {
        Document doc = clienteCollection.find(eq("_id", new ObjectId(id))).first();
        if (doc != null) {
            Cliente cliente = new Cliente(
                    doc.getString("nombreCompleto"),
                    doc.getString("correoElectronico"),
                    doc.getString("telefono"),
                    doc.getString("direccion")
            );
            cliente.setId(doc.getObjectId("_id"));
            return cliente;
        }
        return null;
    }
    
    // Método para obtener todos los clientes
    public List<Cliente> obtenerClientes() {
        List<Cliente> clientes = new ArrayList<>();
        MongoCursor<Document> cursor = clienteCollection.find().iterator();
        
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Cliente cliente = new Cliente(
                        doc.getString("nombreCompleto"),
                        doc.getString("correoElectronico"),
                        doc.getString("telefono"),
                        doc.getString("direccion")
                );
                cliente.setId(doc.getObjectId("_id"));
                clientes.add(cliente);
            }
        } finally {
            cursor.close();
        }
        return clientes;
    }
}
