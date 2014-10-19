package com.rolandoislas.hudinstaller;

import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import com.rolandoislas.hudinstaller.data.Constants;
import com.rolandoislas.hudinstaller.gui.List;
import com.rolandoislas.hudinstaller.gui.Update;
import com.rolandoislas.hudinstaller.util.state.StateBasedApplication;

public class HudInstaller extends StateBasedApplication {

	public static int screenHeight;
	public static int screenWidth;
	public final static String APP_NAME = "HUDI";
	public static final int WIDTH = 500;
	public static final int HEIGHT = 300;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HudInstaller window = new HudInstaller();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public HudInstaller() {
		super(APP_NAME + " " + Constants.VERSION);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		screenWidth = gd.getDisplayMode().getWidth();
		screenHeight = gd.getDisplayMode().getHeight();
		setPosition(screenWidth / 2 - WIDTH / 2, screenHeight / 2 - HEIGHT / 2);
		setWidth(WIDTH);
		setHeight(HEIGHT);
		initialize();
		addStates();
		setState(0);
	}
	
	private void addStates() {
		addState(new Update());
		addState(new List());
	}

}
