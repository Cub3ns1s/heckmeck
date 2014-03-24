package heckmeck.gui;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIServerStart extends JPanel {

	private static final long serialVersionUID = -1432504468764188547L;
	private Container container;

	public GUIServerStart() {		
		
		JLabel lblAmount = new JLabel("Anzahl Spieler: ");
		add(lblAmount);
		
		JTextField txtField = new JTextField(10);
		add(txtField);
		
		JLabel lblIP = new JLabel("IP: 127.0.0.1");
		setBounds(200, 200, 20, 10);
		add(lblIP);
		
		JButton btnStart = new JButton("Starte Server");
		add(btnStart);
				
		setVisible(true);
	}

}
