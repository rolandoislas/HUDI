package com.rolandoislas.hudinstaller.util;

import java.io.File;

import com.rolandoislas.hudinstaller.data.InstallLocation;

public class OS {
	
	private static String OSS;
	
	public OS() {
		OSS = System.getProperty("os.name").toLowerCase();
	}
	
	public static String getSys() {
		if(isWindows()) {
			return "win";
		} else if(isMac()) {
			return "mac";
		} else if(isUnix()) {
			return "nix";
		}
		return null;
	}

	private static boolean isUnix() {
		return (OSS.indexOf("nix") >= 0 || OSS.indexOf("nux") >= 0 || OSS.indexOf("aix") > 0 );
	}

	private static boolean isMac() {
		return (OSS.indexOf("mac") >= 0);
	}

	private static boolean isWindows() {
		return (OSS.indexOf("win") >= 0);
	}

	public File getDir(String type) {
		String sys = getSys();
		if(sys.equalsIgnoreCase("win")) {
			return type.equals("tf") ? (isWin64() ? new File(InstallLocation.TF_CUSTOM_WIN) : new File(InstallLocation.TF_CUSTOM_WIN_86)) : new File(InstallLocation.APPDATA_WIN);
		} else if(sys.equalsIgnoreCase("mac")) {
			return type.equals("tf") ? new File(InstallLocation.TF_CUSTOM_MAC) : new File(InstallLocation.APPDATA_MAC);
		} else if(sys.equalsIgnoreCase("nix")) {
			return type.equals("tf") ? new File(InstallLocation.TF_CUSTOM_NIX) : new File(InstallLocation.APPDATA_NIX);
		}
		return null;
	}

	private boolean isWin64() {
		File file = new File("C:/Program Files (x86)");
		return file.exists();
	}

	public File getDir() {
		return getDir("");
	}
	
}
