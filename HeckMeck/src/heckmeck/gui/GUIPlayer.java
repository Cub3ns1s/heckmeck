package heckmeck.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import heckmeck.server.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIPlayer extends JPanel {

	private JLabel mNameLabel ;
	private JPanel mFixedDicesPanel;
	private JPanel mTopTokenPanel;
	
	public GUIPlayer(  ){
		initUI( );
		setLayout( new GridLayout( 1,3)	);
		setSize( new Dimension( 300,300)	);
	}
	
	public void setPlayerState( PlayerState playerState){
		mNameLabel.setText(playerState.getName());
		
		setTopToken(playerState.getDeck().getTopToken());
		setFixedDices(playerState.getDiceState().getFixedDices());
	}
	
	private void initUI( ){
		mNameLabel = new JLabel( "Leer");
		add( mNameLabel );
		mFixedDicesPanel = new JPanel();
		mFixedDicesPanel.setBackground(GUIHeckmeck.BACKGROUNDCOLOR);
		mTopTokenPanel = new JPanel();
		mTopTokenPanel.setBackground(GUIHeckmeck.BACKGROUNDCOLOR);
		add( mFixedDicesPanel);
		add( mTopTokenPanel);
		revalidate();
	}
	
	private void setTopToken( Token topToken ){
		if (topToken == null){
			return;
		}
		mTopTokenPanel.removeAll();
		String path = topToken.getValue() + ".png";
		ImageIcon imageIcon = new ImageIcon(path);
		GUIHeckmeck.resizeImageIcon(imageIcon);
		mTopTokenPanel.add(new JLabel(imageIcon));
		revalidate();
	}
	
	private void setFixedDices( List<Dice> fixedDices ){
		mFixedDicesPanel.removeAll( );
		mFixedDicesPanel.add(new JLabel( "Fixed Dices"));
		
		
		for (int i = 0; i < fixedDices.size(); i++) {
			Dice dice = fixedDices.get(i);
			String path = "W" + dice.getLabel() + ".png";
			ImageIcon imageIcon = new ImageIcon(path);
			GUIHeckmeck.resizeImageIcon(imageIcon);
			mFixedDicesPanel.add(new JLabel(imageIcon));
			revalidate();
		}
	}
	
	
	
}
