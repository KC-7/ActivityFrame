package com.github.kc_7.activityplus;

import javax.swing.JFrame;

public class ActivityPlus extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	protected static Activity activity;

	public ActivityPlus(Launcher launcher, String title) {
		
		getContentPane().add(launcher);		
		setResizable(false);	
		pack();
		
		setTitle(title);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
	}
	
	protected void launchActivity() {
		
		getContentPane().removeAll();
		getContentPane().add(activity);
		revalidate();
		
		activity.requestFocusInWindow();
		activity.activate();
	}
}
