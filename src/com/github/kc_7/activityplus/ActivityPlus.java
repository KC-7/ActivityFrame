package com.github.kc_7.activityplus;

import javax.swing.JFrame;

public class ActivityPlus extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Activity activity;
	private Launcher launcher;

	public ActivityPlus(Activity activity, Launcher launcher, String title) {
		
		this.activity = activity;
		this.launcher = launcher;
		
		getContentPane().add(launcher);		
		setResizable(false);
		pack();
		
		setLocationRelativeTo(null);
		setTitle(title);
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
