package com.github.kc_7.activityplus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public abstract class Activity extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	
	protected Timer timer; 
	private int rate;
	private final Set<Integer> keys = new HashSet<>();
	
	public Activity(int width, int height, int rate, Color color) {
		
		setFocusable(true);
		setPreferredSize(new Dimension(width, height));
		setBackground(color);
		addKeyListener(this);
		
		this.rate = rate;
	}
	
	protected final void activate() {
		
		load();
		start();
		
		timer = new Timer(rate, this);
		timer.start();
		
	}
	
	protected final void pause() {
		
		timer.stop();
		
	}
	
	protected final void resume() {
		
		timer.start();
		
	}

	protected final void deactivate() {
		
		stop();
		timer.stop();
		keys.clear();
		
		final ActivityPlus plus = (ActivityPlus) SwingUtilities.getWindowAncestor(this);
		plus.endActivity();
		
	}
	
	protected final boolean write(String fileName, String key, Object value) {
		
		final File file = new File("data/" + fileName);
		
		try (final BufferedWriter bw = new BufferedWriter(new FileWriter(file, findValue(fileName, key) == null))) {
			
			value = value.toString();
			String content = key + ": " + value + ";";
			
			file.createNewFile();
			bw.write(content);
			bw.close();
			
			return true;
			
		} catch (IOException e) {
			
			return false;
			
		}
		
	}
	
	private String findValue(String fileName, String key) {
		
		String content = read(fileName);
		int i = content.indexOf(key);
		int j = content.indexOf(";", i);
		
		if (i != -1 || j != -1) {

			
			return content.substring(i, j);
			
		} else {
			
			return null;
			
		}
		
	}
	
	protected final String read(String fileName) {
		
		new File("data/").mkdir();
		final File file = new File("data/" + fileName);
		
		try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
			
			final StringBuilder sb = new StringBuilder();
			String line;
			
			while ((line = br.readLine()) != null) {
				
				sb.append(line);
				
				if (line != null) {
					
					sb.append(System.lineSeparator());
					
				}
				
			}
			
			return sb.toString();
			
		} catch (IOException e) {
			
			return null;
			
		}
		
	}
	
	protected abstract void load();
	
	protected abstract void start();
	
	protected abstract void stop();
	
	protected abstract void process();
	
	protected abstract void draw(Graphics g);
	
	protected abstract void keyPress(Set<Integer> keys);
	
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
	
	@Override public synchronized void keyTyped(KeyEvent e) {}
	
	@Override
	public synchronized void keyPressed(KeyEvent e) {
		
		keys.add(e.getKeyCode());
		keyPress(keys);
		
	}
	
	@Override
	public synchronized void keyReleased(KeyEvent e) {
		
		keys.remove(e.getKeyCode());
		
	}
	
}
