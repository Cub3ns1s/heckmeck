package heckmeck.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GUIClient extends JFrame {

	// Attributes
	private static final long serialVersionUID = 4283851808155206905L;

	// Main Method
	public static void main(String args[]) {
		new GUIClient();
	}

	// Constructor
	public GUIClient() {
		setTitle("Heckmeck");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/heckmeck/pictures/ww2.gif")));
		setSize(new Dimension(1000, 800));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		add(new GUILogon());
		revalidate();
	}

}