package heckmeck.gui;

import heckmeck.client.Client;
import heckmeck.client.HeckmeckUI;
import heckmeck.server.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class GUIHeckmeck extends JFrame implements HeckmeckUI {

	public static final Color BACKGROUNDCOLOR = new Color(203, 1, 6);
	private final static int SCREENWIDTH = 340;
	private final static int SCREENHEIGHT = 240;
	private static Dimension mScreenSize;
	private static final long serialVersionUID = 4220957201237157813L;

	private JFrame mFrame;
	private JPanel mCenterPanel;
	private JPanel mCenterGrillPanel;
	private JPanel mCenterDicePanel;
	private JPanel mCenterButtonPanel;
	private JPanel mBottomPanel;
	private List<GUIPlayer> mPlayerList;
	private GUIPlayer mTopLeftPane;
	private GUIPlayer mTopRightPane;
	
	private GameState mGameState;
	private Client mClient;
	private String mName;
	private ActionListener mButtonListener;
	
	public static void main(String[] args) {
		new GUIHeckmeck(args[0]);
	}

	private GUIHeckmeck(String name) {
		mClient = new Client(name, this);
		mName = name;
		mScreenSize = new Dimension(800, 600);
		mPlayerList = new ArrayList<GUIPlayer>( );

		for (int i = 0; i < 4; i++){
			mPlayerList.add(new GUIPlayer());
		}
		
		initActionListener();
		
		createFrame();
		createTopPanel();
		createCenterPanel( );
		createBottomPanel();
		
		new Thread(mClient).start();
		
	}
	
	private void initActionListener() {
		mButtonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = "0S";
				mClient.createDecisionMessage(input);
			}
		};
		
	}

	private void createFrame() {
		mFrame = new JFrame("Heckmeck - " + mName);
		mFrame.setSize(mScreenSize);
		mFrame.setResizable(false);
		mFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mFrame.setBackground(BACKGROUNDCOLOR);
		mFrame.setLayout(new GridLayout(3,1));
		
		mFrame.setVisible(true);
	}
	
	private void createTopPanel() {
		JPanel topPanel = new JPanel(new GridLayout(1,2));
		topPanel.setBackground(BACKGROUNDCOLOR);

		mTopLeftPane = mPlayerList.get(0);
		mTopRightPane = mPlayerList.get(1);
		
		mTopLeftPane.setBackground(BACKGROUNDCOLOR);
		mTopRightPane.setBackground(BACKGROUNDCOLOR);
		
		topPanel.add(mTopLeftPane);
		topPanel.add(mTopRightPane);
		mFrame.add(topPanel);
		
		mFrame.revalidate();
		mFrame.repaint();
	}

	private void createCenterPanel() {

		mCenterGrillPanel = new JPanel();
		mCenterDicePanel = new JPanel();
		mCenterButtonPanel = new JPanel();
		
		mCenterGrillPanel.setBackground(BACKGROUNDCOLOR);
		mCenterDicePanel.setBackground(BACKGROUNDCOLOR);
		mCenterButtonPanel.setBackground(BACKGROUNDCOLOR);
		
		mCenterPanel = new JPanel(new GridLayout(3,1));
		mCenterPanel.setBackground(BACKGROUNDCOLOR);
		
		mCenterPanel.add(mCenterGrillPanel);
		mCenterPanel.add(mCenterDicePanel);
		mCenterPanel.add(mCenterButtonPanel);
		mFrame.add(mCenterPanel);
		
		mFrame.revalidate();
		mFrame.repaint();
	}

	private void createBottomPanel() {
		mBottomPanel = new JPanel();
		mBottomPanel.setBackground(BACKGROUNDCOLOR);
		
		mBottomPanel.add(new JLabel("BOTTOM"));
		mFrame.add(mBottomPanel);
		
		mFrame.revalidate();
		mFrame.repaint();
	}

	public static void resizeImageIcon(ImageIcon imageIcon) {
		Image image = imageIcon.getImage();
		Image scaledImage = image.getScaledInstance(
				(int) (imageIcon.getIconWidth() * getScreenFactor()),
				(int) (imageIcon.getIconHeight() * getScreenFactor()), 4);
		imageIcon.setImage(scaledImage);
	}

	private static double getScreenFactor() {
		double xFactor = SCREENWIDTH / mScreenSize.getWidth();
		double yFactor = SCREENHEIGHT / mScreenSize.getHeight();

		if (xFactor > yFactor) {
			return yFactor;
		} else {
			return xFactor;
		}
	}

	@Override
	public void update(GameState gameState) {
		mGameState = gameState;
		updateCenterPanel();
		updatePlayer(gameState.getPlayerStates());
	}

	private void updateCenterPanel() {
		insertGrillTokenImages();
		insertDices();
		insertButton();
	}
	
	private void updatePlayer(List<PlayerState> playerStates) {
		for ( int i = 0; i < playerStates.size(); i++) {
			mPlayerList.get(i).setPlayerState(playerStates.get(i));
		}
	}

	private void insertGrillTokenImages() {
		mCenterGrillPanel.removeAll();

		String path = "";
		List<Token> tokenList = mGameState.getGrill().getTokens();

		for (Token token : tokenList) {
			if (token.isActive()) {
				path = token.getValue() + ".png";

			} else {
				path = "inactiveToken.png";
			}

			ImageIcon imageIcon = new ImageIcon(path);
			resizeImageIcon(imageIcon);

			mCenterGrillPanel.add(new JLabel(imageIcon));

		}			
		mFrame.revalidate();
		mFrame.repaint();
	}

	private void insertDices() {
		mCenterDicePanel.removeAll();

		java.util.List<Dice> unfixedDices = mGameState.getCurrentPlayer()
				.getDiceState().getUnfixedDices();

		for (Dice dice : unfixedDices) {
			String path = "W" + dice.getLabel() + ".png";

			ImageIcon imageIcon = new ImageIcon(path);
			resizeImageIcon(imageIcon);
			JLabel label = new JLabel(imageIcon);
			label.addMouseListener(new MouseHandler());
			mCenterDicePanel.add(label);
			
		}
		
		mFrame.revalidate();
		mFrame.repaint();
	}
	
	private void insertButton() {
		mCenterButtonPanel.removeAll();
		
		JButton buttonStop = new JButton("End Turn");
		buttonStop.addActionListener(mButtonListener);
		mCenterButtonPanel.add(buttonStop);
		
		mFrame.revalidate();
		mFrame.repaint();
	}

	@Override
	public void showMessage(String message) {
	}

	private class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			JLabel label = (JLabel) event.getSource();
			ImageIcon imageIcon = (ImageIcon) label.getIcon();
			String path = imageIcon.getDescription();

			String input = path.substring(1, 2) + 'C';
			System.out.println(input);
			mClient.createDecisionMessage(input);
		}
	}

}
