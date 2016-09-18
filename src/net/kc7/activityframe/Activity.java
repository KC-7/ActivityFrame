package net.kc7.activityframe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Activity extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private final int ACTIVITY_WIDTH;
	private final int ACTIVITY_HEIGHT;
	private final int ACTIVITY_FRAMERATE;
	
	private boolean active = false;
	
	protected static final int UP = KeyEvent.VK_UP;
	protected static final int DOWN = KeyEvent.VK_DOWN;
	protected static final int LEFT = KeyEvent.VK_LEFT;
	protected static final int RIGHT = KeyEvent.VK_RIGHT;
	
	public Activity(int width, int height, int framerate) {	
		ACTIVITY_WIDTH = width;
		ACTIVITY_HEIGHT = height;
		ACTIVITY_FRAMERATE = framerate;
		
		setFocusable(true);
		setPreferredSize(new Dimension(ACTIVITY_WIDTH, ACTIVITY_HEIGHT));
		addKeyListener(new KeyHandler());
		
		loadResources();
		startActivity();	
	}
	
	// OVERRIDE -- Loads images, audio, and other assets
	protected boolean loadResources() {
		return true;
	}
	
	// DEFAULT -- Kicks off the activity
	protected boolean startActivity() {
		active = true;
		Timer timer = new Timer(ACTIVITY_FRAMERATE, this);
		timer.start();
		
		return true;
	}
	
	// OVERRIDE -- Handle data and execute tasks every frame
	private boolean processInfo() {
		return true;
	}
	
	@Override
	public final void actionPerformed(ActionEvent e) {
		if (active) processInfo();
		repaint();
	}
	
	@Override
	public final void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (active) drawView(g);
		else drawVoid(g);
	}
	
	// OVERRIDE -- Draws the View every active frame
	protected boolean drawView(Graphics g) {
		return true;
	}
	
	protected boolean drawVoid(Graphics g) {
		return true;
	}
	
	private boolean handleKey(int key) {
		return true;
	}
	
	private final class KeyHandler extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			
			handleKey(e.getKeyCode());
			
		}
	}
}
