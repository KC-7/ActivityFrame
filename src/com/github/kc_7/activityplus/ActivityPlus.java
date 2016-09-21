package com.github.kc_7.activityplus;

import javax.swing.JFrame;

public class ActivityPlus extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public ActivityPlus(Activity activity, String title) {
		setContentPane(activity);
		setResizable(false);
		pack();
		setTitle(title);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
