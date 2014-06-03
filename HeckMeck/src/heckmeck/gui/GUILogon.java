package heckmeck.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUILogon extends GUIBackground{

	private static final long serialVersionUID = -4583822138415796314L;
	private JLabel mPicture;
	private JLabel mName;
	private JLabel mIP;
	private JTextField mInputName;
	private JTextField mInputIP;
	private JButton mLogon;
	private JButton mStartServer;
	private ActionListener mActionListener;
	private JPanel mMain;

	public GUILogon() {
		
		setSize(new Dimension(1000, 800));
		
		mMain = new JPanel(null);
		mMain.setOpaque(false);
		mMain.setSize(new Dimension(1000, 800));
		add(mMain);
		
		mActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				
				if (button.getText().equals(mStartServer.getText())) {
					if(!mInputIP.getText().isEmpty()){
					new Thread( new GUIServer(mInputIP.getText())).start( );
				}
				}
				else {
					mMain.removeAll();
					mMain.add(new GUIGame(mInputName.getText(), mInputIP.getText()));
					mMain.revalidate();
				}
			}
		};
		
		mPicture = new JLabel(new ImageIcon("huehner.gif"));
		mPicture.setBounds(325, 180, 292, 226);
		mPicture.repaint();
		mMain.add(mPicture);
		
		mName = new JLabel("Name: ");
		mName.setBounds(390, 440, 100, 20);
		mName.setForeground(Color.WHITE);
		mMain.add(mName);
		
		mInputName = new JTextField(10);
		mInputName.setBounds(440, 440, 100, 20);
		mMain.add(mInputName);
		
		mIP = new JLabel("IP: ");
		mIP.setBounds(390, 470, 100, 20);
		mIP.setForeground(Color.WHITE);
		mMain.add(mIP);
		
		mInputIP = new JTextField(10);
		mInputIP.setBounds(440, 470, 100, 20);
		mInputIP.setText("127.0.0.1");
		mMain.add(mInputIP);
		
		mLogon = new JButton("Logon");
		mLogon.setBounds(355, 505, 110, 20);
		mLogon.addActionListener(mActionListener);
		mMain.add(mLogon);
		
		mStartServer = new JButton("Start Server");
		mStartServer.setBounds(475, 505, 110, 20);
		mStartServer.addActionListener(mActionListener);
		mMain.add(mStartServer);
		
		setVisible(true);
	}
	
}
