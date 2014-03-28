package heckmeck.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import heckmeck.server.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIPlayer extends JPanel {

	private static final long serialVersionUID = -8444188031674557590L;
	private JLabel mNameLabel ;
	private JPanel mFixedDicesPanel;
	private JPanel mTopTokenPanel;
	
	public GUIPlayer(){
		initUI( );
		setLayout(new GridLayout(3,1));
		setSize(new Dimension(400, 200));
	}
	
	public void setPlayerState( PlayerState playerState){
		mNameLabel.setText(playerState.getName());
		
		markCurrentPlayer(playerState.isTurn());
		setTopToken(playerState.getDeck().getTopToken());
		setFixedDices(playerState.getDiceState().getFixedDices());
	}

	private void initUI( ){
		mNameLabel = new JLabel();
		add( mNameLabel );
		mFixedDicesPanel = new JPanel();
		mFixedDicesPanel.setBackground(GUIClient.BACKGROUNDCOLOR);
		mTopTokenPanel = new JPanel();
		mTopTokenPanel.setBackground(GUIClient.BACKGROUNDCOLOR);
		add( mFixedDicesPanel);
		add( mTopTokenPanel);
		revalidate();
		repaint();
	}
	
	private void setTopToken( Token topToken ){
		mTopTokenPanel.removeAll();
		
		if (topToken == null){
			return;
		}
		
		String path = topToken.getValue() + ".png";
		ImageIcon imageIcon = new ImageIcon(path);
		GUIGame.resizeImageIcon(imageIcon);
		mTopTokenPanel.add(new JLabel(imageIcon));
		revalidate();
		repaint();
	}
	
	private void setFixedDices( List<Dice> fixedDices ){
		mFixedDicesPanel.removeAll( );
		
		for (int i = 0; i < fixedDices.size(); i++) {
			Dice dice = fixedDices.get(i);
			String path = "W" + dice.getLabel() + ".png";
			ImageIcon imageIcon = new ImageIcon(path);
			GUIGame.resizeImageIcon(imageIcon);
			mFixedDicesPanel.add(new JLabel(imageIcon));
			revalidate();
			repaint();
		}
	}
	
	private void markCurrentPlayer(boolean isTurn) {
		if (isTurn) {
			setBackground(new Color(254, 1, 7));
			mFixedDicesPanel.setBackground(new Color(254, 1, 7));
			mTopTokenPanel.setBackground(new Color(254, 1, 7));
		}
		else {
			setBackground(GUIClient.BACKGROUNDCOLOR);
			mFixedDicesPanel.setBackground(GUIClient.BACKGROUNDCOLOR);
			mTopTokenPanel.setBackground(GUIClient.BACKGROUNDCOLOR);
		}
	}
	
}
