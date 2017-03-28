package prealpha;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public abstract class Launcher extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	
	// Indicate if the Launcher has launched before
	private boolean launched = false;
	
	// Contains codes of pressed keyList
	private final Set<Integer> keys = new HashSet<>();
	
	public Launcher(int width, int height, Color color) {
		
		setFocusable(true);
		setPreferredSize(new Dimension(width, height));
		setBackground(color);
		addKeyListener(this);
		
	}
	
	protected final void launch() {
		
		// Clear keyList list
		keys.clear();
		
		// Launch activity of the ActivityPlus corresponding to this Launcher
		final ActivityPlus plus = (ActivityPlus) SwingUtilities.getWindowAncestor(this);
		plus.launchActivity();
		launched = true;
		
	}
	
	// Draw Launcher before launched
	protected abstract void drawLauncher(Graphics g);
	
	// Draw Launcher after launched
	protected abstract void drawEnded(Graphics g);
	
	// Handle key presses
	protected abstract void keyPress(Set<Integer> keys);
	
	@Override
	protected final void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (launched) {
			drawEnded(g);
		} else {
			drawLauncher(g);
		}
		
	}
	
	@Override 
	public synchronized void keyTyped(KeyEvent e) {}
	
	@Override
	public synchronized void keyPressed(KeyEvent e) {
		
		// Add key code to keyList list
		keys.add(e.getKeyCode());
		
		// Call handler for key press
		keyPress(keys);
		
	}
	
	@Override 
	public synchronized void keyReleased(KeyEvent e) {}
	
}
