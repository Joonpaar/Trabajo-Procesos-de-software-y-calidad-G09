package es.deusto.spq.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.deusto.spq.server.jdo.TipoProducto;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class VentanaAnyadir extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtPrecio;
	private JTextField txtStock;
	private JComboBox<TipoProducto> combo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAnyadir frame = new VentanaAnyadir();
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
	public VentanaAnyadir() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(29, 44, 55, 14);
		panel.add(lblNombre);
		
		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setBounds(29, 138, 46, 14);
		panel.add(lblPrecio);
		
		JLabel lblStock = new JLabel("Stock");
		lblStock.setBounds(217, 44, 46, 14);
		panel.add(lblStock);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(217, 138, 46, 14);
		panel.add(lblTipo);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(83, 41, 86, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtPrecio = new JTextField();
		txtPrecio.setBounds(83, 135, 86, 20);
		panel.add(txtPrecio);
		txtPrecio.setColumns(10);
		
		txtStock = new JTextField();
		txtStock.setBounds(265, 41, 86, 20);
		panel.add(txtStock);
		txtStock.setColumns(10);
		
		combo = new JComboBox<>(TipoProducto.values());
		combo.setBounds(265, 135, 86, 20);
		panel.add(combo);
		
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblTitulo = new JLabel("INDIQUE LOS DATOS DEL PRODUCTO");
		panel_1.add(lblTitulo);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		
		JButton btnConfirmar = new JButton("AÑADIR");
		panel_2.add(btnConfirmar);
	}
}
