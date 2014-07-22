package com.rolandoislas.hudinstaller.gui;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.rolandoislas.hudinstaller.util.state.ApplicationState;
import com.rolandoislas.hudinstaller.util.state.StateBasedApplication;
import com.rolandoislas.hudinstaller.worker.UpdateWorker;

import javax.swing.SpringLayout;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.JProgressBar;

public class Update extends JPanel implements ApplicationState {

	private static final long serialVersionUID = 1L;
	private static final int ID = 0;
	private JTextArea txtrStatus;
	private JProgressBar progressBar;
	
	public Update() {
		createComponents();
	}

	private void createComponents() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JLabel lblUpdatingInstaller = new JLabel("Updating Installer");
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblUpdatingInstaller, 0, SpringLayout.HORIZONTAL_CENTER, this);
		lblUpdatingInstaller.setFont(new Font("Tahoma", Font.PLAIN, 24));
		add(lblUpdatingInstaller);
		
		txtrStatus = new JTextArea();
		txtrStatus.setEditable(false);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, txtrStatus, 0, SpringLayout.HORIZONTAL_CENTER, this);
		add(txtrStatus);
		
		progressBar = new JProgressBar();
		springLayout.putConstraint(SpringLayout.SOUTH, txtrStatus, -67, SpringLayout.NORTH, progressBar);
		springLayout.putConstraint(SpringLayout.SOUTH, progressBar, -61, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, progressBar, 0, SpringLayout.HORIZONTAL_CENTER, this);
		add(progressBar);
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public Container initialize(StateBasedApplication sba, JFrame frame) {
		UpdateWorker updateWorker = new UpdateWorker(sba, txtrStatus, progressBar);
		updateWorker.execute();
		return this;
	}
}
