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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.mycompany.sistemaventas.Principal;
import com.mycompany.sistemaventas.controlador.VentaController;
import com.mycompany.sistemaventas.modelo.Venta;
import com.mycompany.sistemaventas.modelo.Producto;

public class HistorialVentasUI extends JFrame {

    private VentaController ventaController;
    private Principal menuPrincipal; // Referencia al menú principal

    public HistorialVentasUI(Principal menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
        ventaController = new VentaController();
        initUI();
    }

    private void initUI() {
        setTitle("Historial de Ventas");
        setSize(1100, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Centramos la ventana en la pantalla
        setLocationRelativeTo(null); // Esto centra la ventana

        // Definir columnas de la tabla
        String[] columnas = {"Fecha", "Cliente", "Producto", "Cantidad", "Monto Total ($)"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnas);

        // Crear tabla y añadir el modelo
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER); // Añadir al centro de la ventana

        // Obtener el historial de ventas desde el controlador
        List<Venta> ventas = ventaController.obtenerHistorialVentas();
        for (Venta venta : ventas) {
            // Iterar sobre los productos vendidos en la venta
            List<Producto> productosVendidos = venta.getProductosVendidos();
            List<Integer> cantidades = venta.getCantidades();
            
            for (int i = 0; i < productosVendidos.size(); i++) {
                Producto producto = productosVendidos.get(i); // Obtener el producto por índice
                int cantidad = cantidades.get(i);             // Obtener la cantidad por índice

                // Añadir una fila por cada producto vendido en la venta
                model.addRow(new Object[]{
                    venta.getFechaVenta(),
                    venta.getCliente().getNombreCompleto(),
                    producto.getNombre(),
                    cantidad,
                    venta.getMontoTotal()
                });
            }
        }
        
        // Crear botón "Regresar"
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(10, 350, 200, 30);
        btnRegresar.addActionListener(e -> regresar());
        
        // Añadir el botón al panel de la ventana
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(btnRegresar);
        add(panel, BorderLayout.SOUTH);

        // Mostrar la ventana
        setVisible(true);
    }
    
    // Método para regresar a la ventana anterior
    private void regresar() {
        // Cerrar la ventana actual
        this.dispose();
        // Mostrar la ventana principal
        if (menuPrincipal != null) {
            menuPrincipal.setVisible(true);
        }
    }
}
