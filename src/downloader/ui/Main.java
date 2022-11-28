package downloader.ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main extends JFrame {

	Main(String title, String[] args) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		for (String url : args) {
			DLThread DL = new DLThread(url);
			DL.start();
			Download bar = new Download (DL.getDL());
			this.add(bar);
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
