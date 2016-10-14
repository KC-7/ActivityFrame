package com.github.kc_7.activityplus;

import javax.swing.JFrame;

public class ActivityPlus extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	protected static Activity activity;
	protected static Launcher launcher;

	public ActivityPlus(String title) {
		
		getContentPane().add(launcher);		
		setResizable(false);
		pack();
		
		setLocationRelativeTo(null);
		setTitle(title);
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
	
	protected void endActivity() {
		
		getContentPane().removeAll();
		getContentPane().add(launcher);
		revalidate();
		
		launcher.requestFocusInWindow();
		launcher.repaint();
		
	}
}
