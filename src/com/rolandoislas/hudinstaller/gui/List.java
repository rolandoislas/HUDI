package com.rolandoislas.hudinstaller.gui;

import java.awt.Container;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.rolandoislas.hudinstaller.data.Constants;
import com.rolandoislas.hudinstaller.util.state.ApplicationState;
import com.rolandoislas.hudinstaller.util.state.StateBasedApplication;
import com.rolandoislas.hudinstaller.worker.ListWorker;

import java.awt.Panel;

public class List extends JPanel implements ApplicationState {

	private static final long serialVersionUID = 1L;
	private static final int ID = 1;
	private Panel panel;
	private JLabel lblInstalled;
	
	public List() {
		createComponents();
	}

	private void createComponents() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JLabel lblTitle = new JLabel("Install " + Constants.DISPLAY_NAME);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblTitle, 0, SpringLayout.HORIZONTAL_CENTER, this);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
		add(lblTitle);
		
		panel = new Panel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 6, SpringLayout.SOUTH, lblTitle);
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 206, SpringLayout.SOUTH, lblTitle);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panel, 0, SpringLayout.HORIZONTAL_CENTER, this);
		add(panel);
		
		lblInstalled = new JLabel("Installed: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblInstalled, 10, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblInstalled, 0, SpringLayout.HORIZONTAL_CENTER, this);
		add(lblInstalled);
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public Container initialize(StateBasedApplication sba, JFrame frame) {
		ListWorker listWorker = new ListWorker(panel, lblInstalled, sba);
		listWorker.execute();
		return this;
	}
}
