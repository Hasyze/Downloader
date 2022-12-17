package downloader.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import downloader.fc.Downloader;

public class PlayPauseButton  extends JButton {

	public PlayPauseButton(Downloader dler, JPanel pan) {
		this.setText("PAUSE");
		this.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (dler.isPaused()) {
					dler.pause();
            		setText("PAUSE");
            	}
            	else  {
            		dler.resume();
            		setText("RESUME");
            	}
				pan.revalidate();
				pan.repaint();
			}
		});
	}
}
