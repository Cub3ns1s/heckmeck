package heckmeck.gui;

import heckmeck.client.Client;
import heckmeck.client.HeckmeckUI;
import heckmeck.server.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class GUIGame extends GUIBackground implements HeckmeckUI {

	private final static int SCREENWIDTH = 440;
	private final static int SCREENHEIGHT = 340;
	private static Dimension mScreenSize;
	private static final long serialVersionUID = 4220957201237157813L;

	private JPanel mCenterPanel;
	private JPanel mCenterGrillPanel;
	private JPanel mCenterDicePanel;
	private JPanel mCenterButtonPanel;
	private List<GUIPlayer> mPlayerList;
	private GUIPlayer mTopLeftPane;
	private GUIPlayer mTopRightPane;
	private GUIPlayer mBottomLeftPane;
	private GUIPlayer mBottomRightPane;
	private JLabel mMessageLbl;

	private GameState mGameState;
	private Client mClient;
	private boolean mEndTurn = false;
	private String mDiceValue;
	private JPanel mStatistics;
	
	public GUIGame(String name, String ip) {
		mClient = new Client(name, this, ip);
		mScreenSize = new Dimension(1000, 800);
		mPlayerList = new ArrayList<GUIPlayer>();

		for (int i = 0; i < 4; i++) {
			mPlayerList.add(new GUIPlayer());
		}

		initPanel();
		createTopPanel();
		createCenterPanel();
		createMessagePanel();
		createBottomPanel();

		new Thread(mClient).start();
	}

	private void initPanel() {
		setSize(mScreenSize);
		setLayout(new GridLayout(4, 1));
	}

	private void createTopPanel() {
		JPanel topPanel = new JPanel(new GridLayout(1, 2));
		topPanel.setOpaque(false);

		mTopLeftPane = mPlayerList.get(0);
		mTopLeftPane.setOpaque(false);
		mTopRightPane = mPlayerList.get(1);
		mTopRightPane.setOpaque(false);

		topPanel.add(mTopLeftPane);
		topPanel.add(mTopRightPane);
		add(topPanel);

		revalidate();
		repaint();
	}

	private void createCenterPanel() {

		mCenterGrillPanel = new JPanel();
		mCenterGrillPanel.setOpaque(false);
		mCenterDicePanel = new JPanel();
		mCenterDicePanel.setOpaque(false);
		mCenterButtonPanel = new JPanel();
		mCenterButtonPanel.setOpaque(false);

		mCenterPanel = new JPanel(new GridLayout(3, 1));
		mCenterPanel.setOpaque(false);

		mCenterPanel.add(mCenterGrillPanel);
		mCenterPanel.add(mCenterDicePanel);
		mCenterPanel.add(mCenterButtonPanel);
		add(mCenterPanel);

		revalidate();
		repaint();
	}

	private void createMessagePanel() {
		JPanel messagePanel = new JPanel();
		messagePanel.setOpaque(false);

		mMessageLbl = new JLabel(MessageTexts.M002);
		mMessageLbl.setFont(mMessageLbl.getFont().deriveFont(Font.BOLD, 20));
		mMessageLbl.setForeground(Color.WHITE);

		messagePanel.add(mMessageLbl);
		add(messagePanel);

		revalidate();
		repaint();
	}

	private void createBottomPanel() {
		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
		bottomPanel.setOpaque(false);

		mBottomLeftPane = mPlayerList.get(2);
		mBottomLeftPane.setOpaque(false);
		mBottomRightPane = mPlayerList.get(3);
		mBottomRightPane.setOpaque(false);

		bottomPanel.add(mBottomLeftPane);
		bottomPanel.add(mBottomRightPane);
		add(bottomPanel);

		revalidate();
		repaint();
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
		for (int i = 0; i < playerStates.size(); i++) {
			mPlayerList.get(i).setPlayerState(playerStates.get(i));
		}
	}

	private void insertGrillTokenImages() {
		mCenterGrillPanel.removeAll();

		String path = null;
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
		revalidate();
		repaint();
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

		revalidate();
		repaint();
	}

	private void insertButton() {
		mCenterButtonPanel.removeAll();

		JButton buttonContinue = new JButton("Continue Turn");
		buttonContinue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mEndTurn = false;
				if (mDiceValue != null) {
					buildDecision();
				} else {
					mMessageLbl.setText(MessageTexts.M003);
				}

			}
		});
		mCenterButtonPanel.add(buttonContinue);

		JButton buttonStop = new JButton("End Turn");
		buttonStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mEndTurn = true;
				if (mDiceValue != null) {
					buildDecision();
				} else {
					mMessageLbl.setText(MessageTexts.M003);
				}
			}
		});
		mCenterButtonPanel.add(buttonStop);

		revalidate();
		repaint();
	}

	private void buildDecision() {
		String input;

		if (mEndTurn) {
			input = mDiceValue + 'S';
		} else {
			input = mDiceValue + 'C';
		}

		System.out.println(input);
		mClient.createDecisionMessage(input);
		mEndTurn = false;
		mDiceValue = null;
	}

	@Override
	public void showMessage(String message) {
		mMessageLbl.setText(message);
	}

	private class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			mMessageLbl.setText(null);
			JLabel label = (JLabel) event.getSource();
			ImageIcon imageIcon = (ImageIcon) label.getIcon();
			String path = imageIcon.getDescription();
			mDiceValue = path.substring(1, 2);
			String message = MessageTexts.M004 + mDiceValue;
			mMessageLbl.setText(message);
		}
	}

	@Override
	public void endGame(GameEndMessage gameEndMessage) {
		
		removeAll();
		mStatistics = new JPanel(null);
		setSize(mScreenSize);
		mStatistics.setOpaque(false);
		add(mStatistics);
		
		JLabel endLbl = new JLabel(MessageTexts.M021);
		endLbl.setForeground(Color.WHITE);
		endLbl.setFont(endLbl.getFont().deriveFont(Font.BOLD, 20));
		endLbl.setBounds(200, 300, 200, 100);
		mStatistics.add(endLbl);
		
		prepareEndOutput(gameEndMessage.getmPlayers());
	}

	private void prepareEndOutput(List<PlayerState> players) {
		
	}

}
