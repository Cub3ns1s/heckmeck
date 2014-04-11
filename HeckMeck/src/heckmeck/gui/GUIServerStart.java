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
	private JLabel mAmount;
	private JLabel mIP;
	private JTextField mInputText;
	private JButton mBtnStart;
	private JButton mBtnStop;
	private Server mServer;

	public GUIServerStart(String ip) {

		new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				
				if (button.getText().equals("Start Server")) {
					String amountPlayers = mInputText.getText();
					mInputText.setEnabled(false);
					mBtnStart.setText("End server");
					
					mServer = new Server(Integer.valueOf(amountPlayers));
				}
				else {
					mServer.shutdown();
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
		mInputText.setText("2");
		add(mInputText);

		mIP = new JLabel("IP: " + ip);
		mIP.setBounds(165, 60, 100, 20);
		add(mIP);

		mBtnStart = new JButton("Start Server");
		mBtnStart.setBounds(125, 90, 150, 20);
		mBtnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					startServer( );
				}
			}
		);
		add(mBtnStart);

		mBtnStop = new JButton("Stop Server");
		mBtnStop.setBounds(125, 90, 150, 20);
		mBtnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopServer( );
			}
		}
	);
		setVisible(true);
	}
	
	private void startServer() {
		String amountPlayers = mInputText.getText();
		mInputText.setEnabled(false);		
		remove( mBtnStart);
		add(mBtnStop);			
		mServer = new Server(Integer.valueOf(amountPlayers));
		new Thread( mServer ).start();
	}
	private void stopServer( ){
		remove( mBtnStop);
		add(mBtnStart);		
		mInputText.setEnabled(true);
		mServer.shutdown();
	}

}
