package es.deusto.spq.client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.xdevapi.Client;

import es.deusto.spq.pojo.UserData;
import es.deusto.spq.server.jdo.Carro;
import es.deusto.spq.server.jdo.Producto;
import es.deusto.spq.server.jdo.TipoProducto;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class VentanaCatalogo extends JFrame {

	private JPanel contentPane;
	private JTable tablaProductos;
	public static DefaultTableModel modeloTablaProductos;
	public static List<Producto> productos;
	private Cliente cliente;
	
	//VARIABLE PARA GUARDAR EL NOMBRE DEL USUARIO LOGEADO
	public static String cli = "";
	private JTextField txtNombre;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCatalogo frame = new VentanaCatalogo();
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
	public VentanaCatalogo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 827, 493);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblTitulo = new JLabel("CATALOGO DEL BAZAR");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel.add(lblTitulo);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		
		JButton btnAdmin = new JButton("ADMIN");
		Utilidades.modifyButton(btnAdmin);
		if(cliente.admin == true) {
			panel_2.add(btnAdmin);
		}
		
		JButton btnVolver = new JButton("CERRAR SESION");
		Utilidades.modifyButton(btnVolver);
		panel_2.add(btnVolver);
		
		JButton btnCarrito = new JButton("CARRITO");
		Utilidades.modifyButton(btnCarrito);
		panel_2.add(btnCarrito);
		
		modeloTablaProductos=new DefaultTableModel();
		String [] nombreColumnas = {"Nombre", "Tipo", "Stock", "Precio"};
		modeloTablaProductos.setColumnIdentifiers(nombreColumnas);
		for (Producto producto: Cliente.getProductos()) {
			String [] pr = {producto.getNombre(), String.valueOf(producto.getTipo()), String.valueOf(producto.getStock()), String.valueOf(producto.getPrecio())};
			modeloTablaProductos.addRow(pr);
		}
		tablaProductos = new JTable(modeloTablaProductos) {
			public boolean isCellEditable(int rows, int column) {
				return false;
			}
		};
		tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollTablaProductos = new JScrollPane(tablaProductos);
		panel_1.add(scrollTablaProductos);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.EAST);
		
		JLabel lblFiltro = new JLabel("Filtrar por:");
		lblFiltro.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel_3.add(lblFiltro);
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.addItem("Nombre");
		comboBox.addItem("Tipo");
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		comboBox.setBackground(Color.WHITE);
		panel_3.add(comboBox);
		
		txtNombre = new JTextField();
		Utilidades.modifyTextField(txtNombre, false);
		panel_3.add(txtNombre);
		txtNombre.setColumns(10);
		
		JButton btnFiltrar = new JButton("Filtrar");
		Utilidades.modifyButton(btnFiltrar);
		panel_3.add(btnFiltrar);
		
		btnFiltrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Producto> lista = Cliente.getProductos();
				try {
				while(modeloTablaProductos.getRowCount()> 0) {
					modeloTablaProductos.removeRow(0);
				}
				} catch (ArrayIndexOutOfBoundsException e2) {
				}
				for(Producto p: lista) {
					if (comboBox.getSelectedItem().toString().equals("Nombre")) {
						if(p.getNombre().equals(txtNombre.getText())) {
							String [] pr = {p.getNombre(), String.valueOf(p.getTipo()), String.valueOf(p.getStock()), String.valueOf(p.getPrecio())};
							modeloTablaProductos.addRow(pr);
						}
						
					}else if(comboBox.getSelectedItem().toString().equals("Tipo")){
						if (txtNombre.getText().equals("Hogar")) {
							if(p.getTipo().equals(TipoProducto.Hogar)) {
								String [] pr = {p.getNombre(), String.valueOf(p.getTipo()), String.valueOf(p.getStock()), String.valueOf(p.getPrecio())};
								modeloTablaProductos.addRow(pr);
							}
						}
						
						else if (txtNombre.getText().equals("Jardineria")) {
							if(p.getTipo().equals(TipoProducto.Jardineria)) {
								String [] pr = {p.getNombre(), String.valueOf(p.getTipo()), String.valueOf(p.getStock()), String.valueOf(p.getPrecio())};
								modeloTablaProductos.addRow(pr);
							}
						}
						else if(txtNombre.getText().equals("Limpieza")) {
							if(p.getTipo().equals(TipoProducto.Limpieza)) {
								String [] pr = {p.getNombre(), String.valueOf(p.getTipo()), String.valueOf(p.getStock()), String.valueOf(p.getPrecio())};
								modeloTablaProductos.addRow(pr);
							}
						}
					}
				}
				if(modeloTablaProductos.getRowCount() == 0) {
					for (Producto producto: lista) {
						String [] pr = {producto.getNombre(), String.valueOf(producto.getTipo()), String.valueOf(producto.getStock()), String.valueOf(producto.getPrecio())};
						modeloTablaProductos.addRow(pr);
					}
				}
			}
		});
		
		
		
		btnAdmin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaMenuAdmin ventana = new VentanaMenuAdmin();
				ventana.setVisible(true);
				dispose();
				
			}
		});
		
		btnVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaLogin v1 = new VentanaLogin();
				v1.setVisible(true);
			}
		});
		
		btnCarrito.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaCarrito v1 = new VentanaCarrito();
				v1.setVisible(true);
			}
		});
		
		tablaProductos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					int fila = tablaProductos.rowAtPoint(e.getPoint());
					Producto p = Cliente.getProductos().get(fila);
					
					int opcion = JOptionPane.showOptionDialog(null,"Producto: "+p.getNombre()+" | Tipo: "+p.getTipo()+" | Precio: "+p.getPrecio()+" | Stock: "+p.getStock(),
						    p.getNombre(),
						    JOptionPane.YES_NO_OPTION,
						    JOptionPane.PLAIN_MESSAGE,
						    null,
						    new Object[]{"Salir", "Añadir al carro"},
						    "Salir"  // Botón seleccionado por defecto
						);

						if (opcion == JOptionPane.YES_OPTION) {
						} else {
							Object[] opciones = {"Aceptar", "Cancelar"};
							JTextField textField = new JTextField();
							Object[] mensaje = {"Cuántas unidades quiere?:", textField};
							int opcion2 = JOptionPane.showOptionDialog(
							        null,                              // Parent component
							        mensaje,                           // Mensaje a mostrar
							        "Ingresar número",                 // Título de la ventana
							        JOptionPane.YES_NO_OPTION,         // Tipo de ventana
							        JOptionPane.PLAIN_MESSAGE,         // Tipo de mensaje
							        null,                              // Icono personalizado
							        opciones,                          // Texto de los botones
							        opciones[0]                        // Botón seleccionado por defecto
							);
							
							int numero = -1;
							if (opcion2 == JOptionPane.YES_OPTION) {
							    try {
							        numero = Integer.parseInt(textField.getText());
							    } catch (NumberFormatException e2) {
							        JOptionPane.showMessageDialog(null, "El valor ingresado no es un número válido.");
							    }
							}
							if (numero >= 0) {
								Carro c=Cliente.getCarro(cli);
								List<Integer> cantN=c.getCantidades();
								cantN.add(numero);
								List<String> prodN =c.getProductos();
								prodN.add(p.getNombre());
								Cliente.actualizarCarro(prodN, cantN);
						        JOptionPane.showMessageDialog(null, "Producto añadido al carro.");
							}
						}
						
				}
			};
		});
	}
	
	

}
