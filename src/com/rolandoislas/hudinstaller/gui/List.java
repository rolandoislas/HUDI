package com.rolandoislas.hudinstaller.gui;

import java.awt.Container;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.rolandoislas.hudinstaller.HudInstaller;
import com.rolandoislas.hudinstaller.data.Constants;
import com.rolandoislas.hudinstaller.data.License;
import com.rolandoislas.hudinstaller.util.state.ApplicationState;
import com.rolandoislas.hudinstaller.util.state.StateBasedApplication;
import com.rolandoislas.hudinstaller.worker.ListWorker;

import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Cursor;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		
		JButton button = new JButton(new ImageIcon(List.class.getResource("/images/Help Button.png")));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Popup(License.getAboutText(), 450, 200, "About " + HudInstaller.APP_NAME, true);
			}
		});
		button.setContentAreaFilled(false);
		springLayout.putConstraint(SpringLayout.NORTH, button, 3, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, button, -19, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, button, 19, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, button, -3, SpringLayout.EAST, this);
		button.setBackground(SystemColor.menu);
		button.setBorderPainted(false);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setBorder(null);
		button.setToolTipText("About");
		add(button);
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
