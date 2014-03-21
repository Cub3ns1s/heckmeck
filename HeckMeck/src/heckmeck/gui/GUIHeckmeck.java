package heckmeck.gui;

import heckmeck.client.Client;
import heckmeck.client.HeckmeckUI;
import heckmeck.server.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class GUIHeckmeck extends JFrame implements HeckmeckUI {

	public static final Color BACKGROUNDCOLOR = new Color(203, 1, 6);
	private final static int SCREENWIDTH = 400;
	private final static int SCREENHEIGHT = 300;

	private static final long serialVersionUID = 4220957201237157813L;

	private JFrame mFrame;

	private JPanel mBottomPanel;

	private JSplitPane mTopSplitPane;
	private JSplitPane mCenterSplitPane;
	
	private GameState mGameState;
	private Client mClient;
	private String mName;
	private static Dimension mScreenSize;
	
	private List<GUIPlayer> mPlayerList;
	private JPanel mCenterGrillPanel;
	private JPanel mCenterDicePanel;


	public static void main(String[] args) {
		new GUIHeckmeck(args[0]);
	}

	private GUIHeckmeck(String name) {
		mClient = new Client(name, this);
		mName = name;
		mScreenSize = new Dimension(800, 600);
		new Thread(mClient).start();
		
		mPlayerList = new ArrayList<GUIPlayer>( );

		for (int i = 0; i < 4; i++){
			mPlayerList.add(new GUIPlayer());
		}

		createFrame();
		createCenterPanel( );
		createTopPanel();
		createBottomPanel();

		
	}

	private void createCenterPanel() {		
		mCenterGrillPanel = new JPanel();
		mCenterDicePanel = new JPanel();
		mCenterSplitPane = new JSplitPane();
		
		mCenterGrillPanel.setBackground(BACKGROUNDCOLOR);
		mCenterDicePanel.setBackground(BACKGROUNDCOLOR);

		mCenterSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				mCenterGrillPanel, mCenterDicePanel);
		
		mCenterSplitPane.setResizeWeight(0.5);
		setDivider(mCenterSplitPane);		
		
		mFrame.add(mCenterSplitPane);
		mFrame.revalidate();
	}

	private void setDivider(JSplitPane pCenterSplitPane) {
		// setze den Divider auf Hintergrundfarbe
		pCenterSplitPane.setUI(new BasicSplitPaneUI() {
			public BasicSplitPaneDivider createDefaultDivider() {
				return new BasicSplitPaneDivider(this) {

					public void setBorder(Border b) {
					}

					@Override
					public void paint(Graphics g) {
						g.setColor(Color.cyan);
						g.fillRect(0, 0, getSize().width, getSize().height);
						super.paint(g);
					}
				};
			}
		});

		pCenterSplitPane.setDividerSize(0);
		pCenterSplitPane.setBorder(null);
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
	}

	public static void resizeImageIcon(ImageIcon imageIcon) {
		Image image = imageIcon.getImage();
		Image scaledImage = image.getScaledInstance(
				(int) (imageIcon.getIconWidth() * getScreenFactor()),
				(int) (imageIcon.getIconHeight() * getScreenFactor()), 4);
		imageIcon.setImage(scaledImage);
	}


	private void createBottomPanel() {
		mBottomPanel = new JPanel();
		mBottomPanel.setBackground(BACKGROUNDCOLOR);
		mBottomPanel.setPreferredSize(new Dimension(800, 200));
		mBottomPanel.add(new JLabel("BOTTOM"));
		mFrame.add(BorderLayout.PAGE_END, mBottomPanel);
		mFrame.revalidate();
	}

	private void createTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setBackground(BACKGROUNDCOLOR);
		topPanel.setPreferredSize(new Dimension(800, 200));

		JPanel pTopLeftPane = mPlayerList.get(0);
		JPanel pTopRightPane = mPlayerList.get(1);
		
		pTopLeftPane.setBackground(BACKGROUNDCOLOR);
		pTopRightPane.setBackground(BACKGROUNDCOLOR);

		mTopSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				pTopRightPane, pTopLeftPane);
		setDivider(mTopSplitPane);

		topPanel.add(mTopSplitPane);
		mFrame.add(BorderLayout.PAGE_START, topPanel);
		mFrame.revalidate();
	}

//	private Dimension getNewDimension(int percentageHeight, int percentageWidth) {
//
//		int height = (int) (mScreenSize.getHeight() * percentageHeight / 100);
//		int width = (int) (mScreenSize.getWidth() * percentageWidth / 100);
//		return new Dimension(width, height);
//	}

	private static double getScreenFactor() {
		double xFactor = SCREENWIDTH / mScreenSize.getWidth();
		double yFactor = SCREENHEIGHT / mScreenSize.getHeight();

		if (xFactor > yFactor) {
			return yFactor;
		} else {
			return xFactor;
		}
	}

	private void createFrame() {
		mFrame = new JFrame("Heckmeck - " + mName);
		mFrame.setSize(mScreenSize);
		mFrame.setResizable(false);
		mFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mFrame.getContentPane().setBackground(BACKGROUNDCOLOR);
		mFrame.setLayout(new BorderLayout());
		
		mFrame.setVisible(true);
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
	}

	private void updatePlayer(List<PlayerState> playerStates) {
		for ( int i = 0; i < playerStates.size(); i++) {
			mPlayerList.get(i).setPlayerState(playerStates.get(i));
		}
	}

	@Override
	public void showMessage(String message) {
	}

//	private boolean isCurrentPlayer(PlayerState player) {
//		if (mGameState.getCurrentPlayer().equals(player)) {
//			return true;
//		} else {
//			return false;
//		}
//	}

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
