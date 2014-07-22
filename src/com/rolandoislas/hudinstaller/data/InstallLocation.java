package com.rolandoislas.hudinstaller.data;

public class InstallLocation {

	private static String homeDir = System.getProperty("user.home");
	public static final String APPDATA_WIN = homeDir + "/AppData/Roaming/Hud Installer/";
	public static final String APPDATA_MAC = homeDir + "/Library/Application Support/Hud Installer/";
	public static final String APPDATA_NIX = homeDir + "/.hudinstaller/";
	public static final String  TF_CUSTOM_WIN = "C:/Program Files (x86)/Steam/SteamApps/common/Team Fortress 2/tf/custom/" + Constants.HUD_DIR;
	public static final String  TF_CUSTOM_WIN_86 = "C:/Program Files/Steam/SteamApps/common/Team Fortress 2/tf/custom/" + Constants.HUD_DIR;
	public static final String  TF_CUSTOM_MAC = homeDir + "/Library/Application Support/Steam/SteamApps/common/Team Fortress 2/tf/custom/"  + Constants.HUD_DIR;
	public static final String  TF_CUSTOM_NIX = homeDir + "/.steam/steam/SteamApps/common/Team Fortress 2/tf/custom/"  + Constants.HUD_DIR;
	
}
