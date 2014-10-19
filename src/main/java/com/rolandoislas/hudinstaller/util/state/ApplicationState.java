package com.rolandoislas.hudinstaller.util.state;

import java.awt.Container;

import javax.swing.JFrame;

public interface ApplicationState{
	public int getID();
	public Container initialize(StateBasedApplication sba, JFrame frame);
}
