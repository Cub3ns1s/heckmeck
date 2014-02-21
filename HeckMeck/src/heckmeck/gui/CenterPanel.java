package heckmeck.gui;

import java.awt.Color;
import java.util.Iterator;

import heckmeck.client.HeckmeckUI;
import heckmeck.server.GameState;
import heckmeck.server.Token;

import javax.swing.*;

public class CenterPanel extends JFrame implements HeckmeckUI{

	private static final long serialVersionUID = 8567397620665687871L;
	private JPanel mCenterPanel;
	private GameState mGameState;

	
	public CenterPanel(GameState gameState) {
		mGameState = gameState;
		mCenterPanel = new JPanel();
		configure();
	}

	private void configure() {
		mCenterPanel.setBackground(new Color(122, 6, 39));

		insertGrillTokenImages();

//		mFrame.add(mCenterPanel);
//		mFrame.revalidate();
	}
	
	private void insertGrillTokenImages() {
		for (Iterator<Token> iterator = mGameState.getGrill().getTokens()
				.iterator(); iterator.hasNext();) {
			Token token = iterator.next();

			String path = "L:/Ausbildung/Heckmeck/" + token.getValue() + ".png";

			mCenterPanel.add(new JLabel(new ImageIcon(path)));
			mCenterPanel.revalidate();
		}
	}

	@Override
	public void update(GameState gameState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showMessage(String message) {
		// TODO Auto-generated method stub
		
	}
}
