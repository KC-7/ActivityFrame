package com.github.kc_7.activityplus;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ActivityPlus extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public ActivityPlus(JPanel panel, String title) {
		
		add(panel);
		setResizable(false);
		pack();
		
		setTitle(title);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
