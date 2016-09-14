package net.kc7.activityframe;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Frame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private String title;

	public Frame() {
		
		setContentPane(new Activity());
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}

	public static void main(String[] args) {	
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {             
				new Frame();
			}			
		});
	}
	
}
