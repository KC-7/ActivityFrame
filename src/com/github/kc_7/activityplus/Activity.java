package com.github.kc_7.activityplus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class Activity extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public static int PULSE_RATE;
	public static int PULSE_DELAY;
	public static int WIDTH;
	public static int HEIGHT;
	public static Color COLOR;
	
	protected boolean active = true;
	private Timer pulse;
	
	public Activity() {	
		
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(COLOR);
		addKeyListener(new KeyHandler());
		
		load();
		activate();	
	}
	
	// Start, then activate state and start Timer
	protected final void activate() {
		start();
		
		active = true;
		pulse = new Timer(PULSE_RATE, this);
		pulse.setInitialDelay(PULSE_DELAY);
		pulse.start();
	}
	
	// Stop Timer
	private void deactivate() {
		stop();
		
		pulse.stop();
	}
	
	// Pulse the processor if active, else deactivate. Then graphics
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
	
	// Release resources or write data
	protected abstract void stop();
	
	// Load images, audio, and other assets
	protected abstract void load();
	
	// Execute tasks on pulse
	protected abstract void pulseProcessor();
	
	// Draw graphics for an active state
	protected abstract void drawActive(Graphics g);
	
	// Draw graphics for an inactive state
	protected abstract void drawInactive(Graphics g);
	
	
	protected abstract void handleKey(Set<Integer> pressedKeys);
	
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
		
		Set<Integer> pressedKeys = new HashSet<>();
		
		@Override
		public synchronized void keyPressed(KeyEvent e) {
			pressedKeys.add(e.getKeyCode());
			handleKey(pressedKeys);
		}
		
		@Override
		public synchronized void keyReleased(KeyEvent e) {
			pressedKeys.remove(e.getKeyCode());
		}
	}
}
