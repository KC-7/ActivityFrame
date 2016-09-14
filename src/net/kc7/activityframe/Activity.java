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

	private static final long serialVersionUID = 2L;
	
	private final int ACTIVITY_WIDTH = 100;
	private final int ACTIVITY_HEIGHT = 100;
	private final int ACTIVITY_FRAMERATE = 1000;
	
	private boolean active = false;
	
	private static final int UP = KeyEvent.VK_UP;
	private static final int DOWN = KeyEvent.VK_DOWN;
	private static final int LEFT = KeyEvent.VK_LEFT;
	private static final int RIGHT = KeyEvent.VK_RIGHT;
	
	public Activity() {
		
		setFocusable(true);
		setPreferredSize(new Dimension(ACTIVITY_WIDTH, ACTIVITY_HEIGHT));
		addKeyListener(new KeyHandler());
		
		loadResources();
		startActivity();
		
	}
	
	protected boolean loadResources() {
		return true;
	}
	
	protected boolean processInfo() {
		return true;
	}
	
	protected boolean drawView(Graphics g) {
		return true;
	}
	
	protected boolean drawVoid(Graphics g) {
		return true;
	}
	
	protected boolean handleKey(int key) {
		return true;
	}
	
	private void startActivity() {
		
		active = true;
		
		Timer timer = new Timer(ACTIVITY_FRAMERATE, this);
		timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (active) {
			processInfo();
		}
		
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (active) {
			drawView(g);
		} else {
			drawVoid(g);
		}
	}
	
	private class KeyHandler extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			
			handleKey(e.getKeyCode());
			
		}
	}
}
