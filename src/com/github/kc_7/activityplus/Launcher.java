package com.github.kc_7.activityplus;

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
	
	private boolean launched = false;
	private final Set<Integer> keys = new HashSet<>();
	
	public Launcher(int width, int height, Color color) {
		
		setFocusable(true);
		setPreferredSize(new Dimension(width, height));
		setBackground(color);
		addKeyListener(this);
		
	}
	
	protected final void launch() {
		
		keys.clear();
		
		final ActivityPlus plus = (ActivityPlus) SwingUtilities.getWindowAncestor(this);
		plus.launchActivity();
		
		launched = true;
		
	}
	
	protected abstract void drawLauncher(Graphics g);
	
	protected abstract void drawEnded(Graphics g);
	
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
	
	@Override public synchronized void keyTyped(KeyEvent e) {}
	
	@Override
	public synchronized void keyPressed(KeyEvent e) {
		
		keys.add(e.getKeyCode());
		keyPress(keys);
		
	}
	
	@Override public synchronized void keyReleased(KeyEvent e) {}
	
}
