package prealpha;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public abstract class Activity extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	protected static final String PATH_DATA = "data/";
	
	private Timer timer; 
	protected final Set<Integer> keyList = new HashSet<>();
	
	public Activity(int width, int height, int rate, Color color) {
		
		setFocusable(true);
		setPreferredSize(new Dimension(width, height));
		setBackground(color);
		addKeyListener(this);
		
		timer = new Timer(rate, this);
	}
	
	// Indicates the running state of the timer
	protected final boolean isActive() {
		return timer.isRunning();
	}	
	
	// Loads and starts the activity
	protected final void activate() {
		
		load();
		start();
		
		timer.start();
		
	}
	
	// Pauses the activity
	protected final void pause() {
		timer.stop();
		System.out.println(this.getSize());
	}
	
	// Resumes the activity
	protected final void resume() {
		timer.start();
	}
	
	// Stops activity and ends it via ActivityPlus
	protected final void deactivate() {
		
		stop();
		timer.stop();
		keyList.clear();
		
		final ActivityPlus plus = (ActivityPlus) SwingUtilities.getWindowAncestor(this);
		plus.endActivity();
		
	}
	
	protected final boolean writeValue(String fileName, String key, Object value) {
		
		final File file = new File(PATH_DATA + fileName);
		
		file.getParentFile().mkdirs();
		
		// If the value is not found, appends content
		try (final BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
			
			final String keyValuePair = key + ": " + value + ";";
			bw.write(keyValuePair);
			bw.write(System.lineSeparator());
			bw.close();
			
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	protected abstract void load();		// Loads resources upon activation
	protected abstract void start();	// Initializes data upon activation, after loading
	protected abstract void stop();		// Saves or discards data upon deactivation
	protected abstract void process();			// Computes data upon timer firing
	protected abstract void draw(Graphics g);	// Draws graphics upon timer firing
	protected abstract void checkKeyList();		// Checks key list upon key press
	
	// Processes data and repaints component
	@Override
	public final void actionPerformed(ActionEvent e) {
		process();
		repaint();
	}
	
	// Paints component and draws
	@Override
	protected final void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	// Adds key to and analyzes key list
	@Override
	public final synchronized void keyPressed(KeyEvent e) {
		keyList.add(e.getKeyCode());
		checkKeyList();
	}
	
	// Removes key from key list
	@Override
	public final synchronized void keyReleased(KeyEvent e) {
		keyList.remove(e.getKeyCode());
	}
	
	@Override 
	public final synchronized void keyTyped(KeyEvent e) {}
	
}
