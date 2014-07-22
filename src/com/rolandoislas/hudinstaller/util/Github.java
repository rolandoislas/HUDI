package com.rolandoislas.hudinstaller.util;

import com.google.gson.JsonArray;
import com.rolandoislas.hudinstaller.data.Constants;
import com.rolandoislas.hudinstaller.net.JSON;

public class Github {

	public static final String ROOT_URL = "https://github.com";
	private static final String API_ROOT_URL = "https://api.github.com";
	private JSON json = new JSON();

	public String getLatestCommit() {
		JsonArray commits = json.getJson(API_ROOT_URL + "/repos/" + Constants.REPO_OWNER + "/" + Constants.REPO_NAME + "/commits").getAsJsonArray();
		String latest = commits.get(0).getAsJsonObject().get("sha").getAsString();
		return latest;
	}

	public String getCommitArchiveUrl(String sha) {
		String archiveUrl = ROOT_URL + "/" + Constants.REPO_OWNER + "/" + Constants.REPO_NAME + "/archive/" + sha + ".zip";
		return archiveUrl;
	}
	
}
