package es.deusto.spq.client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
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

import es.deusto.spq.pojo.UserData;
import es.deusto.spq.server.jdo.Producto;
import es.deusto.spq.server.jdo.TipoProducto;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class VentanaEditarBorrar extends JFrame {

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
					VentanaEditarBorrar frame = new VentanaEditarBorrar();
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
	public VentanaEditarBorrar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon icono = new ImageIcon("src\\main\\java\\Imagenes\\B.png");
		Image imagenIcono = icono.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		ImageIcon iconoRedimensionado = new ImageIcon(imagenIcono);
		setIconImage(iconoRedimensionado.getImage());
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 801, 28);
		contentPane.add(panel);
		
		JLabel lblTitulo = new JLabel("MODO EDICION");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel.add(lblTitulo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 33, 528, 383);
		contentPane.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(5, 416, 801, 33);
		contentPane.add(panel_2);
		
		JButton btnVolver = new JButton("VOLVER");
		Utilidades.modifyButton(btnVolver);
		panel_2.add(btnVolver);
		
		modeloTablaProductos=new DefaultTableModel();
		String [] nombreColumnas = {"Nombre", "Tipo", "Stock", "Precio"};
		modeloTablaProductos.setColumnIdentifiers(nombreColumnas);
		for (Producto producto: Cliente.getProductos()) {
			String [] pr = {producto.getNombre(), String.valueOf(producto.getTipo()), String.valueOf(producto.getStock()), String.valueOf(producto.getPrecio())};
			modeloTablaProductos.addRow(pr);
		}
		tablaProductos = new JTable(modeloTablaProductos) {
			public boolean isCellEditable(int row,int column){
				if (Cliente.admin == true) {
					if (column == 0) {
						return false;
					}
					return true;
				}else return false;

			}
		};
		tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollTablaProductos = new JScrollPane(tablaProductos);
		panel_1.add(scrollTablaProductos);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(535, 33, 271, 383);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblFiltro = new JLabel("Filtrar por:");
		lblFiltro.setBounds(0, 9, 71, 18);
		lblFiltro.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panel_3.add(lblFiltro);
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setBounds(76, 5, 76, 26);
		comboBox.addItem("Nombre");
		comboBox.addItem("Tipo");
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		comboBox.setBackground(Color.WHITE);
		panel_3.add(comboBox);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(162, 9, 99, 20);
		Utilidades.modifyTextField(txtNombre, false);
		panel_3.add(txtNombre);
		txtNombre.setColumns(10);
		
		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBounds(99, 42, 89, 23);
		Utilidades.modifyButton(btnFiltrar);
		panel_3.add(btnFiltrar);
		
		JButton btnAyuda = new JButton("AYUDA");
		btnAyuda.setBounds(99, 349, 89, 23);
		Utilidades.modifyButton(btnAyuda);
		panel_3.add(btnAyuda);
		
		btnAyuda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "PARA ELIMINAR, SELECCIONE LA FILA DEL PRODUCTO Y ACONTINUACION PULSE SUPRIMIR.\n PARA EDITAR HAGA DOBLE CLICK EN EL ATRIBUTO DE LA FILA SELECCIONADA Y CAMBIE EL ATRIBUTO POR EL DESEADO.");
				
			}
		});
		
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
		
		btnVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaMenuAdmin v1 = new VentanaMenuAdmin();
				v1.setVisible(true);
			}
		});
		
		tablaProductos.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if (Cliente.admin) {
				int fil = e.getFirstRow();
					String nombre = String.valueOf(modeloTablaProductos.getValueAt(fil, 0));
					String tipo =  String.valueOf(modeloTablaProductos.getValueAt(fil, 1));
					int stock =  Integer.parseInt(String.valueOf(modeloTablaProductos.getValueAt(fil, 2)));
					int precio = Integer.parseInt(String.valueOf(modeloTablaProductos.getValueAt(fil, 3)));			
					Cliente.editarProducto(nombre, tipo, stock, precio);
				}
			}
		});
		tablaProductos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_DELETE && Cliente.admin==true) {
					int fil = tablaProductos.getSelectedRow();
					String nombre = String.valueOf(modeloTablaProductos.getValueAt(fil, 0));
					Cliente.borrarProducto(nombre);
					try {modeloTablaProductos.removeRow(fil);
					} catch (ArrayIndexOutOfBoundsException e2) {
					}
					tablaProductos.repaint();
				}			
			}
		});
	}
	
	

}
