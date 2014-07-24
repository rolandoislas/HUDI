package com.rolandoislas.hudinstaller.worker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.google.gson.JsonObject;
import com.rolandoislas.hudinstaller.net.Download;
import com.rolandoislas.hudinstaller.util.Github;
import com.rolandoislas.hudinstaller.util.OS;
import com.rolandoislas.hudinstaller.util.Utils;
import com.rolandoislas.hudinstaller.util.state.StateBasedApplication;

public class UpdateWorker extends SwingWorker<Void, Void> {

	private JTextArea status;
	private JProgressBar progressBar;
	private StateBasedApplication sba;
	private String latestCommit;
	private boolean updateFailed = false;
	private Github github = new Github();

	public UpdateWorker(StateBasedApplication sba, JTextArea status, JProgressBar progressBar) {
		this.sba = sba;
		this.status = status;
		this.progressBar = progressBar;
	}

	@Override
	protected Void doInBackground() throws Exception {
		checkForUpdate();
		return null;
	}

	private void checkForUpdate() {
		status.setText("Checking for update.");
		progressBar.setValue(5);
		try {
			if(isFirstRun() || isUpdateAvaliable()) {
				status.setText("Update found.");
				progressBar.setValue(10);
				latestCommit = github.getLatestCommit();
				doUpdate();
			} else {
				status.setText("No update found.");
				progressBar.setValue(100);
			}
		} catch (IOException e) {
			e.printStackTrace();
			doUpdateFailed("Failed to access update site.");
		}
		if(!updateFailed) {
			status.setText("Update complete.");
			progressBar.setValue(100);
			sba.setState(1);
		}
	}

	private void doUpdate() {
		try {
			purgeCache();
			downloadArchive();
			extractArchive();
			updateCacheJson();
			deleteTempararyFiles();
		} catch (IOException e) {
		}
	}

	private void deleteTempararyFiles() {
		status.setText("Cleaning Up.");
		Utils.delete(new File(new OS().getDir() + "/temp"));
	}

	private void updateCacheJson() throws IOException {
		status.setText("Updating cache version data.");
		JsonObject json = new JsonObject();
		json.addProperty("commit", latestCommit);
		String text = json.toString();
		String path = new OS().getDir() + "/cache.json";
		try {
			Utils.writeTextFile(text, path);
		} catch (FileNotFoundException e) {
			doUpdateFailed("Could not write cache version data.");
			e.printStackTrace();
			throw new IOException();
		}
		progressBar.setValue(85);
	}

	private void purgeCache() {
			status.setText("Purging cache.");
			Utils.delete(new File(new OS().getDir() + "/cache"));
			progressBar.setValue(15);
	}

	private void extractArchive() throws IOException {
		status.setText("Extracting update.");
		try {
			Utils.unZip(new File(new OS().getDir() + "/temp/cache.zip"), new File(new OS().getDir() + "/cache/"));
		} catch (IOException e) {
			doUpdateFailed("Failed extracting archive.");
			e.printStackTrace();
			throw e;
		}
		progressBar.setValue(80);
	}

	private void doUpdateFailed(String reason) {
		updateFailed = true;
		status.setText("Update failed. " + reason);
		progressBar.setValue(100);
	}

	private void downloadArchive() throws IOException {
		status.setText("Downloading update.");
		try {
			new Download(new URL(new Github().getCommitArchiveUrl(latestCommit)), new File(new OS().getDir() + "/temp/cache.zip")).get();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			doUpdateFailed("Could not download archive.");
			e.printStackTrace();
			throw e;
		}
		progressBar.setValue(50);
	}

	private boolean isUpdateAvaliable() throws IOException {
		String gitCommit = github.getLatestCommit();
		if(!gitCommit.equals(Utils.getCachedCommit())) {
			return true;
		}
		return false;
	}
	
	private boolean isFirstRun() {
		OS OS = new OS();
		File file = new File(OS.getDir() + "/cache.json");
		if(!file.exists()) {
			return true;
		}
		return false;
	}

}
