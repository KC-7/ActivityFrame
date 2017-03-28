package alpha;

import java.awt.Dimension;

public class AlphaTester {

	public static void main(String[] args) {
		final Application application = new Application(new Dimension(600, 600));
		final Activity activity = new TestActivity(application);
		activity.setRate(TestActivity.RATE);
		application.pushActivity(activity);
	}

}