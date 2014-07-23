package com.rolandoislas.hudinstaller.net;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JSON {
	
	private JsonParser jsonParser = new JsonParser();
	
	public JsonElement getJson(String url) throws MalformedURLException, IOException {
		String requestString = url;
		URL requestURL = new URL(requestString);
		Scanner scanner = new Scanner(requestURL.openStream());
		String response = scanner.useDelimiter("\\Z").next();
		JsonElement json = jsonParser.parse(response);
		scanner.close();
		return json;
	}
	
}
