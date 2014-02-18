package heckmeck.client;

import java.awt.*;

import javax.swing.*;

public class GUIHeckmeck extends JFrame {

	private static final long serialVersionUID = 4220957201237157813L;
	private JFrame mFenster;

	public GUIHeckmeck() {
		// erzeuge Fenster
		mFenster = new JFrame("Heckmeck");
		mFenster.setSize(1000, 800);
		mFenster.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mFenster.getContentPane().setBackground(new Color(122, 6, 39));
		mFenster.setLayout(new FlowLayout());

		mFenster.setVisible(true);
		
		mFenster.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/21.png")));
		mFenster.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/21.png")));
		mFenster.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/21.png")));
		mFenster.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/21.png")));

	}

	public static void main(String[] args) {
		GUIHeckmeck gui = new GUIHeckmeck();
	}

}
