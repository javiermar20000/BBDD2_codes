/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaventas;

/**
 *
 * @author javiermar2000
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mycompany.sistemaventas.vista.GestorClienteUI;
import com.mycompany.sistemaventas.vista.GestorProductosUI;
import com.mycompany.sistemaventas.vista.GestorVentasUI;
import com.mycompany.sistemaventas.vista.HistorialVentasUI;

public class Principal extends JFrame {

    private JButton gestorClientesButton;
    private JButton gestorProductosButton;
    private JButton gestorVentasButton;
    private JButton historialVentasButton;

    public Principal() {
        setTitle("Sistema de Ventas");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setLayout(new GridBagLayout()); // Utilizamos GridBagLayout para controlar el espaciado entre botones
        
        // Configurar GridBagConstraints para espaciado entre botones
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Espaciado vertical entre botones
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; // Columna única para todos los botones
        gbc.gridy = GridBagConstraints.RELATIVE; // Filas sucesivas

        // Crear botones
        gestorClientesButton = new JButton("Gestor de Clientes");
        gestorProductosButton = new JButton("Gestor de Productos");
        gestorVentasButton = new JButton("Gestor de Ventas");
        historialVentasButton = new JButton("Historial de Ventas");

        // Agregar botones al JFrame
        add(gestorClientesButton, gbc);
        add(gestorProductosButton, gbc);
        add(gestorVentasButton, gbc);
        add(historialVentasButton, gbc);

        // Añadir eventos a los botones
        gestorClientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirGestorClientes();
            }
        });

        gestorProductosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirGestorProductos();
            }
        });

        gestorVentasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirGestorVentas();
            }
        });

        historialVentasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirHistorialVentas();
            }
        });
    }

    // Métodos para abrir cada ventana específica
    private void abrirGestorClientes() {
        this.dispose();
        new GestorClienteUI(this).setVisible(true);
    }

    private void abrirGestorProductos() {
        this.dispose();
        new GestorProductosUI(this).setVisible(true);
    }

    private void abrirGestorVentas() {
        this.dispose();
        new GestorVentasUI(this).setVisible(true);
    }

    private void abrirHistorialVentas() {
        this.dispose();
        new HistorialVentasUI(this).setVisible(true);
    }

    public static void main(String[] args) {
        // Crear y mostrar el menú principal
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }
}
