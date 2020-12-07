package ui;

import java.io.IOException;

import customExceptions.EntityRepeatedException;
import customExceptions.NullEntityException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.VirtualSchool;

/**
 * The Class Main.
 */
public class Main extends Application {

	/** The school. */
	private final VirtualSchool school = new VirtualSchool("SAI");

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
		loadDefaultData();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
			Parent root = loader.load();
			MainController controller = loader.getController();
			controller.setMain(this);
			controller.getLoginController().setMainController(controller);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(school.getName());
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load default data.
	 */
	public void loadDefaultData() {
		try {
			school.addTeacher("12234", "Carlos", "Samper", "root", 3500000);
			school.addStudents("60001", "Amanda", "Medina", "student");
			school.addDirector("12345", "Roberto", "Gomez", "root1", 5000000);
			school.addCourse("1234", "Biology", "Biology for 6th grade");
			school.addCourse("1244", "History", "History for 6th grade");
			school.addCourse("1254", "History", "History for 6th grade");

			school.getCourses().get(0).setTeacher(school.getTeachers().get(0));
			school.getCourses().get(1).setTeacher(school.getTeachers().get(0));
			school.getCourses().get(2).setTeacher(school.getTeachers().get(0));
			school.getTeachers().get(0).addCourse("1234");
			school.getTeachers().get(0).addCourse("1244");
			school.getTeachers().get(0).addCourse("1254");

		} catch (NullEntityException e) {
			e.printStackTrace();
		} catch (EntityRepeatedException e) {
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
