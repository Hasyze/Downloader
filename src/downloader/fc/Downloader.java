/* downloader.fc.Downloader
 * (c) blanch@imag.fr 2021–2023                                            */

package downloader.fc;

import java.beans.PropertyChangeSupport;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.SwingWorker;

public class Downloader extends SwingWorker<Object, Object> {
	public static final int CHUNK_SIZE = 1024;

	URL url;
	int content_length;
	BufferedInputStream in;

	String filename;
	File temp;
	FileOutputStream out;
	ReentrantLock verrou;

	private int _progress;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public Downloader(String uri) {
		try {
			url = new URL(uri);

			URLConnection connection = url.openConnection();
			content_length = connection.getContentLength();

			in = new BufferedInputStream(connection.getInputStream());

			String[] path = url.getFile().split("/");
			filename = path[path.length - 1];
			temp = File.createTempFile(filename, ".part");
			out = new FileOutputStream(temp);
			verrou = new ReentrantLock();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		return url.toString();
	}

	public String download() throws InterruptedException {
		byte buffer[] = new byte[CHUNK_SIZE];
		int size = 0;
		int count = 0;

		while (true) {
			verrou.lock();
			try {
				count = in.read(buffer, 0, CHUNK_SIZE);
			} catch (IOException e) {
				continue;
			}

			if (count < 0) {
				break;
			}

			try {
				out.write(buffer, 0, count);
			} catch (IOException e) {
				continue;
			}

			size += count;
			setProgress(100 * size / content_length);
			verrou.unlock();
			Thread.sleep(1000);
		}

		if (size < content_length) {
			temp.delete();
			throw new InterruptedException();
		}

		try {
			Files.copy(temp.toPath(), new File(filename).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			temp.delete();
		} catch (IOException e) {
			throw new InterruptedException();
		}
		return filename;
	}

	@Override
	protected Object doInBackground() throws Exception {

		while (!isCancelled()) {
			if(!isPaused()) {
				return download();
			}else {
				Thread.sleep(200);
			}
		}
		return null;
	}
	
	public void pause() {
		verrou.unlock();
	}
	
	public void resume() {
		verrou.lock();
	}
	
	public boolean isPaused() {
		return verrou.isHeldByCurrentThread();
	}

}
