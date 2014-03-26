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
	private ActionListener al;
	private JLabel lblAmount;
	private JLabel lblIP;
	private JTextField txtField;
	private JButton btnStart;

	public GUIServerStart() {

		al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = txtField.getText();
			}
		};

		setLayout(null);
		setSize(new Dimension(400, 300));

		lblAmount = new JLabel("Anzahl Spieler: ");
		lblAmount.setBounds(100, 30, 100, 20);
		add(lblAmount);

		txtField = new JTextField(10);
		txtField.setBounds(200, 30, 100, 20);
		add(txtField);

		lblIP = new JLabel("IP: 127.0.0.1");
		lblIP.setBounds(165, 60, 100, 20);
		add(lblIP);

		btnStart = new JButton("Starte Server");
		btnStart.setBounds(125, 90, 150, 20);
		btnStart.addActionListener(al);
		add(btnStart);

		setVisible(true);
	}

}
