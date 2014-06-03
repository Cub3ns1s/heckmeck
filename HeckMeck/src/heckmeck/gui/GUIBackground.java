package heckmeck.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GUIBackground extends JPanel {

	private static final long serialVersionUID = 6837629626520462683L;
	private BufferedImage img;
	 
	  public GUIBackground() {
	    // load the background image
		  setLayout(null);
	    try {
	      img = ImageIO.read(new File("holz.jpg"));
	    } catch(IOException e) {
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