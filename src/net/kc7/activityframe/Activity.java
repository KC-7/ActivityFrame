package net.kc7.activityframe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class Activity extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private final int ACTIVITY_FRAMERATE;
	
	protected boolean active = false;
	protected Timer timer;
	
	protected static final int UP = KeyEvent.VK_UP;
	protected static final int DOWN = KeyEvent.VK_DOWN;
	protected static final int LEFT = KeyEvent.VK_LEFT;
	protected static final int RIGHT = KeyEvent.VK_RIGHT;
	
	public Activity(int width, int height, int framerate) {	
		ACTIVITY_FRAMERATE = framerate;
		
		setFocusable(true);
		setPreferredSize(new Dimension(width, height));
		addKeyListener(new KeyHandler());
		
		loadResources();
		startActivity();	
	}
	
	// Loads images, audio, and other assets
	protected abstract void loadResources();
	
	// Kicks off the activity
	protected void startActivity() {
		active = true;
		timer = new Timer(ACTIVITY_FRAMERATE, this);
		timer.start();
	}
	
	// Handle data and execute tasks every frame
	protected abstract void processInfo();
	
	// Draws the View every active frame
	protected abstract void drawView(Graphics g);
	
	// Draws the Void for an inactive frame
	protected abstract void drawVoid(Graphics g);
	
	// Handles key values
	protected abstract void handleKey(int key);
	
	@Override
	public final void actionPerformed(ActionEvent e) {
		if (active) processInfo();
		repaint();
	}
	
	@Override
	protected final void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (active) drawView(g);
		else drawVoid(g);
	}
	
	private final class KeyHandler extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			handleKey(e.getKeyCode());
		}
	}
}
