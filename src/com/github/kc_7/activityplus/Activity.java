package com.github.kc_7.activityplus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public abstract class Activity extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	
	protected Timer timer; 
	private final Set<Integer> pressedKeys = new HashSet<>();
	
	public Activity(int width, int height, int rate, Color color) {
		
		setFocusable(true);
		setPreferredSize(new Dimension(width, height));
		setBackground(color);
		addKeyListener(this);
		
		timer = new Timer(rate, this);
	}
	
	// Activates the activity
	protected final void activate() {
		
		load();
		start();
		
		timer.start();
		
	}
	
	protected final void suspend() {
		
		timer.stop();
		
	}
	
	protected final void resume() {
		
		timer.start();
		
	}
	
	// Deactivates the activity
	protected final void deactivate() {
		
		stop();
		
		timer.stop();
		
		final ActivityPlus plus = (ActivityPlus) SwingUtilities.getWindowAncestor(this);
		plus.endActivity();
		
	}
	
	protected abstract void load();
	
	protected abstract void start();
	
	protected abstract void stop();
	
	protected abstract void process();
	
	protected abstract void draw(Graphics g);
	
	protected abstract void handleKey(Set<Integer> pressedKeys);
	
	@Override
	public final void actionPerformed(ActionEvent e) {
		
		process();
		repaint();
		
	}
	
	@Override
	protected final void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		draw(g);
		
	}
	
	@Override
	public synchronized void keyTyped(KeyEvent e) {}
	
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
