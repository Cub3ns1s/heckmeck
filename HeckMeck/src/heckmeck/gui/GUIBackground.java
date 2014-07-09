package heckmeck.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GUIBackground extends JPanel {

	// Attributes
	private static final long serialVersionUID = 6837629626520462683L;
	private BufferedImage img;

	// Constructor
	public GUIBackground() {
		setLayout(null);
		try {
			img = ImageIO.read(getClass().getResource(
					"/heckmeck/pictures/holz.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// paint the background image and scale it to fill the entire space
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}
}