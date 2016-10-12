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
	
	protected boolean active = false;
	private Timer pulser; 
	
	public Activity(int width, int height, int pulse_rate, Color color) {	
		setFocusable(true);
		setPreferredSize(new Dimension(width, height));
		setBackground(color);
		addKeyListener(new KeyHandler());
		
		pulser = new Timer(pulse_rate, this);
	}
	
	// Start, then activate state and start Pulser
	protected final void activate() {
		load();
		start();
		
		active = true;
		pulser.start();
	}
	
	// Stop, then deactivate state and stop Pulser
	protected void deactivate() {
		stop();
		
		active = false;
		pulser.stop();
	}
	
	// Pulse the processor if active, else deactivate. Then graphics
	private void pulseActivity() {
		if (active) {
			pulseProcessor();
		} 
		pulseGraphics(null);
	}

	// Draw graphics
	private void pulseGraphics(Graphics g) {
		if (g == null) {
			repaint();
		} else {
			if (active) {
				drawActive(g);
			} else {
				drawInactive(g);
			}
		}
	}
	// Load images, audio, and other assets
	protected abstract void load();
	
	// Initialize data and start the activity
	protected abstract void start();
	
	// Release resources or write data
	protected abstract void stop();
	
	// Execute tasks on pulser
	protected abstract void pulseProcessor();
	
	// Draw graphics for an active state
	protected abstract void drawActive(Graphics g);
	
	// Draw graphics for an inactive state
	protected abstract void drawInactive(Graphics g);
	
	// Handles press and release of keys
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
		final Set<Integer> pressedKeys = new HashSet<>();
		
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
