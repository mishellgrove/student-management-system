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
import model.Director;
import model.Person;
import model.Student;
import model.Teacher;
import model.VirtualSchool;
import threads.ImageThread;

/**
 * The Class LoginController.
 */
public class LoginController {
	
	/** The image view. */
	@FXML
	private ImageView imageView;
	
	/** The user field. */
	@FXML
	private TextField userField;
	
	/** The password field. */
	@FXML
	private PasswordField passwordField;
	
	/** The main controller. */
	private MainController mainController;
	
	/** The images. */
	private ImageThread images;


	/**
	 * Gets the main controller.
	 *
	 * @return the main controller
	 */
	public MainController getMainController() {
		return mainController;
	}

	/**
	 * Sets the main controller.
	 *
	 * @param mainController the new main controller
	 */
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	/**
	 * Handle login.
	 *
	 * @param e the e
	 */
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
				if (person instanceof Student) {
					loadStudentsView((Student) person);
					images.isStop(true);
				}
				if (person instanceof Director) {
					loadDirectorView((Director) person);
					images.isStop(true);
				}
			} else {
				MainController.showAlert("The user or the password is not correct", "WARNING", AlertType.WARNING);
			}
		}
	}

	/**
	 * Load teacher view.
	 *
	 * @param teacher the teacher
	 */
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

	/**
	 * Load director view.
	 *
	 * @param director the director
	 */
	public void loadDirectorView(Director director) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("directorInit.fxml"));
			Parent root = loader.load();
			DirectorInitController controller = loader.getController();
			controller.setDirector(director);
			controller.setLastController(this);
			mainController.getPane().setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load students view.
	 *
	 * @param student the student
	 */
	public void loadStudentsView(Student student) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("studentsView.fxml"));
			Parent root = loader.load();
			StudentsViewController controller = loader.getController();
			controller.setLoginController(this);
			controller.setStudent(student);
			mainController.getPane().setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if is input valid.
	 *
	 * @return true, if is input valid
	 */
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

	/**
	 * Initialize.
	 */
	@FXML
	public void initialize() {
		images = new ImageThread(imageView);
		images.start();
	}

}
