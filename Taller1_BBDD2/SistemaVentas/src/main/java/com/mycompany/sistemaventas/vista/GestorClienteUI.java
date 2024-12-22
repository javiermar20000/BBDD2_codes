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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mycompany.sistemaventas.Principal;
import com.mycompany.sistemaventas.controlador.ClienteController;
import com.mycompany.sistemaventas.modelo.Cliente;

public class GestorClienteUI extends JFrame {

    private ClienteController clienteController;
    private Principal menuPrincipal; // Referencia al menú principal

    public GestorClienteUI(Principal menuPrincipal) {
        clienteController = new ClienteController();
        this.menuPrincipal = menuPrincipal; // Guardamos la referencia
        initUI();
    }

    private void initUI() {
        setTitle("Gestor de Clientes");
        setSize(400, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Centramos la ventana en la pantalla
        setLocationRelativeTo(null); // Esto centra la ventana

        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(null);

        JLabel lblNombre = new JLabel("Nombre Completo:");
        lblNombre.setBounds(10, 20, 150, 25);
        panel.add(lblNombre);
        JTextField txtNombre = new JTextField(20);
        txtNombre.setBounds(180, 20, 165, 25);
        panel.add(txtNombre);

        JLabel lblCorreo = new JLabel("Correo Electrónico:");
        lblCorreo.setBounds(10, 60, 150, 25);
        panel.add(lblCorreo);
        JTextField txtCorreo = new JTextField(20);
        txtCorreo.setBounds(180, 60, 165, 25);
        panel.add(txtCorreo);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(10, 100, 150, 25);
        panel.add(lblTelefono);
        JTextField txtTelefono = new JTextField(20);
        txtTelefono.setBounds(180, 100, 165, 25);
        panel.add(txtTelefono);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(10, 140, 150, 25);
        panel.add(lblDireccion);
        JTextField txtDireccion = new JTextField(20);
        txtDireccion.setBounds(180, 140, 165, 25);
        panel.add(txtDireccion);

        JButton btnAgregar = new JButton("Agregar Cliente");
        btnAgregar.setBounds(10, 180, 150, 35);
        panel.add(btnAgregar);

        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Cliente cliente = new Cliente(txtNombre.getText(), txtCorreo.getText(), txtTelefono.getText(), txtDireccion.getText());
                clienteController.agregarCliente(cliente);
                JOptionPane.showMessageDialog(null, "Cliente agregado exitosamente.");
            }
        });

        JButton btnActualizar = new JButton("Actualizar Cliente");
        btnActualizar.setBounds(180, 180, 160, 35);
        panel.add(btnActualizar);

        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog("Ingrese el ID del cliente a actualizar:");
                Cliente clienteActualizado = new Cliente(txtNombre.getText(), txtCorreo.getText(), txtTelefono.getText(), txtDireccion.getText());
                clienteController.actualizarCliente(id, clienteActualizado);
                JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente.");
            }
        });

        JButton btnEliminar = new JButton("Eliminar Cliente");
        btnEliminar.setBounds(10, 220, 150, 35);
        panel.add(btnEliminar);

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog("Ingrese el ID del cliente a eliminar:");
                clienteController.eliminarCliente(id);
                JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente.");
            }
        });
        // Botón para regresar al menú principal
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(180, 220, 160, 35);
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
