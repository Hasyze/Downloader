package downloader.ui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import downloader.fc.Downloader;

public class Main extends JFrame {

	Main(String title, String[] args) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(420, 500);

		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
		this.add(pan);

		for (String url : args) {
			Downloader dler = new Downloader(url);
			dler.execute();
			Download bar = new Download(dler);
			pan.add(bar);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main("Downloader", args).setVisible(true);
			}
		});
	}

}
