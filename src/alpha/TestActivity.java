package alpha;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class TestActivity extends Activity {
	
	public final static int RATE = 300;
	private final static String ID = "id";
	private int id;
	private int counter;

	public TestActivity(Application application) {
		super(application);
	}
	
	public TestActivity(Application application,  HashMap<String, Object> bundle) {
		super(application, bundle);
	}

	@Override
	protected void start(HashMap<String, Object> bundle) {
		id = bundle.containsKey(ID) ? (int) bundle.get(ID) + 1 : id;
	}

	@Override
	protected void stop() {}

	@Override
	protected void pause() {}

	@Override
	protected void resume() {}

	@Override
	protected void update() {
		counter++;
	}

	@Override
	protected void render(Graphics g) {
		g.drawString(counter + "", 10, 10);
		g.drawString(id + "", 30, 30);
	}
	
	@Override
	protected void handlePressedKeys() {
		
		if (super.isPressedKey(KeyEvent.VK_BACK_SPACE)) {
			super.deactivate();
		}
		
		if (super.isPressedKey(KeyEvent.VK_ENTER)) {
			final HashMap<String, Object> bundle = new HashMap<String, Object>();
			bundle.put(ID, id);
			final Activity newActivity = new TestActivity(super.getApplication(), bundle);
			newActivity.setRate(RATE);
			super.getApplication().pushActivity(newActivity);
		}
		
	}

}
