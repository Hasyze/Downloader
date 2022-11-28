package downloader.ui;

public class Main {

	public static void main(String[] args) {
		for (String url : args) {
			DLThread DL = new DLThread(url);
			DL.start();
			DL.getDL();
		}
	}

}
