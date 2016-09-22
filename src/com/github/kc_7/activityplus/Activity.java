package com.github.kc_7.activityplus;

import java.awt.Color;
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
	public static int PULSE_DELAY;
	public static int WIDTH;
	public static int HEIGHT;
	public static int COLOR;
	
	protected boolean active = false;
	protected Timer pulse;
	
	protected static final int KEY_UP = KeyEvent.VK_UP;
	protected static final int KEY_DOWN = KeyEvent.VK_DOWN;
	protected static final int KEY_LEFT = KeyEvent.VK_LEFT;
	protected static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
	
	public Activity() {	
		
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(new Color(COLOR));
		addKeyListener(new KeyHandler());
		
		loadResources();
		activate();	
	}
	
	// Start, then activate state and start Timer
	private void activate() {
		start();
		
		active = true;
		pulse = new Timer(PULSE_RATE, this);
		pulse.setInitialDelay(PULSE_DELAY);
		pulse.start();
	}
	
	// Stop, then deactivate and stop Timer
	private void deactivate() {
		stop();
		
		active = false;		// Will be investigated, replaced, and/or deprecated in the near future
		pulse.stop();
	}
	
	// Pulse the processor if active, then graphics
	private void pulseActivity() {
		if (active) {
			pulseProcessor();
		} else {
			deactivate();
		}
		pulseGraphics(null);
	}

	// Draw graphics
	private void pulseGraphics(Graphics g) {
		if (g == null) {
			repaint();
		} else {
			if (active) drawActive(g);
			else drawInactive(g);
		}
	}
	
	// Initialize data and start the activity
	protected abstract void start();
	
	// OPTIONAL -- Release resources or write data
	protected void stop() {}
	
	// Load images, audio, and other assets
	protected abstract void loadResources();
	
	// Execute tasks on pulse
	protected abstract void pulseProcessor();
	
	// Draw graphics for an active state
	protected abstract void drawActive(Graphics g);
	
	// Draw graphics for an inactive state
	protected abstract void drawInactive(Graphics g);
	
	// Handle key values
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
	
	private class KeyHandler extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			handleKey(e.getKeyCode());
		}
	}
}
