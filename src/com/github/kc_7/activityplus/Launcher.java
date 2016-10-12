package com.github.kc_7.activityplus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public abstract class Launcher extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public Launcher(int width, int height, Color color) {
		setFocusable(true);
		setPreferredSize(new Dimension(width, height));
		setBackground(color);
		addKeyListener(new KeyHandler());
	}
	
	protected abstract void drawLauncher(Graphics g);
	protected abstract void handleKey(Set<Integer> pressedKeys);
	
	protected final void launch() {
		final ActivityPlus plus = (ActivityPlus)SwingUtilities.getWindowAncestor(this);
		plus.launchActivity();
	}
	
	@Override
	protected final void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawLauncher(g);
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
