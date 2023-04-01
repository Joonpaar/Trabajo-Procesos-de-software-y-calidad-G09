package es.deusto.spq.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class Utilidades {
	public static Popup popup;
	
	public static void modifyButtonOpcion(JButton boton) {
		boton.setForeground(Color.WHITE);
		boton.setBackground(new Color(255, 127, 80));
		boton.setBorderPainted(false);
		boton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		boton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				boton.setBackground(new Color(255, 127, 80));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				boton.setBorderPainted(true);

			}

		});
	}
	
	public static void modifyButton(JButton boton) {
		boton.setAlignmentX(Component.CENTER_ALIGNMENT);
		boton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		boton.setForeground(new Color(67, 67, 67));
		boton.setFocusPainted(false);
		boton.setBackground(Color.white);	

		boton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				boton.setContentAreaFilled(true);
			}
		});
	}
	
	public static void JLabelWithPopup(JLabel label) {
		label.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent evt) {
				if (popup != null) {
					popup.hide();
				}
				JLabel text = new JLabel("Campo requerido");
				text.setFont(new Font("Segoe UI", Font.BOLD, 11));
				text.setForeground(Color.WHITE);
				text.setOpaque(true);
				text.setBackground(new Color(20, 115, 191));
				popup = PopupFactory.getSharedInstance().getPopup(evt.getComponent(), text,
						(int) label.getLocationOnScreen().getX()+20, (int) label.getLocationOnScreen().getY()-15);
				popup.show();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (popup != null) {
					popup.hide();
				}
			}
		});
	}
	
	public static JPasswordField modifyPasswordField(JPasswordField field) {
		Border line = BorderFactory.createLineBorder(new Color(194, 194, 194), 1);
		Border empty = new EmptyBorder(0, 5, 0, 0);
		CompoundBorder border = new CompoundBorder(line, empty);
		field.setBorder(border);
		field.setForeground(Color.GRAY);
		char passwordChar = field.getEchoChar();
		field.setEchoChar((char) 0);

		field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Border line = BorderFactory.createLineBorder(new Color(20, 115, 191), 1);
				Border empty = new EmptyBorder(0, 5, 0, 0);
				CompoundBorder border = new CompoundBorder(line, empty);
				field.setBorder(border);
				field.setForeground(Color.BLACK);
				field.setEchoChar(passwordChar);
				super.focusGained(e);
			}

			@Override
			public void focusLost(FocusEvent e) {
				Border line = BorderFactory.createLineBorder(new Color(194, 194, 194), 1);
				Border empty = new EmptyBorder(0, 5, 0, 0);
				CompoundBorder border = new CompoundBorder(line, empty);
				field.setBorder(border);
				field.setForeground(Color.GRAY);
				field.setEchoChar(passwordChar);
				super.focusLost(e);
			}
		});
		return field;
	}
	
	public static JTextField modifyTextField(JTextField field, boolean opcion) {
		Border line = BorderFactory.createLineBorder(new Color(194, 194, 194), 1);
		Border empty = new EmptyBorder(0, 5, 0, 0);
		CompoundBorder border = new CompoundBorder(line, empty);
		field.setBorder(border);
		field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		field.setForeground(Color.GRAY);

		field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Border line = BorderFactory.createLineBorder(new Color(20, 115, 191), 1);
				Border empty = new EmptyBorder(0, 5, 0, 0);
				CompoundBorder border = new CompoundBorder(line, empty);
				field.setBorder(border);
				field.setForeground(Color.BLACK);
				super.focusGained(e);
			}

			@Override
			public void focusLost(FocusEvent e) {
				Border line = BorderFactory.createLineBorder(new Color(194, 194, 194), 1);
				Border empty = new EmptyBorder(0, 5, 0, 0);
				CompoundBorder border = new CompoundBorder(line, empty);
				field.setBorder(border);
				if (opcion == true) {
					field.setForeground(Color.GRAY);
				}
				
				super.focusLost(e);
			}
		});

		field.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				field.setCursor(new Cursor(Cursor.TEXT_CURSOR));
			}

		});
		return field;
	}
}
