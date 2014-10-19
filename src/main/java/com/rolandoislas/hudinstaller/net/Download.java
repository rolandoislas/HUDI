package com.rolandoislas.hudinstaller.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class Download {
	
	private File filePath;
	private URL url;

	public Download(URL url, File filePath) {
		this.filePath = filePath;
		this.url = url;
	}

	public void get() throws IOException {
		if (filePath != null) {
			// Create directory structure if it does not exist.
			File folder = filePath.getParentFile();
			if (!folder.exists()) {
				folder.mkdirs();
			}
			InputStream in = null;
			OutputStream out = null;
			try {
				in = url.openStream();
				out = new FileOutputStream(filePath);
				// Begin transfer
				transfer(in, out);
			} catch (IOException e) {
				throw e;
			} finally {
				// Close streams
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) { /* ignore */ }
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) { /* ignore */ }
				}
			}
		}
	}

	private void transfer(InputStream in, OutputStream out) throws IOException{
		byte[] buffer = new byte[2048];
		int bytesRead;
		while ((bytesRead = in.read(buffer)) > 0) {
			out.write(buffer, 0, bytesRead);
		}
	}

}

