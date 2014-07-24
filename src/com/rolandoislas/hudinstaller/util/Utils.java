package com.rolandoislas.hudinstaller.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.rolandoislas.hudinstaller.data.Constants;

public class Utils {
	
	private static JsonParser jsonParser = new JsonParser();
	private static OS OS = new OS();

	public static void writeTextFile(String text, String path) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(path);
		out.print(text);
		out.close();
	}

	public static String getCachedCommit() {
		String rawJson = getJsonFromFile(OS.getDir() + "/cache.json");
		JsonElement json = jsonParser.parse(rawJson).getAsJsonObject().get("commit");
		return json.isJsonNull() ? null : json.getAsString();
	}

	private static String getJsonFromFile(String dir) {
		InputStream input = null;
		try {
			input = new FileInputStream(dir);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(input);
		String rawJson = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return rawJson;
	}

	public static void unZip(File zipPath, File destPath) throws IOException {
		if(!destPath.exists()) {
			destPath.mkdir();
		}
		ZipInputStream in = new ZipInputStream(new FileInputStream(zipPath));
		ZipEntry entry = in.getNextEntry();
		while(entry != null) {
			String filePath = destPath + File.separator + entry.getName();
			if(!entry.isDirectory()) {
				extract(in, filePath);
			} else {
				File dir = new File(filePath);
				dir.mkdir();
			}
			in.closeEntry();
			entry = in.getNextEntry();
		}
		in.close();
	}

	private static void extract(ZipInputStream in, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytes = new byte[4096];
		int read = 0;
		while((read = in.read(bytes)) != -1) {
			bos.write(bytes, 0, read);
		}
		bos.close();
	}

	public static void delete(File file) {
		if(file.isDirectory()) {
			if(file.list().length == 0) {
				file.delete();
			} else {
				String files[] = file.list();
				for(String path: files) {
					File deleteFile = new File(file, path);
					delete(deleteFile);
				}
				if(file.list().length == 0) {
					file.delete();
				}
			}
		} else {
			file.delete();
		}
	}
	
	public static JsonArray getVersions() throws FileNotFoundException {
		String versionFile = OS.getDir() + "/cache/" + Constants.REPO_NAME + "-" + getCachedCommit() + "/versions.json";
		if(!new File(versionFile).exists()) {
			throw new FileNotFoundException();
		}
		String rawJson = getJsonFromFile(versionFile);
		JsonArray json = jsonParser.parse(rawJson).getAsJsonArray();
		return json;
	}
	
}
