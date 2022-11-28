package downloader.ui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main extends JFrame {

	Main(String title, String[] args) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(420, 500);

		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
		this.add(pan);

		for (String url : args) {
			DLThread DL = new DLThread(url);
			DL.start();
			Download bar = new Download(DL.getDL());
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
