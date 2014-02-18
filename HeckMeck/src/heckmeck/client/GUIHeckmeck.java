package heckmeck.client;

import java.awt.*;

import javax.swing.*;

public class GUIHeckmeck extends JFrame {

	private static final long serialVersionUID = 4220957201237157813L;
	private JFrame mFenster;

	public GUIHeckmeck() {
		// erzeuge Fenster
		mFenster = new JFrame("Heckmeck");
		mFenster.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		mFenster.setResizable(false);
		mFenster.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mFenster.getContentPane().setBackground(new Color(122, 6, 39));
		mFenster.setLayout(new BorderLayout());
		mFenster.setVisible(true);
		
		
		JPanel pTop = new JPanel();
		pTop.setBackground(Color.yellow);
		pTop.setPreferredSize(new Dimension(1000, 300));
		pTop.add(new JLabel("TOP"));
		mFenster.add(BorderLayout.PAGE_START, pTop);
		mFenster.revalidate();
		
		JPanel pBottom = new JPanel();
		pBottom.setBackground(Color.yellow);
		pBottom.setPreferredSize(new Dimension(1000, 300));
		pBottom.add(new JLabel("BOTTOM"));
		mFenster.add(BorderLayout.PAGE_END, pBottom);
		mFenster.revalidate();
		
		JPanel pLeft = new JPanel();
		pLeft.setBackground(Color.cyan);
		pLeft.setPreferredSize(new Dimension(300, 1000));
		pLeft.add(new JLabel("LEFT"));
		mFenster.add(BorderLayout.LINE_START, pLeft);
		mFenster.revalidate();
		
		JPanel pRight = new JPanel();
		pRight.setBackground(Color.cyan);
		pRight.setPreferredSize(new Dimension(300, 1000));
		pRight.add(new JLabel("RIGHT"));
		mFenster.add(BorderLayout.LINE_END, pRight);
		mFenster.revalidate();
		
		JPanel pCenter = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.ipadx = 5;
		gbc.ipady = 5;
		pCenter.setBackground(new Color(122, 6, 39));
		
		
		pCenter.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/21.png")), gbc);
		mFenster.revalidate();
		pCenter.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/22.png")), gbc);
		mFenster.revalidate();
		pCenter.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/23.png")), gbc);
		mFenster.revalidate();
		pCenter.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/24.png")), gbc);
		mFenster.revalidate();
		pCenter.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/25.png")), gbc);
		mFenster.revalidate();
		pCenter.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/26.png")), gbc);
		mFenster.revalidate();
		pCenter.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/27.png")), gbc);
		mFenster.revalidate();
		pCenter.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/28.png")), gbc);
		mFenster.revalidate();
		pCenter.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/29.png")), gbc);
		mFenster.revalidate();
		pCenter.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/30.png")), gbc);
		mFenster.revalidate();
		pCenter.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/31.png")), gbc);
		mFenster.revalidate();
		pCenter.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/32.png")), gbc);
		mFenster.revalidate();
		pCenter.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/33.png")), gbc);
		mFenster.revalidate();
		pCenter.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/34.png")), gbc);
		mFenster.revalidate();
		pCenter.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/35.png")), gbc);
		mFenster.revalidate();
		pCenter.add(new JLabel(new ImageIcon("C:/Users/marilena.magnasco/Pictures/36.png")), gbc);
		mFenster.revalidate();
		
		mFenster.getContentPane().add(pCenter);
		mFenster.revalidate();

	}

	public static void main(String[] args) {
		GUIHeckmeck gui = new GUIHeckmeck();
	}

}
