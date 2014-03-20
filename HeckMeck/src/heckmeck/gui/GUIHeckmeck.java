package heckmeck.gui;

import heckmeck.client.Client;
import heckmeck.client.HeckmeckUI;
import heckmeck.server.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class GUIHeckmeck extends JFrame implements HeckmeckUI {

	private static final Color BACKGROUNDCOLOR = new Color(203, 1, 6);
	private final static int SCREENWIDTH = 400;
	private final static int SCREENHEIGHT = 300;

	private static final long serialVersionUID = 4220957201237157813L;

	private JFrame mFrame;
	private JTextField mTextField;

	private JPanel mBottomPanel;
	private JPanel mLeftPanel;
	private JPanel mRightPanel;

	private JSplitPane mTopSplitPane;
	private JSplitPane mCenterSplitPane;
	
	private GameState mGameState;
	private Client mClient;
	private String mName;
	private static Dimension mScreenSize;
	
	private List<GUIPlayer> mPlayerList;


	public static void main(String[] args) {
		new GUIHeckmeck(args[0]);

	}

	private GUIHeckmeck(String name) {
		mClient = new Client(name, this);
		mName = name;
		mScreenSize = new Dimension( 800, 600 );
		mCenterSplitPane = new JSplitPane();
		new Thread(mClient).start();
		
		mPlayerList = new ArrayList<GUIPlayer>( );

		for (int i = 0; i < 6; i++){
			mPlayerList.add(new GUIPlayer());
		}
		
		createFrame();
		createTopPanel();
		createBottomPanel();
		createLeftPanel();
		createRightPanel();
		
	}

	private void createCenterPanel() {		
		mFrame.remove(mCenterSplitPane);
		
		JPanel pCenterTopPane = new JPanel();
		JPanel pCenterBottomPane = new JPanel();

		pCenterTopPane.setBackground(BACKGROUNDCOLOR);
		pCenterBottomPane.setBackground(BACKGROUNDCOLOR);

		mCenterSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				pCenterTopPane, pCenterBottomPane);
		mCenterSplitPane.setResizeWeight(0.5);
		setDivider(mCenterSplitPane);

		insertGrillTokenImages(pCenterTopPane);
		insertDices(pCenterBottomPane);
		
		
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

	private void insertDices(JPanel pCenterBottomPane) {
		pCenterBottomPane.removeAll();

		java.util.List<Dice> unfixedDices = mGameState.getCurrentPlayer()
				.getDiceState().getUnfixedDices();

		for (Dice dice : unfixedDices) {
			String path = "W" + dice.getLabel() + ".png";

			ImageIcon imageIcon = new ImageIcon(path);
			resizeImageIcon(imageIcon);
			JLabel label = new JLabel(imageIcon);
			label.addMouseListener(new MouseHandler());
			pCenterBottomPane.add(label);
			mFrame.revalidate();
		}

	}

	private void insertGrillTokenImages(JPanel pCenterTopPane) {
		pCenterTopPane.removeAll();

		String path = "";
		SortedSet<Token> tokenList = mGameState.getGrill().getTokens();

		for (Token token : tokenList) {
			if (token.isActive()) {
				path = token.getValue() + ".png";

			} else {
				path = "inactiveToken.png";
			}

			ImageIcon imageIcon = new ImageIcon(path);
			resizeImageIcon(imageIcon);

			pCenterTopPane.add(new JLabel(imageIcon));
			mFrame.revalidate();
		}
	}

	public static void resizeImageIcon(ImageIcon imageIcon) {
		Image image = imageIcon.getImage();
		Image scaledImage = image.getScaledInstance(
				(int) (imageIcon.getIconWidth() * getScreenFactor()),
				(int) (imageIcon.getIconHeight() * getScreenFactor()), 4);
		imageIcon.setImage(scaledImage);
	}

	private void createRightPanel() {
		mRightPanel = new JPanel();
		mRightPanel.setBackground(BACKGROUNDCOLOR);
		mRightPanel.setPreferredSize(new Dimension(getNewDimension(100, 25)));
		mRightPanel.add(new JLabel("RIGHT"));
		mFrame.add(BorderLayout.LINE_END, mRightPanel);
		mFrame.revalidate();
	}

	private void createLeftPanel() {
		mLeftPanel = new JPanel();
		mLeftPanel.setBackground(BACKGROUNDCOLOR);
		mLeftPanel.setPreferredSize(new Dimension(getNewDimension(100, 25)));
		mLeftPanel.add(new JLabel("LEFT"));
		mFrame.add(BorderLayout.LINE_START, mLeftPanel);
		mFrame.revalidate();
	}

	private void createBottomPanel() {
		mBottomPanel = new JPanel();
		mBottomPanel.setBackground(BACKGROUNDCOLOR);
		mBottomPanel.setPreferredSize(new Dimension(getNewDimension(25, 100)));
		mBottomPanel.add(new JLabel("BOTTOM"));
		mFrame.add(BorderLayout.PAGE_END, mBottomPanel);
		mFrame.revalidate();
	}

	private void createTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setBackground(BACKGROUNDCOLOR);
		topPanel.setPreferredSize(new Dimension(getNewDimension(25, 100)));

		JPanel pTopLeftPane = mPlayerList.get(0);
		JPanel pTopRightPane = mPlayerList.get(1);
		
//		int width = (int) (topPanel.getPreferredSize().getWidth()) / 2;
//		int height = (int) (topPanel.getPreferredSize().getHeight());
//		
//		pTopLeftPane.setPreferredSize(new Dimension(width, height));
//		pTopRightPane.setPreferredSize(new Dimension(width, height));
		
		pTopLeftPane.setBackground(BACKGROUNDCOLOR);
		pTopRightPane.setBackground(BACKGROUNDCOLOR);

		mTopSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				pTopRightPane, pTopLeftPane);
		setDivider(mTopSplitPane);

		topPanel.add(mTopSplitPane);
		mFrame.add(BorderLayout.PAGE_START, topPanel);
		mFrame.revalidate();

	}

	private Dimension getNewDimension(int percentageHeight, int percentageWidth) {

		int height = (int) (mScreenSize.getHeight() * percentageHeight / 100);
		int width = (int) (mScreenSize.getWidth() * percentageWidth / 100);
		return new Dimension(width, height);
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

	private void createFrame() {
		mFrame = new JFrame("Heckmeck - " + mName);
		mFrame.setSize(mScreenSize);
		mFrame.setResizable(false);
		mFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mFrame.getContentPane().setBackground(BACKGROUNDCOLOR);
		mFrame.setLayout(new BorderLayout());
		
		mFrame.add(mCenterSplitPane);
		
		mFrame.setVisible(true);
	}

	@Override
	public void update(GameState gameState) {
		mGameState = gameState;
		createCenterPanel();
		updatePlayer(gameState.getPlayerStates());
	}

	

	private void updatePlayer(List<PlayerState> playerStates) {
		for ( int i = 0; i < playerStates.size(); i++) {
			mPlayerList.get(i).setPlayerState(playerStates.get(i));
		}
	}

	@Override
	public void showMessage(String message) {
	}

	private boolean isCurrentPlayer(PlayerState player) {
		if (mGameState.getCurrentPlayer().equals(player)) {
			return true;
		} else {
			return false;
		}
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
