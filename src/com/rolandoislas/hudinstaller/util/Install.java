package com.rolandoislas.hudinstaller.util;

import java.io.File;
import java.io.IOException;

import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;

import com.rolandoislas.hudinstaller.HudInstaller;
import com.rolandoislas.hudinstaller.data.Constants;
import com.rolandoislas.hudinstaller.gui.Popup;

public class Install {

	private String versionDirectory;
	private static String displayName;
	private static Popup installMessage;

	public Install(String versionDirectory, String displayName) {
		this.versionDirectory = versionDirectory;
		Install.displayName = displayName;
	}

	public void run() {
		displayInstallMessage();
		InstallWorker installWorker = new InstallWorker(versionDirectory);
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
	private static OS OS = new OS();

	public InstallWorker(String versionDirectory) {
		this.versionDirectory = versionDirectory;
	}
	
	private void installHud() throws IOException {
		purgeHud();
		if(Constants.BASE_DIRECTORY) {
			install("base");
		}
		install(versionDirectory);
		Install.displayPostInstallMessage();
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
