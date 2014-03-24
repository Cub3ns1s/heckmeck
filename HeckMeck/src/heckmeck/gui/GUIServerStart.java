package heckmeck.gui;

import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIServerStart extends JPanel {

	public GUIServerStart() {
		JLabel lblAmount = new JLabel("Anzahl Spieler: ");
		add(lblAmount);
		
		JTextField txtField = new JTextField();
		add(txtField);
		
		setVisible(true);
	}

}
