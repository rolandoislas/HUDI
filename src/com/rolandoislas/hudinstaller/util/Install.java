package com.rolandoislas.hudinstaller.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;

import com.rolandoislas.hudinstaller.HudInstaller;
import com.rolandoislas.hudinstaller.data.Constants;
import com.rolandoislas.hudinstaller.gui.List;
import com.rolandoislas.hudinstaller.gui.Popup;
import com.rolandoislas.hudinstaller.util.state.StateBasedApplication;

public class Install {

	private String versionDirectory;
	private StateBasedApplication sba;
	private static String displayName;
	private static Popup installMessage;

	public Install(String versionDirectory, String displayName, StateBasedApplication sba) {
		this.versionDirectory = versionDirectory;
		Install.displayName = displayName;
		this.sba = sba;
	}

	public void run() {
		displayInstallMessage();
		InstallWorker installWorker = new InstallWorker(versionDirectory, displayName, sba);
		installWorker.execute();
	}

	public static void displayError() {
		installMessage.dispose();
		new Popup("Could not install custom HUD.", "Install Failed", true);
	}

	public static void displayPostInstallMessage() {
		installMessage.dispose();
		// HUD installed
		String message = displayName + " installed.\n";
		// HUD repo url
		message += Constants.DISPLAY_NAME + ": " + Github.ROOT_URL + "/" + Constants.REPO_OWNER + "/" + Constants.REPO_NAME + "/\n";
		// Installer repo url
		message += HudInstaller.APP_NAME + ": http://github.com/rolandoislas/tf2-hud-installer/"; 
		new Popup(message, 450, 150, "Install Complete", true);
	}

	private void displayInstallMessage() {
		installMessage = new Popup("Installing " + displayName + ". Please wait.", "Installing HUD", false);
	}

}

class InstallWorker extends SwingWorker<Void, Void> {
	
	private String versionDirectory;
	private String displayName;
	private StateBasedApplication sba;
	private static OS OS = new OS();

	public InstallWorker(String versionDirectory, String displayName, StateBasedApplication sba) {
		this.versionDirectory = versionDirectory;
		this.displayName = displayName;
		this.sba = sba;
	}
	
	private void installHud() throws IOException {
		purgeHud();
		if(Constants.BASE_DIRECTORY) {
			install("base");
		}
		install(versionDirectory);
		updateInstallData();
		Install.displayPostInstallMessage();
	}

	private void updateInstallData() throws FileNotFoundException, IOException {
		Utils.writeToDataFile(Utils.getCachedCommit(), new String[]{"installed", "commit"});
		Utils.writeToDataFile(displayName, new String[]{"installed", "displayName"});
		sba.initState(new List());
		sba.setState(1);
	}

	private void install(String string) throws IOException {
		FileUtils.copyDirectory(new File(OS.getDir() + "/cache/" + Constants.REPO_NAME + "-" + Utils.getCachedCommit() + "/" + string), OS.getDir("tf"));
	}

	private void purgeHud() {
		Utils.delete(OS.getDir("tf"));
	}

	@Override
	protected Void doInBackground(){
		try {
			installHud();
		} catch (IOException e) {
			Install.displayError();
			e.printStackTrace();
		}
		return null;
	}
	
}
