package ui;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.util.Duration;
import threads.CloserThread;

/**
 * The Class SplashController.
 */
public class SplashController {

	/** The pane. */
	@FXML // fx:id="pane"
	private StackPane pane; // Value injected by FXMLLoader

	/** The circle 1. */
	@FXML // fx:id="circle1"
	private Circle circle1; // Value injected by FXMLLoader

	/** The circle 2. */
	@FXML // fx:id="circle2"
	private Circle circle2; // Value injected by FXMLLoader

	/** The ellipse. */
	@FXML // fx:id="circle3"
	private Ellipse ellipse; // Value injected by FXMLLoader
	
	/** The stage. */
	private Stage stage;
	
	/** The main. */
	private Main main;
	

	/**
	 * Gets the main.
	 *
	 * @return the main
	 */
	public Main getMain() {
		return main;
	}

	/**
	 * Sets the main.
	 *
	 * @param main the new main
	 */
	public void setMain(Main main) {
		this.main = main;
	}


	/**
	 * Gets the stage.
	 *
	 * @return the stage
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * Sets the stage.
	 *
	 * @param stage the new stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Initialize.
	 */
	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		rotate(circle1, true, 360, 10);
		rotate(circle2, true, 180, 18);
		rotate(ellipse, false, 180, 15);
	}
	
	/**
	 * Inits the thread.
	 */
	public void initThread() {
		CloserThread closer = new CloserThread(stage, main);
		closer.start();
	}

	/**
	 * Rotate.
	 *
	 * @param circle the circle
	 * @param reverse the reverse
	 * @param angle the angle
	 * @param duration the duration
	 */
	private void rotate(Circle circle, boolean reverse, int angle, int duration) {
		RotateTransition transition = new RotateTransition(Duration.seconds(duration), circle);
		transition.setAutoReverse(reverse);

		transition.setByAngle(angle);
		transition.setDelay(Duration.seconds(0));
		transition.setRate(3);
		transition.setCycleCount(18);
		transition.play();
	}
	
	/**
	 * Rotate.
	 *
	 * @param circle the circle
	 * @param reverse the reverse
	 * @param angle the angle
	 * @param duration the duration
	 */
	private void rotate(Ellipse circle, boolean reverse, int angle, int duration) {
		RotateTransition transition = new RotateTransition(Duration.seconds(duration), ellipse);
		transition.setAutoReverse(reverse);

		transition.setByAngle(angle);
		transition.setDelay(Duration.seconds(0));
		transition.setRate(3);
		transition.setCycleCount(18);
		transition.play();
	}

}
