package com.github.kc_7.activityplus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

public abstract class Launcher extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final Color COLOR = null;

	public Launcher() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(COLOR);
		addKeyListener(new KeyHandler());
	}
	
	protected abstract void handleKey(Set<Integer> pressedKeys);
	
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
