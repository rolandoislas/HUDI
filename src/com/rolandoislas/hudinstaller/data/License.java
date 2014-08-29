package com.rolandoislas.hudinstaller.data;

import com.rolandoislas.hudinstaller.HudInstaller;

public class License {

	private static String aboutText = HudInstaller.APP_NAME + " " + Constants.VERSION + "\n\n" + 
	HudInstaller.APP_NAME + " is an open source HUD installer for Team Fortress 2 that\nallows customization to suit the HUD's repo.\nhttp://github.com/rolandoislas/tf2-hud-installer/\n\nPowered by:\nGoogle-Gson\nApache Commons IO";

	public static String getAboutText() {
		return aboutText;
	}

}
