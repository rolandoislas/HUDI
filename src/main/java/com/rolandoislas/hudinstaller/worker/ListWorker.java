package com.rolandoislas.hudinstaller.worker;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.rolandoislas.hudinstaller.util.Install;
import com.rolandoislas.hudinstaller.util.Utils;
import com.rolandoislas.hudinstaller.util.state.StateBasedApplication;

public class ListWorker extends SwingWorker<Void, Void> {

	private Panel panel;
	private JLabel lblInstalled;
	private StateBasedApplication sba;

	public ListWorker(Panel panel, JLabel lblInstalled, StateBasedApplication sba) {
		this.panel = panel;
		this.lblInstalled = lblInstalled;
		this.sba = sba;
	}

	@Override
	protected Void doInBackground() throws Exception {
		addListButtons();
		addInstalledString();
		return null;
	}

	private void addInstalledString() throws FileNotFoundException {
		String[] installed = null;
		try {
			installed = Utils.getInstalledVersion();
		} catch (FileNotFoundException e) {
			lblInstalled.setText(lblInstalled.getText() + " No installed version detected.");
		}
		if(!installed[0].equals("")) {
			String updateMessage;
			if(Utils.isHudDifferent()) {
				updateMessage = "Update checking disabled.";
			}
			else if(installed[0].equals(Utils.getCachedCommit())) {
				updateMessage = "Up to date.";
			} else {
				updateMessage = "Update avaliable.";
			}
			lblInstalled.setText(lblInstalled.getText() + " " + installed[1] + " - commit " + installed[0].substring(0, 7) + "... - " + updateMessage);
			lblInstalled.setToolTipText("commit " + installed[0]);
		} else {
			lblInstalled.setText(lblInstalled.getText() + " No installed version detected.");
		}
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
						Install install = new Install(versionDirectory, displayName, sba);
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
