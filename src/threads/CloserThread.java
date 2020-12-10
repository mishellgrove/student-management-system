package threads;

import javafx.application.Platform;

import javafx.stage.Stage;
import ui.Main;

/**
 * The Class CloserThread.
 */
public class CloserThread extends Thread {

	/** The stage. */
	private Stage stage;

	/** The main. */
	private Main main;

	/**
	 * Instantiates a new closer thread.
	 *
	 * @param stage the stage
	 * @param main  the main
	 */
	public CloserThread(Stage stage, Main main) {
		this.stage = stage;
		this.main = main;
	}

	/**
	 * Run.
	 */
	@Override
	public void run() {
		try {
			Thread.sleep(5000);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					stage.hide();
					main.loadMain(stage);
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
