package com.github.kc_7.activityplus;

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
	
	public static int PULSE_RATE;
	public static int WIDTH;
	public static int HEIGHT;
	
	protected boolean active = false;
	protected Timer timer;
	
	protected static final int UP = KeyEvent.VK_UP;
	protected static final int DOWN = KeyEvent.VK_DOWN;
	protected static final int LEFT = KeyEvent.VK_LEFT;
	protected static final int RIGHT = KeyEvent.VK_RIGHT;
	
	public Activity() {	
		
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(new KeyHandler());
		
		loadResources();
		startActivity();	
	}
	
	// Set active and start the Timer
	protected void startActivity() {
		active = true;
		timer = new Timer(PULSE_RATE, this);
		timer.start();
	}
	
	// Set inactive and stop the Timer
	protected void stopActivity() {
		active = false;
		timer.stop();
	}
	
	// Pulses the processor if active and graphics
	private void pulseActivity() {
		if (active) pulseProcessor();
		repaint();
	}
	
	// Draws graphics
	private void pulseGraphics(Graphics g) {
		if (active) drawActive(g);
		else drawInactive(g);
	}
	
	// Load images, audio, and other assets
	protected abstract void loadResources();
	
	// Execute checks and tasks on pulse
	protected abstract void pulseProcessor();
	
	// Draws graphics for an active state
	protected abstract void drawActive(Graphics g);
	
	// Draws graphics for an inactive state
	protected abstract void drawInactive(Graphics g);
	
	// Handles key values
	protected abstract void handleKey(int key);
	
	@Override
	public final void actionPerformed(ActionEvent e) {
		pulseActivity();
	}
	
	@Override
	protected final void paintComponent(Graphics g) {
		super.paintComponent(g);
		pulseGraphics(g);
	}
	
	private final class KeyHandler extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			handleKey(e.getKeyCode());
		}
	}
}
