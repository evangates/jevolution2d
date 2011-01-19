package thoughtmerge.jevolution;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;


public class TestApp extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		TestApp app = new TestApp();
	}

	private MyPanel canvas;
	private Timer timer;
	private long startTime = 0;
	private int numFrames = 0;
	private double fps = 60.0f;
	
	public TestApp() {
		canvas = new MyPanel(800,600);

		this.add(canvas);

		timer = new Timer(20, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFPS();
				canvas.tick(1/fps);
				repaint();
			}
		});
		timer.start();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.pack();
		this.setVisible(true);

		setLocationRelativeTo(null);
		setResizable(false);
	}

	public void updateFPS() {
		++numFrames;
		if (startTime == 0) {
			startTime = System.currentTimeMillis();
		} else {
			long currentTime = System.currentTimeMillis();
			long delta = (currentTime - startTime);
			if (delta > 1000) {
				fps = (numFrames * 1000) / delta;
				if (fps <= 0) {
					fps = 60;
				}
				numFrames = 0;
				startTime = currentTime;
			}
		}
	}
	
	
}
