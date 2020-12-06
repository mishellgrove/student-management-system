package ui;

import java.io.IOException;

import customExceptions.NullEntityException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.VirtualSchool;

public class Main extends Application {

	private final VirtualSchool school = new VirtualSchool("SAI");

	public static void main(String[] args) {
		launch(args);
	}

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

	public void loadDefaultData() {
		try {
			school.addTeacher("12234", "Carlos", "Samper", "root", 3500000);
			school.addStudents("60001", "Amanda", "Medina", "student");
			school.addDirector("12345", "Roberto", "Gomez", "root1", 5000000);
		} catch (NullEntityException e) {
			e.printStackTrace();
		}
	}

	public VirtualSchool getVirtualSchool() {
		return school;
	}
}
