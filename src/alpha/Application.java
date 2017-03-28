package alpha;

import java.awt.Dimension;
import java.util.Stack;

import javax.swing.JFrame;

public class Application extends JFrame {
	
	private final Dimension dimension;	
	private final Stack<Activity> stack = new Stack<Activity>();
	
	public Application(Dimension dimension) {
		this.dimension = dimension;
		super.setMinimumSize(dimension);
		super.setResizable(false);
		super.pack();
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setVisible(true);	
	}
	
	public Dimension getDimension() {
		return dimension;
	}
	
	public void pushActivity(Activity activity) {
		if (!stack.empty()) {
			stack.peek().suspend();
		}
		stack.push(activity);
		updateDisplay();
		activity.activate();
	}
	
	public void popActivity() {
		if (!stack.empty()) {
			stack.pop();
			if (!stack.empty()) {
				updateDisplay();
			}
		}
	}
	
	public void updateDisplay() {
		final Activity currentActivity = stack.peek();
		getContentPane().removeAll();	// FIX: Possibly replace, or overhaul layering system
		getContentPane().add(currentActivity);
		currentActivity.revalidate();
		currentActivity.requestFocusInWindow();
		currentActivity.proceed();
	}
	
}
