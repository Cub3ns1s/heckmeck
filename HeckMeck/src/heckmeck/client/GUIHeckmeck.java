package heckmeck.client;

import heckmeck.server.GameState;
import heckmeck.server.Token;

import java.awt.*;
import java.util.Iterator;

import javax.swing.*;

public class GUIHeckmeck extends JFrame {

	private static final long serialVersionUID = 4220957201237157813L;
	private JFrame mFrame;
	private GameState mGameState;

	public GUIHeckmeck(GameState gameState) {
		mGameState = gameState;

		createFrame();
		createTopPanel();
		createBottomPanel();
		createLeftPanel();
		createRightPanel();
		createCenterPanel();
	}

	private void createCenterPanel() {
		JPanel pCenter = new JPanel(new GridBagLayout());
		pCenter.setBackground(new Color(122, 6, 39));

		insertGrillTokenImages(pCenter);

		mFrame.getContentPane().add(pCenter);
		mFrame.revalidate();
	}

	private void insertGrillTokenImages(JPanel pCenter) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.ipadx = 5;
		gbc.ipady = 5;

		for (Iterator<Token> iterator = mGameState.getGrill().getTokens()
				.iterator(); iterator.hasNext();) {
			Token token = iterator.next();

			String path = "L:/Ausbildung/Heckmeck/" + token.getValue() + ".png";

			pCenter.add(new JLabel(new ImageIcon(path)), gbc);
			mFrame.revalidate();
		}
	}

	private void createRightPanel() {
		JPanel pRight = new JPanel();
		pRight.setBackground(Color.cyan);
		pRight.setPreferredSize(new Dimension(300, 1000));
		pRight.add(new JLabel("RIGHT"));
		mFrame.add(BorderLayout.LINE_END, pRight);
		mFrame.revalidate();
	}

	private void createLeftPanel() {
		JPanel pLeft = new JPanel();
		pLeft.setBackground(Color.cyan);
		pLeft.setPreferredSize(new Dimension(300, 1000));
		pLeft.add(new JLabel("LEFT"));
		mFrame.add(BorderLayout.LINE_START, pLeft);
		mFrame.revalidate();
	}

	private void createBottomPanel() {
		JPanel pBottom = new JPanel();
		pBottom.setBackground(Color.yellow);
		pBottom.setPreferredSize(new Dimension(1000, 300));
		pBottom.add(new JLabel("BOTTOM"));
		mFrame.add(BorderLayout.PAGE_END, pBottom);
		mFrame.revalidate();
	}

	private void createTopPanel() {
		JPanel pTop = new JPanel();
		pTop.setBackground(Color.yellow);
		pTop.setPreferredSize(new Dimension(1000, 300));
		pTop.add(new JLabel("TOP"));
		mFrame.add(BorderLayout.PAGE_START, pTop);
		mFrame.revalidate();
	}

	private void createFrame() {
		mFrame = new JFrame("Heckmeck");
		mFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		mFrame.setResizable(false);
		mFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mFrame.getContentPane().setBackground(new Color(122, 6, 39));
		mFrame.setLayout(new BorderLayout());
		mFrame.setVisible(true);
	}

	public static void main(GameState[] args) {
		new GUIHeckmeck(args[0]);
	}

}
