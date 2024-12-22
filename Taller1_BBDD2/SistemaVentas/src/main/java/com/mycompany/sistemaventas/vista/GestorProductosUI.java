/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistemaventas.vista;

/**
 *
 * @author javiermar2000
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mycompany.sistemaventas.Principal;
import com.mycompany.sistemaventas.controlador.ProductoController;
import com.mycompany.sistemaventas.modelo.Producto;

public class GestorProductosUI extends JFrame {

    private ProductoController productoController;
    private Principal menuPrincipal; // Referencia al menú principal

    // Constructor que recibe el menú principal
    public GestorProductosUI(Principal menuPrincipal) {
        productoController = new ProductoController();
        this.menuPrincipal = menuPrincipal; // Guardamos la referencia
        initUI();
    }

    private void initUI() {
        setTitle("Gestor de Productos");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Centramos la ventana en la pantalla
        setLocationRelativeTo(null); // Esto centra la ventana

        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(null);

        // Campos de entrada para cada atributo de Producto
        JLabel lblNombre = new JLabel("Nombre del Producto:");
        lblNombre.setBounds(10, 20, 150, 25);
        panel.add(lblNombre);
        JTextField txtNombre = new JTextField(20);
        txtNombre.setBounds(180, 20, 195, 25);
        panel.add(txtNombre);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(10, 60, 150, 25);
        panel.add(lblCategoria);
        JTextField txtCategoria = new JTextField(20);
        txtCategoria.setBounds(180, 60, 195, 25);
        panel.add(txtCategoria);

        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setBounds(10, 100, 150, 25);
        panel.add(lblMarca);
        JTextField txtMarca = new JTextField(20);
        txtMarca.setBounds(180, 100, 195, 25);
        panel.add(txtMarca);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(10, 140, 150, 25);
        panel.add(lblStock);
        JTextField txtStock = new JTextField(20);
        txtStock.setBounds(180, 140, 195, 25);
        panel.add(txtStock);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(10, 180, 150, 25);
        panel.add(lblPrecio);
        JTextField txtPrecio = new JTextField(20);
        txtPrecio.setBounds(180, 180, 195, 25);
        panel.add(txtPrecio);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(10, 220, 150, 25);
        panel.add(lblDescripcion);
        JTextField txtDescripcion = new JTextField(20);
        txtDescripcion.setBounds(180, 220, 195, 25);
        panel.add(txtDescripcion);

        // Botón para agregar producto
        JButton btnAgregar = new JButton("Agregar Producto");
        btnAgregar.setBounds(10, 260, 170, 35);
        panel.add(btnAgregar);

        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = txtNombre.getText();
                    String categoria = txtCategoria.getText();
                    String marca = txtMarca.getText();
                    int stock = Integer.parseInt(txtStock.getText());
                    double precio = Double.parseDouble(txtPrecio.getText());
                    String descripcion = txtDescripcion.getText();

                    Producto producto = new Producto("0", nombre, categoria, marca, stock, precio, descripcion);

                    productoController.agregarProducto(producto);
                    JOptionPane.showMessageDialog(null, "Producto agregado exitosamente.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error en formato de número. Revise stock y precio.");
                }
            }
        });

        // Botón para actualizar producto
        JButton btnActualizar = new JButton("Actualizar Producto");
        btnActualizar.setBounds(200, 260, 180, 35);
        panel.add(btnActualizar);

        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = JOptionPane.showInputDialog("Ingrese el ID del producto a actualizar:");
                    String nombre = txtNombre.getText();
                    String categoria = txtCategoria.getText();
                    String marca = txtMarca.getText();
                    int stock = Integer.parseInt(txtStock.getText());
                    double precio = Double.parseDouble(txtPrecio.getText());
                    String descripcion = txtDescripcion.getText();

                    Producto producto = new Producto(id, nombre, categoria, marca, stock, precio, descripcion);

                    productoController.actualizarProducto(id, producto);
                    JOptionPane.showMessageDialog(null, "Producto actualizado exitosamente.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error en formato de número. Revise stock y precio.");
                }
            }
        });

        // Botón para eliminar producto
        JButton btnEliminar = new JButton("Eliminar Producto");
        btnEliminar.setBounds(10, 300, 170, 35);
        panel.add(btnEliminar);

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog("Ingrese el ID del producto a eliminar:");
                productoController.eliminarProducto(id);
                JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente.");
            }
        });
        
        // Botón para regresar al menú principal
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(200, 300, 180, 35);
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
}
