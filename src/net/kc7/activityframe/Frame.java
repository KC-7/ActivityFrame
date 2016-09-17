package net.kc7.activityframe;

import javax.swing.JFrame;

public class Frame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public Frame(Activity activity, String title) {
		
		setContentPane(activity);
		setTitle(title);
		pack();
		
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
}
