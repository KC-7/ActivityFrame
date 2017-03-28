package alpha;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class Activity extends JPanel implements KeyListener {
	
	private final Application application;
	private HashMap<String, Object> bundle = new HashMap<String, Object>();
	private final Timer timer = new Timer(1000, e -> tick());
	private final BitSet pressedKeys = new BitSet(256);
	private final BitSet releasedKeys = new BitSet(256);
	
	public Activity(Application application) {
		super.setFocusable(true);
		super.setPreferredSize(new Dimension(application.getDimension()));
		super.addKeyListener(this);	
		this.application = application;
	}
	
	public Activity(Application application, HashMap<String, Object> bundle) {
		this(application);
		this.bundle = bundle;
	}
	
	public Application getApplication() {
		return application;
	}
	
	public boolean isActive() {
		return timer.isRunning();
	}
	
	public boolean isPressedKey(int e) {
		return pressedKeys.get(e);
	}
	
	public void setRate(int rate) {
		timer.setDelay(rate);
	}
	
	final void activate() {
		start(bundle);
		proceed();
	}
	
	protected final void proceed() {
		resume();
		timer.start();
	}
	
	protected final void suspend() {
		timer.stop();
		pressedKeys.clear();
		releasedKeys.clear();
		pause();
	}
	
	protected final void deactivate() {
		suspend();
		stop();
		application.popActivity();
	}
	
	private void tick() {
		handlePressedKeys();
		handleReleasedKeys();
		update();
		repaint();
	}
	
	private void handleReleasedKeys() {
		for (int i = releasedKeys.nextSetBit(0); i >= 0; i = releasedKeys.nextSetBit(i+1)) {
			pressedKeys.clear(i);
		}
		releasedKeys.clear();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		render(g);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		pressedKeys.set(e.getKeyCode()); 
	}

	@Override
	public void keyReleased(KeyEvent e) {
		releasedKeys.set(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	protected abstract void start(HashMap<String, Object> bundle);
	protected abstract void stop();
	protected abstract void pause();
	protected abstract void resume();
	protected abstract void update();
	protected abstract void handlePressedKeys();
	protected abstract void render(Graphics g);
	
}
