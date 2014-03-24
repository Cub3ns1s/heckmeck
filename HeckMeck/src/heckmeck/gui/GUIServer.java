package heckmeck.gui;

import javax.swing.JFrame;

public class GUIServer extends JFrame {

	private static final long serialVersionUID = 4051788870863701634L;

	public static void main(String args[]) {
		new GUIServer();
	}
	
	public GUIServer() {
		setTitle("Heckmeck - Server");
//		setSize(new Dimension(800, 600));
//		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(new GUIServerStart());
		
		setVisible(true);
		
		
	}
	
	
	
}
