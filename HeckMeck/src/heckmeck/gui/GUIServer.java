package heckmeck.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GUIServer extends JFrame implements Runnable{

	private static final long serialVersionUID = 4051788870863701634L;

	private String mIP; 
	
	public GUIServer(String ip) {
		mIP = ip;		
		
	}

	@Override
	public void run() {

		setTitle("Heckmeck - Server");
		setIconImage(Toolkit.getDefaultToolkit().getImage("ww2.gif"));
		setSize(new Dimension(400, 300));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(new GUIServerStart(mIP));
		
		setVisible(true);

	}
	
	public static void main(String[] args){
		new Thread( new GUIServer(args[0] )).start();;
	}
	
	
}
