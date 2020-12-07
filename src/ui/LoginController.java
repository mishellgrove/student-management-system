package ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Person;
import model.Teacher;
import model.VirtualSchool;
import threads.ImageThread;

public class LoginController {
	@FXML
	private ImageView imageView;
	@FXML
	private TextField userField;
	@FXML
	private PasswordField passwordField;
	private MainController mainController;
	private ImageThread images;

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	@FXML
	public void handleLogin(ActionEvent e) {
		if (isInputValid()) {

			String user = userField.getText();
			String password = passwordField.getText();
			Main main = mainController.getMain();
			VirtualSchool school = main.getVirtualSchool();
			Person person = school.searchUser(user, password);
			if (person != null) {
				MainController.showAlert("Welcome user: " + person.getName(), "WELCOME!", AlertType.INFORMATION);
				if (person instanceof Teacher) {
					loadTeacherView((Teacher) person);
					images.isStop(true);
				}
			} else {
				MainController.showAlert("The user or the password is not correct", "WARNING", AlertType.WARNING);
			}
		}
	}

	public void loadTeacherView(Teacher teacher) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("teacherInit.fxml"));
			Parent root = loader.load();
			TeacherController controller = loader.getController();
			controller.setMainController(mainController);
			controller.setLastController(this);
			controller.setTeacher(teacher);
			mainController.getPane().setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadDirectorView(Teacher teacher) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("teacherInit.fxml"));
			Parent root = loader.load();
			TeacherController controller = loader.getController();
			controller.setMainController(mainController);
			controller.setTeacher(teacher);
			mainController.getPane().setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isInputValid() {
		String errorMessage = "";
		if (userField.getText().isEmpty()) {
			errorMessage += "Please enter the user\n";
		}
		if (passwordField.getText().isEmpty()) {
			errorMessage += "Please enter the password\n";
		}
		if (errorMessage.isEmpty()) {
			return true;
		} else {
			MainController.showAlert(errorMessage, "WARNING", AlertType.WARNING);
		}
		return false;
	}

	@FXML
	public void initialize() {
		images = new ImageThread(imageView);
		images.start();
	}

}
