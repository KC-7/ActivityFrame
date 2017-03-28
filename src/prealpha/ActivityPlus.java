package prealpha;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
		
	}
	
	// Swithces from Launcher to Activity and activates it
	protected void launchActivity() {
		
		getContentPane().removeAll();
		getContentPane().add(activity);
		revalidate();
		
		activity.requestFocusInWindow();
		activity.activate();
		
	}
	
	// Switches from Activity to Launcher
	protected void endActivity() {
		
		getContentPane().removeAll();
		getContentPane().add(launcher);
		revalidate();
		
		launcher.requestFocusInWindow();
		launcher.repaint();
		
	}
}
