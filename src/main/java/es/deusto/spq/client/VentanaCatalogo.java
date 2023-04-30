package es.deusto.spq.client;

import java.awt.EventQueue;
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
		setBounds(100, 100, 475, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblTitulo = new JLabel("CATALOGO DEL BAZAR");
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
		contentPane.add(panel_3, BorderLayout.EAST);
		
		JLabel lblFiltro = new JLabel("Filtrar");
		panel_3.add(lblFiltro);
		
		txtNombre = new JTextField();
		panel_3.add(txtNombre);
		txtNombre.setColumns(10);
		
		JButton btnFiltrar = new JButton("Filtrar");
		panel_3.add(btnFiltrar);
		
		btnFiltrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Producto> lista = new ArrayList<>();
				try {
				while(modeloTablaProductos.getRowCount()> 0) {
					modeloTablaProductos.removeRow(0);
				}
				} catch (ArrayIndexOutOfBoundsException e2) {
				}
				for(Producto p: Cliente.getProductos()) {
					if(p.getNombre().equals(txtNombre.getText())) {
						String [] pr = {p.getNombre(), String.valueOf(p.getTipo()), String.valueOf(p.getStock()), String.valueOf(p.getPrecio())};
						modeloTablaProductos.addRow(pr);
					}
				}
				if(modeloTablaProductos.getRowCount() == 0) {
					for (Producto producto: Cliente.getProductos()) {
						String [] pr = {producto.getNombre(), String.valueOf(producto.getTipo()), String.valueOf(producto.getStock()), String.valueOf(producto.getPrecio())};
						modeloTablaProductos.addRow(pr);
					}
				}
			}
		});
		
		
		
		btnAdmin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaAnyadir ventana = new VentanaAnyadir();
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
		
		tablaProductos.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				int fil = e.getFirstRow();
				
				String nombre = String.valueOf(modeloTablaProductos.getValueAt(fil, 0));
				String tipo =  String.valueOf(modeloTablaProductos.getValueAt(fil, 1));
				int stock =  Integer.parseInt(String.valueOf(modeloTablaProductos.getValueAt(fil, 2)));
				int precio = Integer.parseInt(String.valueOf(modeloTablaProductos.getValueAt(fil, 3)));			
				Cliente.editarProducto(nombre, tipo, stock, precio);
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
		
		tablaProductos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
					int fila = tablaProductos.rowAtPoint(e.getPoint());
					Producto p = Cliente.getProductos().get(fila);
					JOptionPane.showMessageDialog(null, "Producto: "+p.getNombre()+" | Tipo: "+p.getTipo()+" | Precio: "+p.getPrecio()+" | Stock: "+p.getStock());
				}
			};
		});
	}
	
	

}
