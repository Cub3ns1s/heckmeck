package heckmeck.gui;

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

	public GUIServerStart() {

		mActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = mInputText.getText();
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

		mIP = new JLabel("IP: 127.0.0.1");
		mIP.setBounds(165, 60, 100, 20);
		add(mIP);

		mBtnStart = new JButton("Starte Server");
		mBtnStart.setBounds(125, 90, 150, 20);
		mBtnStart.addActionListener(mActionListener);
		add(mBtnStart);

		setVisible(true);
	}

}
