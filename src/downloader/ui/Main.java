package downloader.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import downloader.fc.Downloader;

public class Main extends JFrame {

	Main(String title, String[] args) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(420, 500);
		
		JPanel pan = new JPanel();
		this.add(pan);
		pan.setLayout(new BorderLayout());
		
		
		JPanel panDL = new JPanel();
		panDL.setLayout(new StackLayout());
		
		JPanel panAdd = new JPanel(new BorderLayout());
		JTextField addUrl = new JTextField("");
		JButton addButton = new JButton("add");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newURL = addUrl.getText();
				Downloader dler = new Downloader(newURL);
				dler.execute();
				Download bar = new Download(dler);
				panDL.add(bar);
			}
		});
		
		panAdd.add(BorderLayout.CENTER, addUrl);
		panAdd.add(BorderLayout.EAST, addButton);
		
		pan.add(BorderLayout.CENTER, panDL);
		pan.add(BorderLayout.SOUTH, panAdd);

		for (String url : args) {
			Downloader dler = new Downloader(url);
			dler.execute();
			Download bar = new Download(dler);
			panDL.add(bar);
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
