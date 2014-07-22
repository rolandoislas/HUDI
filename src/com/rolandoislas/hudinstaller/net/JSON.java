package com.rolandoislas.hudinstaller.net;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JSON {
	
	private JsonParser jsonParser = new JsonParser();
	
	public JsonElement getJson(String url) {
		String requestString = url;
		URL requestURL = null;
		try {
			requestURL = new URL(requestString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		Scanner scanner = null;
		try {
			scanner = new Scanner(requestURL.openStream());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		String response = scanner.useDelimiter("\\Z").next();
		JsonElement json = jsonParser.parse(response);
		scanner.close();
		return json;
	}
	
}
