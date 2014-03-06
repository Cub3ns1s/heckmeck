package heckmeck.client;

import heckmeck.server.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class GUIHeckmeck extends JFrame implements HeckmeckUI {

	private static final long serialVersionUID = 4220957201237157813L;

	private JFrame mFrame;
	private JTextField mTextField;

	private JPanel mBottomPanel;
	private JPanel mLeftPanel;
	private JPanel mRightPanel;

	private JSplitPane mTopSplitPane;

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
		new Thread(mClient).start();

		createFrame();
		createTopPanel();
		createBottomPanel();
		createLeftPanel();
		createRightPanel();
	}

	private void createCenterPanel() {
		JPanel pCenterTopPane = new JPanel();
		JPanel pCenterBottomPane = new JPanel();

		pCenterTopPane.setBackground(new Color(122, 6, 39));
		pCenterBottomPane.setBackground(new Color(122, 6, 39));

		JSplitPane pCenterSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				pCenterTopPane, pCenterBottomPane);
		pCenterSplitPane.setResizeWeight(0.5);
		setDivider(pCenterSplitPane);

		insertGrillTokenImages(pCenterTopPane);
		insertDices(pCenterBottomPane);

		mFrame.getContentPane().add(pCenterSplitPane);
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
		String path = "";
		java.util.List<Dice> unfixedDices = mGameState.getCurrentPlayer()
				.getDiceState().getUnfixedDices();

		for (Dice dice : unfixedDices) {
			path = "W" + dice.getLabel() + ".png";

			ImageIcon imageIcon = new ImageIcon(path);
			Image image = imageIcon.getImage();
			Dimension dimension = new Dimension(getNewDimension(0.025, 0.04));
			Image scaledImage = image.getScaledInstance((int)dimension.getWidth(), (int)dimension.getHeight(), 4);
			
			pCenterBottomPane.add(new JLabel(new ImageIcon(scaledImage)));
			mFrame.revalidate();
		}

	}

	private void insertGrillTokenImages(JPanel pCenterTopPane) {
		String path = "";
		SortedSet<Token> tokenList = mGameState.getGrill().getTokens();

		for (Token token : tokenList) {
			if (token.isActive()) {
				path = token.getValue() + ".png";

			} else {
				path = "inactiveToken.png";
			}
			
			ImageIcon imageIcon = new ImageIcon(path);
			Image image = imageIcon.getImage();
			Dimension dimension = new Dimension(getNewDimension(0.15, 0.025));
			Image scaledImage = image.getScaledInstance((int)dimension.getWidth(), (int)dimension.getHeight(), 4);
			
			pCenterTopPane.add(new JLabel(new ImageIcon(scaledImage)));
			mFrame.revalidate();
		}
	}

	private void createRightPanel() {
		mRightPanel = new JPanel();
		mRightPanel.setBackground(new Color(122, 6, 39));
		mRightPanel.setPreferredSize(new Dimension(getNewDimension(1, 0.25)));
		mRightPanel.add(new JLabel("RIGHT"));
		mFrame.add(BorderLayout.LINE_END, mRightPanel);
		mFrame.revalidate();
	}

	private void createLeftPanel() {
		mLeftPanel = new JPanel();
		mLeftPanel.setBackground(new Color(122, 6, 39));
		mLeftPanel.setPreferredSize(new Dimension(getNewDimension(1, 0.25)));
		mLeftPanel.add(new JLabel("LEFT"));
		mFrame.add(BorderLayout.LINE_START, mLeftPanel);
		mFrame.revalidate();
	}

	private void createBottomPanel() {
		mBottomPanel = new JPanel();
		mBottomPanel.setBackground(new Color(122, 6, 39));
		mBottomPanel.setPreferredSize(new Dimension(getNewDimension(0.25, 1)));
		mBottomPanel.add(new JLabel("BOTTOM"));
		mFrame.add(BorderLayout.PAGE_END, mBottomPanel);
		mFrame.revalidate();
	}

	private void createTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(122, 6, 39));
		topPanel.setPreferredSize(new Dimension(getNewDimension(0.25, 1)));

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

	private Dimension getNewDimension(double percentageHeight, double percentageWidth) {
		double height = (mScreenSize.getHeight() * percentageHeight );
		double width = (mScreenSize.getWidth() * percentageWidth );
		return new Dimension((int) width, (int) height);
	}

	private void createFrame() {
		mFrame = new JFrame("Heckmeck");
		mFrame.setSize(mScreenSize);
		mFrame.setResizable(true);
		mFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mFrame.getContentPane().setBackground(new Color(122, 6, 39));
		mFrame.setLayout(new BorderLayout());
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
		JPanel leftComp = (JPanel) mTopSplitPane.getLeftComponent();
		leftComp.add(new JLabel(mGameState.getPlayerStates().get(0).getName()));

		Token topToken = mGameState.getPlayerStates().get(0).getDeck()
				.getTopToken();
		if (topToken != null) {
			String path = topToken.getValue() + ".png";
			leftComp.add(new JLabel(new ImageIcon(path)));
		}

		JPanel rightComp = (JPanel) mTopSplitPane.getRightComponent();
		rightComp
				.add(new JLabel(mGameState.getPlayerStates().get(1).getName()));

		topToken = mGameState.getPlayerStates().get(1).getDeck().getTopToken();
		if (topToken != null) {
			String path = topToken.getValue() + ".png";
			rightComp.add(new JLabel(new ImageIcon(path)));
		}
	}

	@Override
	public void showMessage(String message) {
		// TODO Auto-generated method stub

	}

}
