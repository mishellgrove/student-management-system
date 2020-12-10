package ui;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;

import customExceptions.NullEntityException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.VirtualSchool;
import threads.SaverThread;

/**
 * The Class Main.
 */
public class Main extends Application {

	/** The school. */
	private VirtualSchool school = new VirtualSchool("SAI");

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Start.
	 *
	 * @param primaryStage the primary stage
	 */
	@Override
	public void start(Stage primaryStage) {
		loadSplash();
	}

	/**
	 * Load splash.
	 */
	void loadSplash() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("splash.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			SplashController controller = loader.getController();
			controller.setStage(stage);
			controller.setMain(this);
			controller.initThread();

			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load main.
	 *
	 * @param primaryStage the primary stage
	 */
	public void loadMain(Stage primaryStage) {
		loadDefaultData();
		try {
			Thread saver = new SaverThread(this);
			saver.start();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
			Parent root = loader.load();
			MainController controller = loader.getController();
			controller.setMain(this);
			controller.getLoginController().setMainController(controller);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(closer);
			primaryStage.setTitle(school.getName());
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** The closer. */
	private final EventHandler<WindowEvent> closer = new EventHandler<WindowEvent>() {

		@Override
		public void handle(WindowEvent event) {
			saveData();
			System.exit(0);
		}
	};

	/**
	 * Load default data.
	 */
	public void loadDefaultData() {
		try {
			school = (VirtualSchool)school.loadVirtualSchoolData();
			if (school.getDirectors().size() == 0) {
				school.addDirector("1", "Roberto", "Castro", "12345", 5000000);
			}
		} catch (FileNotFoundException e) {
			File file1 = new File(VirtualSchool.filePath);
			try {
				file1.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullEntityException e) {
			e.printStackTrace();
		}
	}

	public void saveData() {
		try {
			school.saveData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the virtual school.
	 *
	 * @return the virtual school
	 */
	public VirtualSchool getVirtualSchool() {
		return school;
	}
}
