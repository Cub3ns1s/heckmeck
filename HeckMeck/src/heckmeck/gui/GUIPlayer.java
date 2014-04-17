package heckmeck.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.net.URL;
import java.util.List;

import heckmeck.server.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIPlayer extends JPanel {

	private static final long serialVersionUID = -8444188031674557590L;
	private JLabel mNameLabel ;
	private JPanel mFixedDicesPanel;
	private JPanel mTopTokenPanel;
	private JLabel mAmountTokenLabel;
	private JLabel mAmountDots;
	private JPanel mLabelPanel;
	
	public GUIPlayer(){
		initUI( );
		setLayout(new GridLayout(3,1));
		setSize(new Dimension(400, 200));
	}
	
	public void setPlayerState( PlayerState playerState){
		setLabels(playerState);
		markCurrentPlayer(playerState.isTurn());
		setTopToken(playerState.getDeck().getTopToken());
		setFixedDices(playerState.getDiceState().getFixedDices());
	}

	private void setLabels(PlayerState playerState) {
		mNameLabel.setText(playerState.getName());
		mLabelPanel.add(mNameLabel);
		
		int amountToken = playerState.getDeck().getSize();
		mAmountTokenLabel.setText("Tokens: " + String.valueOf(amountToken));
		mLabelPanel.add(mAmountTokenLabel);
		
		int amountDots = getAmountDots(playerState);
		mAmountDots.setText("Amount Dots: " + String.valueOf(amountDots));
		mLabelPanel.add(mAmountDots);
	}

	private int getAmountDots(PlayerState playerState) {
		int amount = 0;
		
		for (int i = 0; i < playerState.getDiceState().getFixedDices().size(); i++) {
			Dice dice = playerState.getDiceState().getFixedDices().get(i);
			amount += dice.getValue();
		}
		
		return amount;
	}

	private void initUI( ){
		
		mNameLabel = new JLabel();
		mAmountTokenLabel = new JLabel();
		mAmountDots = new JLabel();
		
		mLabelPanel = new JPanel(new GridLayout(3,1));
//		mLabelPanel.setBackground(GUIClient.BACKGROUNDCOLOR);
		mFixedDicesPanel = new JPanel();
//		mFixedDicesPanel.setBackground(GUIClient.BACKGROUNDCOLOR);
		mTopTokenPanel = new JPanel();
//		mTopTokenPanel.setBackground(GUIClient.BACKGROUNDCOLOR);
		
		add(mLabelPanel);
		add(mFixedDicesPanel);
		add(mTopTokenPanel);
		revalidate();
		repaint();
	}
	
	private void setTopToken( Token topToken ){
		mTopTokenPanel.removeAll();
		
		if (topToken == null){
			return;
		}
		
		URL path = GUIPlayer.class.getResource(topToken.getValue() + ".png");
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
			URL path = GUIPlayer.class.getResource("W" + dice.getLabel() + ".png");
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
//			setBackground(GUIClient.BACKGROUNDCOLOR);
//			mFixedDicesPanel.setBackground(GUIClient.BACKGROUNDCOLOR);
//			mTopTokenPanel.setBackground(GUIClient.BACKGROUNDCOLOR);
		}
	}
	
}
