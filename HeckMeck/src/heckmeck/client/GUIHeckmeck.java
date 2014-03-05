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

	private JPanel mTopPanel;
	private JPanel mBottomPanel;
	private JPanel mLeftPanel;

	private JSplitPane mRightSplitPane;
	private JSplitPane mTopSplitPane;

	private GameState mGameState;
	private Client mClient;
	private String mName;

	public static void main(String[] args) {
		new GUIHeckmeck(args[0]);

	}

	private GUIHeckmeck(String name) {

		mClient = new Client(name, this);
		mName = name;
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

			pCenterBottomPane.add(new JLabel(new ImageIcon(path)));
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
			pCenterTopPane.add(new JLabel(new ImageIcon(path)));
			mFrame.revalidate();
		}
	}

	private void createRightPanel() {
		JPanel pRightTopPane = new JPanel();
		JPanel pRightBottomPane = new JPanel();

		pRightTopPane.setBackground(new Color(122, 6, 39));
		pRightBottomPane.setBackground(new Color(122, 6, 39));

		mRightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				pRightTopPane, pRightBottomPane);
		mRightSplitPane.setPreferredSize(new Dimension(100, 1000));
		mRightSplitPane.setResizeWeight(0.5);
		setDivider(mRightSplitPane);

		mFrame.getContentPane().add(BorderLayout.LINE_END, mRightSplitPane);
		mFrame.revalidate();
	}

	private void createLeftPanel() {
		mLeftPanel = new JPanel();
		mLeftPanel.setBackground(new Color(122, 6, 39));
		mLeftPanel.setPreferredSize(new Dimension(100, 1000));
		mLeftPanel.add(new JLabel("LEFT"));
		mFrame.add(BorderLayout.LINE_START, mLeftPanel);
		mFrame.revalidate();
	}

	private void createBottomPanel() {
		mBottomPanel = new JPanel();
		mBottomPanel.setBackground(new Color(122, 6, 39));
		mBottomPanel.setPreferredSize(new Dimension(1000, 100));
		mBottomPanel.add(new JLabel("BOTTOM"));
		mFrame.add(BorderLayout.PAGE_END, mBottomPanel);
		mFrame.revalidate();
	}

	private void createTopPanel() {
		JPanel pTopLeftPane = new JPanel();
		JPanel pTopRightPane = new JPanel();

		pTopLeftPane.setBackground(new Color(122, 6, 39));
		pTopLeftPane.setVisible(true);

		pTopRightPane.setBackground(new Color(122, 6, 39));
		pTopRightPane.setVisible(true);

		mTopSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				pTopRightPane, pTopLeftPane);
		mTopSplitPane.setPreferredSize(new Dimension(100, 1000));
		mTopSplitPane.setResizeWeight(0.5);
		setDivider(mTopSplitPane);

		mTopSplitPane.setVisible(true);
		mFrame.getContentPane().add(BorderLayout.PAGE_START, mTopSplitPane);
		mFrame.revalidate();

		// mTopPanel = new JPanel();
		// mTopPanel.setBackground(new Color(122, 6, 39));
		// mTopPanel.setPreferredSize(new Dimension(1000, 100));
		// mTopPanel.add(new JLabel("TOP"));
		// mTopPanel.add(new JLabel("Decision:"));
		// mTextField = new JTextField("5C");
		// JButton moveButton = new JButton("Move");
		// moveButton.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// mClient.createDecisionMessage(mTextField.getText());
		// }
		// });
		// mTopPanel.add(moveButton);
		// mTopPanel.add(mTextField);
		// mFrame.add(BorderLayout.PAGE_START, mTopPanel);
		// mFrame.revalidate();
	}

	private void createFrame() {
		mFrame = new JFrame("Heckmeck");
		mFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
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

		JPanel rightComp = (JPanel) mTopSplitPane.getRightComponent();
		rightComp
				.add(new JLabel(mGameState.getPlayerStates().get(1).getName()));
	}

	@Override
	public void showMessage(String message) {
		// TODO Auto-generated method stub

	}

}
