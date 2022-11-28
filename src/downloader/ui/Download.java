package downloader.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JProgressBar;

import downloader.fc.Downloader;

public class Download extends JProgressBar {


	public Download(Downloader d) {
		super(0, 100);
		setValue(0);
		setStringPainted(true); 
	    setLayout(null); 
		setVisible(true);
		d.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				setValue(d.getProgress());
				setString(d.toString()+" "+d.getProgress()+" %");
			}
		});
	}

}
