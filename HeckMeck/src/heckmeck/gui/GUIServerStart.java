package heckmeck.gui;

import heckmeck.server.Server;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIServerStart extends JPanel {

	private static final long serialVersionUID = -1432504468764188547L;
	private ActionListener mActionListener;
	private JLabel mAmount;
	private JLabel mIP;
	private JTextField mInputText;
	private JButton mBtnStart;

	public GUIServerStart(String ip) {

		mActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				Server server = null;
				
				if (button.getText().equals("Start Server")) {
					String amountPlayers = mInputText.getText();
					mInputText.setEnabled(false);
					mBtnStart.setText("End server");
					
					server = new Server(Integer.valueOf(amountPlayers));
				}
				else {
					server.shutdown();
				}
			}
		};

		setLayout(null);
		setSize(new Dimension(400, 300));
		setBackground(GUIClient.BACKGROUNDCOLOR);

		mAmount = new JLabel("Anzahl Spieler: ");
		mAmount.setBounds(100, 30, 100, 20);
		add(mAmount);

		mInputText = new JTextField(10);
		mInputText.setBounds(200, 30, 100, 20);
		add(mInputText);

		mIP = new JLabel("IP: " + ip);
		mIP.setBounds(165, 60, 100, 20);
		add(mIP);

		mBtnStart = new JButton("Start Server");
		mBtnStart.setBounds(125, 90, 150, 20);
		mBtnStart.addActionListener(mActionListener);
		add(mBtnStart);

		setVisible(true);
	}

}
