package es.deusto.spq.client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import es.deusto.spq.server.jdo.Producto;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;

public class VentanaAdmin extends JFrame {

	private JPanel contentPane;
	private JTable tablaProductos;
	public static DefaultTableModel modeloTablaProductos;
	public static List<Producto> productos;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAdmin frame = new VentanaAdmin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaAdmin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblTitulo = new JLabel("GESTION DEL BAZAR");
		panel.add(lblTitulo);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		
		JButton btnAnyadir = new JButton("AÑADIR");
		panel_2.add(btnAnyadir);
		
		modeloTablaProductos=new DefaultTableModel();
		String [] nombreColumnas = {"Nombre", "Tipo", "Stock", "Precio"};
		modeloTablaProductos.setColumnIdentifiers(nombreColumnas);
		//TODO bucle con todos los productos para la tabla
		tablaProductos = new JTable(modeloTablaProductos);
		JScrollPane scrollTablaProductos = new JScrollPane(tablaProductos);
		panel_1.add(scrollTablaProductos);
		
		btnAnyadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaAnyadir ventana = new VentanaAnyadir();
				ventana.setVisible(true);
				dispose();
				
			}
		});
	}

}
