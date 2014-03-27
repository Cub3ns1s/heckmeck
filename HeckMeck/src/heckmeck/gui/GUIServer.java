package heckmeck.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GUIServer extends JFrame implements Runnable {

	private static final long serialVersionUID = 4051788870863701634L;

	public GUIServer(String ip) {
		setTitle("Heckmeck - Server");
		setSize(new Dimension(400, 300));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(new GUIServerStart(ip));
		
		setVisible(true);
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
