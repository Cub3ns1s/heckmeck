package heckmeck.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUILogon extends JPanel{

	private static final long serialVersionUID = -4583822138415796314L;
	private JLabel mPicture;
	private JLabel mName;
	private JLabel mIP;
	private JTextField mInputName;
	private JTextField mInputIP;
	private JButton mLogon;
	private JButton mStartServer;
	private ActionListener mActionListener;

	public GUILogon() {
		setLayout(null);
		setSize(new Dimension(800, 600));
		setBackground(GUIClient.BACKGROUNDCOLOR);
		
		mActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				
				if (button.getText().equals(mStartServer.getText())) {
					new GUIServer();
				}
				else {
					
				}
			}
		};
		
		mPicture = new JLabel(new ImageIcon("huehner.gif"));
		mPicture.setBounds(250, 30, 292, 226);
		add(mPicture);
		
		mName = new JLabel("Name: ");
		mName.setBounds(320, 300, 100, 20);
		add(mName);
		
		mInputName = new JTextField(10);
		mInputName.setBounds(370, 300, 100, 20);
		add(mInputName);
		
		mIP = new JLabel("IP: ");
		mIP.setBounds(320, 330, 100, 20);
		add(mIP);
		
		mInputIP = new JTextField(10);
		mInputIP.setBounds(370, 330, 100, 20);
		add(mInputIP);
		
		mLogon = new JButton("Logon");
		mLogon.setBounds(285, 365, 110, 20);
		add(mLogon);
		
		mStartServer = new JButton("Start Server");
		mStartServer.setBounds(405, 365, 110, 20);
		mStartServer.addActionListener(mActionListener);
		add(mStartServer);
		
		setVisible(true);
	}
	
}
