package com.rolandoislas.hudinstaller.data;

public class Versions {

	private String displayName;
	private String installDirectory;

	public Versions(String displayName, String installDirectory) {
		this.displayName = displayName;
		this.installDirectory = installDirectory;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getInstallDirectory() {
		return installDirectory;
	}

}
