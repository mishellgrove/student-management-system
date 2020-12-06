package threads;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageThread extends Thread {

	private ImageView imageView;
	private boolean stop = false;
	private final Image[] images = { new Image("./ui/images/1.jpg"), new Image("./ui/images/2.jpg"), new Image("./ui/images/3.jpg") };

	public ImageThread(ImageView image) {
		imageView = image;
	}

	@Override
	public void run() {
		int i = 0;
		while (!stop) {
			if (i == images.length) {
				i = 0;
			}
			try {
//				pane.setStyle("-fx-background-color: #" + colors[i] + ";");
				imageView.setImage( images[i]);
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
		}
	}

	public void isStop(boolean stop) {
		this.stop = stop;
	}
}
