package com.rolandoislas.hudinstaller.worker;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.rolandoislas.hudinstaller.util.Install;
import com.rolandoislas.hudinstaller.util.Utils;

public class ListWorker extends SwingWorker<Void, Void> {

	private Panel panel;

	public ListWorker(Panel panel) {
		this.panel = panel;
	}

	@Override
	protected Void doInBackground() throws Exception {
		addListButtons();
		return null;
	}

	private void addListButtons() {
		JsonArray versions = null;
		try {
			versions = Utils.getVersions();
			for(int i = 0; i < versions.size() ; i++) {
				final String displayName = getJsonKeyValue(versions, i, "displayName").getAsString();
				final String versionDirectory = getJsonKeyValue(versions, i, "versionDirectory").getAsString();
				JButton button = new JButton(displayName);
				//button.setIcon(null);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Install install = new Install(versionDirectory, displayName);
						install.run();
					}
				});
				panel.add(button);
			}
		} catch (FileNotFoundException e1) {
			JTextArea textArea = new JTextArea("versions.json not present in repository root.");
			panel.add(textArea);
		}
		panel.revalidate();
	}

	private JsonElement getJsonKeyValue(JsonArray array, int index, String key) {
		return array.get(index).getAsJsonObject().get(key);
	}

}
