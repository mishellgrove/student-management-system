package threads;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The Class ImageThread.
 */
public class ImageThread extends Thread {

	/** The image view. */
	private ImageView imageView;

	/** The stop. */
	private boolean stop = false;

	/** The images. */
	private final Image[] images = { new Image("./ui/images/1.jpg"), new Image("./ui/images/2.jpg"),
			new Image("./ui/images/3.jpg") };

	/**
	 * Instantiates a new image thread.
	 *
	 * @param image the image
	 */
	public ImageThread(ImageView image) {
		imageView = image;
	}

	/**
	 * Run.
	 */
	@Override
	public void run() {
		int i = 0;
		while (!stop) {
			if (i == images.length) {
				i = 0;
			}
			try {
//				pane.setStyle("-fx-background-color: #" + colors[i] + ";");
				imageView.setImage(images[i]);
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
		}
	}

	/**
	 * Checks if is stop.
	 *
	 * @param stop the stop
	 */
	public void isStop(boolean stop) {
		this.stop = stop;
	}
}
