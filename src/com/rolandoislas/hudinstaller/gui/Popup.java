package com.rolandoislas.hudinstaller.gui;

import javax.swing.JDialog;
import javax.swing.JTextArea;

import com.rolandoislas.hudinstaller.HudInstaller;

import java.awt.Component;
import javax.swing.SpringLayout;

public class Popup extends JDialog{
	
	private static final long serialVersionUID = 1L;
	private static final int POPUP_WIDTH = 450;
	private static final int POPUP_HEIGHT = 100;

	/**
	 * @wbp.parser.constructor
	 */

	public Popup(String message, int width, int height, String title, boolean allowClose) {
		this.setAlwaysOnTop(true);
		if(!allowClose) {
			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		}
		this.setTitle(title);
		setBounds(HudInstaller.screenWidth / 2 - width / 2, HudInstaller.screenHeight / 2 - height / 2, width, height);
		setResizable(false);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		JTextArea textArea = new JTextArea(message);
		springLayout.putConstraint(SpringLayout.NORTH, textArea, height / 4, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, textArea, width / 2, SpringLayout.HORIZONTAL_CENTER, this);
		textArea.setEditable(false);
		textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(textArea);
		setVisible(true);
	}

	public Popup(String message, String title, boolean allowClose) {
		this(message, POPUP_WIDTH, POPUP_HEIGHT, title, allowClose);
	}
	
}
