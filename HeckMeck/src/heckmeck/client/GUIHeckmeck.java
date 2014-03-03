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

		JSplitPane pRightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				pRightTopPane, pRightBottomPane);
		pRightSplitPane.setPreferredSize(new Dimension(100, 1000));
		pRightSplitPane.setResizeWeight(0.5);
		setDivider(pRightSplitPane);

		mFrame.getContentPane().add(pRightSplitPane);
		mFrame.revalidate();
		
//		JPanel pRight = new JPanel();
//		pRight.setBackground(new Color(122, 6, 39));
//		pRight.setPreferredSize(new Dimension(100, 1000));
//		pRight.add(new JLabel("RIGHT"));
//		mFrame.add(BorderLayout.LINE_END, pRight);
//		mFrame.revalidate();
	}

	private void createLeftPanel() {
		JPanel pLeft = new JPanel();
		pLeft.setBackground(new Color(122, 6, 39));
		pLeft.setPreferredSize(new Dimension(100, 1000));
		pLeft.add(new JLabel("LEFT"));
		mFrame.add(BorderLayout.LINE_START, pLeft);
		mFrame.revalidate();
	}

	private void createBottomPanel() {
		JPanel pBottom = new JPanel();
		pBottom.setBackground(new Color(122, 6, 39));
		pBottom.setPreferredSize(new Dimension(1000, 100));
		pBottom.add(new JLabel("BOTTOM"));
		mFrame.add(BorderLayout.PAGE_END, pBottom);
		mFrame.revalidate();
	}

	private void createTopPanel() {
		JPanel pTop = new JPanel();
		pTop.setBackground(new Color(122, 6, 39));
		pTop.setPreferredSize(new Dimension(1000, 100));
		pTop.add(new JLabel("TOP"));
		pTop.add(new JLabel("Decision:"));
		mTextField = new JTextField("5C");
		JButton moveButton = new JButton("Move");
		moveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mClient.createDecisionMessage(mTextField.getText());
			}
		});
		pTop.add(moveButton);
		pTop.add(mTextField);
		mFrame.add(BorderLayout.PAGE_START, pTop);
		mFrame.revalidate();
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

	}

	@Override
	public void showMessage(String message) {
		// TODO Auto-generated method stub

	}

}
