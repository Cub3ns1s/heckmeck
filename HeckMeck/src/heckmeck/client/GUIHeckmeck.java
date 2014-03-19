package heckmeck.client;

import heckmeck.server.*;

import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class GUIHeckmeck extends JFrame implements HeckmeckUI {

	private final int SCREENWIDTH = 1024;
	private final int SCREENHEIGHT = 768;

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
	private Dimension mScreenSize;


	public static void main(String[] args) {
		new GUIHeckmeck(args[0]);

	}

	private GUIHeckmeck(String name) {

		mClient = new Client(name, this);
		mName = name;
		mScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mCenterSplitPane = new JSplitPane();
		new Thread(mClient).start();

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

		pCenterTopPane.setBackground(new Color(122, 6, 39));
		pCenterBottomPane.setBackground(new Color(122, 6, 39));

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

	private void resizeImageIcon(ImageIcon imageIcon) {
		Image image = imageIcon.getImage();
		Image scaledImage = image.getScaledInstance(
				(int) (imageIcon.getIconWidth() * getScreenFactor()),
				(int) (imageIcon.getIconHeight() * getScreenFactor()), 4);
		imageIcon.setImage(scaledImage);
	}

	private void createRightPanel() {
		mRightPanel = new JPanel();
		mRightPanel.setBackground(new Color(122, 6, 39));
		mRightPanel.setPreferredSize(new Dimension(getNewDimension(100, 25)));
		mRightPanel.add(new JLabel("RIGHT"));
		mFrame.add(BorderLayout.LINE_END, mRightPanel);
		mFrame.revalidate();
	}

	private void createLeftPanel() {
		mLeftPanel = new JPanel();
		mLeftPanel.setBackground(new Color(122, 6, 39));
		mLeftPanel.setPreferredSize(new Dimension(getNewDimension(100, 25)));
		mLeftPanel.add(new JLabel("LEFT"));
		mFrame.add(BorderLayout.LINE_START, mLeftPanel);
		mFrame.revalidate();
	}

	private void createBottomPanel() {
		mBottomPanel = new JPanel();
		mBottomPanel.setBackground(new Color(122, 6, 39));
		mBottomPanel.setPreferredSize(new Dimension(getNewDimension(25, 100)));
		mBottomPanel.add(new JLabel("BOTTOM"));
		mFrame.add(BorderLayout.PAGE_END, mBottomPanel);
		mFrame.revalidate();
	}

	private void createTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(122, 6, 39));
		topPanel.setPreferredSize(new Dimension(getNewDimension(25, 100)));

		JPanel pTopLeftPane = new JPanel();
		JPanel pTopRightPane = new JPanel();

		pTopLeftPane.setBackground(new Color(122, 6, 39));
		pTopRightPane.setBackground(new Color(122, 6, 39));

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

	private double getScreenFactor() {
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
		mFrame.getContentPane().setBackground(new Color(122, 6, 39));
		mFrame.setLayout(new BorderLayout());
		
		mFrame.add(mCenterSplitPane);
		
		mFrame.setVisible(true);
	}

	@Override
	public void update(GameState gameState) {
		mGameState = gameState;
		createCenterPanel();
		createPlayerAreas();
		mFrame.pack();
	}

	private void createPlayerAreas() {

		switch (mGameState.getPlayerStates().size()) {
		case 2:
			set2players();
			break;

		case 3:
			// set3players();
			break;

		case 4:
			// set4players();
			break;

		case 5:
			// set5players();
			break;

		case 6:
			// set6players();
			break;

		default:
			break;
		}

	}

	private void set2players() {

		// linker Spieler
		JPanel leftComp = (JPanel) mTopSplitPane.getLeftComponent();
		leftComp.removeAll();

		PlayerState player = mGameState.getPlayerStates().get(0);
		JLabel nameLeft = new JLabel(player.getName());
		if (isCurrentPlayer(player)) {
			nameLeft.setOpaque(true);
			nameLeft.setBackground(Color.ORANGE);
		}
		leftComp.add(nameLeft);

		Token topToken = mGameState.getPlayerStates().get(0).getDeck()
				.getTopToken();
		if (topToken != null) {
			String path = topToken.getValue() + ".png";
			leftComp.add(new JLabel(new ImageIcon(path)));
		}

		java.util.List<Dice> fixedDices1 = mGameState.getPlayerStates().get(0)
				.getDiceState().getFixedDices();
		for (Iterator iterator = fixedDices1.iterator(); iterator.hasNext();) {
			Dice dice = (Dice) iterator.next();
			String path = "W" + dice.getLabel() + ".png";
			leftComp.add(new JLabel(new ImageIcon(path)));
		}

		// rechter Spieler
		JPanel rightComp = (JPanel) mTopSplitPane.getRightComponent();
		rightComp.removeAll();
		player = mGameState.getPlayerStates().get(1);
		JLabel nameRight = new JLabel(player.getName());

		if (isCurrentPlayer(player)) {
			nameRight.setOpaque(true);
			nameRight.setBackground(Color.ORANGE);
		}

		rightComp.add(nameRight);

		topToken = mGameState.getPlayerStates().get(1).getDeck().getTopToken();
		if (topToken != null) {
			String path = topToken.getValue() + ".png";
			rightComp.add(new JLabel(new ImageIcon(path)));
		}

		java.util.List<Dice> fixedDices2 = mGameState.getPlayerStates().get(1)
				.getDiceState().getFixedDices();
		for (Iterator iterator = fixedDices2.iterator(); iterator.hasNext();) {
			Dice dice = (Dice) iterator.next();
			String path = "W" + dice.getLabel() + ".png";
			rightComp.add(new JLabel(new ImageIcon(path)));
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
