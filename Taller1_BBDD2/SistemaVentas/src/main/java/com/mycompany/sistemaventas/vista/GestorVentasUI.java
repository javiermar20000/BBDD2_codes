/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaventas.vista;

/**
 *
 * @author javiermar2000
 */
import javax.swing.*;
import com.mycompany.sistemaventas.Principal;
import com.mycompany.sistemaventas.controlador.VentaController;
import com.mycompany.sistemaventas.controlador.ClienteController;
import com.mycompany.sistemaventas.controlador.ProductoController;
import com.mycompany.sistemaventas.modelo.Cliente;
import com.mycompany.sistemaventas.modelo.Producto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GestorVentasUI extends JFrame {

    private VentaController ventaController;
    private ClienteController clienteController;
    private ProductoController productoController;
    private Principal menuPrincipal; // Referencia al menú principal

    private JComboBox<Cliente> comboClientes;
    private JComboBox<Producto> comboProductos;
    private JSpinner spinnerCantidad;
    private JLabel labelCantidad;

    public GestorVentasUI(Principal menuPrincipal) {
        ventaController = new VentaController();
        clienteController = new ClienteController();
        productoController = new ProductoController();
        this.menuPrincipal = menuPrincipal; // Guardamos la referencia
        initUI();
    }

    private void initUI() {
        setTitle("Gestor de Ventas");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        // Centramos la ventana en la pantalla
        setLocationRelativeTo(null); // Esto centra la ventana
        
        // Crear un panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, getWidth(), getHeight()); // Asegúrate de que el panel cubra toda la ventana
        add(panel);

        // Etiqueta para clientes
        JLabel labelClientes = new JLabel("Clientes:");
        labelClientes.setBounds(50, 20, 100, 30); // Posición ajustada para el texto de clientes
        panel.add(labelClientes); // Añadir al panel
    
        // Configuración de JComboBox para seleccionar cliente
        List<Cliente> clientes = clienteController.obtenerClientes();
        comboClientes = new JComboBox<>(clientes.toArray(new Cliente[0]));
        comboClientes.setBounds(50, 50, 200, 30);
        panel.add(comboClientes); // Añadir al panel
        
        // Etiqueta para productos
        JLabel labelProductos = new JLabel("Productos:");
        labelProductos.setBounds(50, 90, 100, 30); // Posición ajustada para el texto de productos
        panel.add(labelProductos); // Añadir al panel

        // Configuración de JComboBox para seleccionar producto
        List<Producto> productos = productoController.obtenerProductos();
        comboProductos = new JComboBox<>(productos.toArray(new Producto[0]));
        comboProductos.setBounds(50, 120, 200, 30);
        panel.add(comboProductos); // Añadir al panel

        // Configuración del JLabel para la cantidad
        labelCantidad = new JLabel("Cantidad a comprar:");
        labelCantidad.setBounds(50, 170, 150, 30);
        panel.add(labelCantidad);

        // Configuración de JSpinner para ingresar cantidad
        spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); // Rango de 1 a 100
        spinnerCantidad.setBounds(200, 170, 40, 30);
        panel.add(spinnerCantidad); // Añadir al panel

        // Configuración del botón "Registrar Venta"
        JButton btnRegistrarVenta = new JButton("Registrar Venta");
        btnRegistrarVenta.setBounds(50, 220, 200, 30);
        btnRegistrarVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarVenta();
            }
        });

        // Añadir el botón a la ventana
        panel.add(btnRegistrarVenta);
        
        // Botón para regresar al menú principal
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(50, 270, 200, 30);
        panel.add(btnRegresar);

        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresarAlMenuPrincipal();
            }
        });
    }
    
    // Método para regresar al menú principal
    private void regresarAlMenuPrincipal() {
        this.dispose(); // Cierra la ventana actual
        if (menuPrincipal != null) {
            menuPrincipal.setVisible(true); // Muestra el menú principal
        }
    }

    private void registrarVenta() {
        // Obtención del cliente seleccionado
        Cliente cliente = (Cliente) comboClientes.getSelectedItem();
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado un cliente.");
            return;
        }

        // Obtención del producto seleccionado
        Producto producto = (Producto) comboProductos.getSelectedItem();
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado un producto.");
            return;
        }

        // Obtener la cantidad de productos a comprar desde el JSpinner
        int cantidad = (int) spinnerCantidad.getValue();

        // Verificar que haya suficiente stock
        if (cantidad <= 0 || cantidad > producto.getStock()) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida o insuficiente en stock.");
            return;
        }

        // Registrar la venta con el cliente y el producto seleccionado
        try {
            // Aquí deberías llamar a la lógica de registro de venta
            ventaController.registrarVenta(cliente, List.of(producto), List.of(cantidad));

            // Actualizar el stock del producto
            productoController.actualizarStock(producto.getId(), cantidad);

            JOptionPane.showMessageDialog(this, "Venta registrada y stock actualizado exitosamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
