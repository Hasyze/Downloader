package downloader.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import downloader.fc.Downloader;

public class DLThread extends Thread {

	Downloader dl;

	public DLThread(String URL) {
		try {
			this.dl = new Downloader(URL);
		} catch (RuntimeException e) {
			System.err.format("skipping %s %s\n", URL, e);
		}

	}

	public void run() {
		try {
			System.out.println(">Downloading " + dl);
			dl.download();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Downloader getDL() {
		return this.dl;
	}
}
