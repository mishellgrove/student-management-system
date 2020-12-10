package ui;

import java.io.IOException;
import java.util.Optional;

import customExceptions.BinaryTreeCastException;
import customExceptions.NullEntityException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import model.Teacher;

/**
 * The Class TeacherController.
 */
public class TeacherController {

	/** The info teacher label. */
	@FXML
	private Label infoTeacherLabel;
	
	/** The table view students. */
	@FXML
	private TableView<Student> tableViewStudents;

	/** The code TC. */
	@FXML
	private TableColumn<Student, String> codeTC;
	
	/** The name TC. */
	@FXML
	private TableColumn<Student, String> nameTC;
	
	/** The lastname TC. */
	@FXML
	private TableColumn<Student, String> lastnameTC;

	/** The teacher. */
	private Teacher teacher;
	
	/** The main controller. */
	private MainController mainController;
	
	/** The last controller. */
	private LoginController lastController;

	/**
	 * Gets the last controller.
	 *
	 * @return the last controller
	 */
	public LoginController getLastController() {
		return lastController;
	}

	/**
	 * Sets the last controller.
	 *
	 * @param lastController the new last controller
	 */
	public void setLastController(LoginController lastController) {
		this.lastController = lastController;
	}

	/**
	 * Gets the teacher.
	 *
	 * @return the teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * Sets the teacher.
	 *
	 * @param teacher the new teacher
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
		initTableView();
		infoTeacherLabel.setText("Teacher: " + teacher.getName() + " " + teacher.getLastName());
	}

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
     * Go back button.
     *
     * @param event the event
     */
    @FXML
    void goBackButton(ActionEvent event) {
    	lastController.getMainController().loadLogin();
    }

	/**
	 * Adds the students.
	 *
	 * @param e the e
	 */
	@FXML
	void addStudents(ActionEvent e) {
		loadStudentsView();
	}

	/**
	 * Load students view.
	 */
	public void loadStudentsView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("students.fxml"));
			Parent root = loader.load();
			StudentsController controller = loader.getController();
			controller.setLastController(this);
			controller.setTeacher(teacher);
			mainController.getPane().setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sort by last name.
	 *
	 * @param e the e
	 */
	@FXML
	void sortByLastName(ActionEvent e) {
		try {
			tableViewStudents.getItems().clear();
			tableViewStudents.getItems().addAll(teacher.getStudentsDescByLastName());
		} catch (BinaryTreeCastException e1) {
			initTableView();
			MainController.showAlert(e1.getMessage(), "ERROR", AlertType.ERROR);
		}
	}

	/**
	 * Sort by code.
	 *
	 * @param e the e
	 */
	@FXML
	void sortByCode(ActionEvent e) {
		try {
			tableViewStudents.getItems().clear();
			tableViewStudents.getItems().addAll(teacher.getStudentsAscByCode());
		} catch (BinaryTreeCastException e1) {
			initTableView();
			MainController.showAlert(e1.getMessage(), "ERROR", AlertType.ERROR);
		}
	}

	/**
	 * Search by course.
	 *
	 * @param e the e
	 */
	@FXML
	void searchByCourse(ActionEvent e) {
		String input = showInputTextDialog("Which course? (id)", "Enter the Id");
		if (!input.isEmpty()) {
			try {
				tableViewStudents.getItems().clear();
				tableViewStudents.getItems().addAll(teacher.getStudentsByCourse(input));
			} catch (NullEntityException e1) {
				initTableView();
				MainController.showAlert(e1.getMessage(), "ERROR", AlertType.ERROR);
			}
		} else {
			MainController.showAlert("You need to specify the id of the course", "WARNING", AlertType.WARNING);
		}
	}

	/**
	 * Inits the table view.
	 */
	private void initTableView() {
		codeTC.setCellValueFactory(new PropertyValueFactory<Student, String>("code"));
		nameTC.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
		lastnameTC.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
		tableViewStudents.getItems().clear();
		tableViewStudents.getItems().addAll(teacher.getStudents());
		tableViewStudents.refresh();
	}

	/**
	 * Show input text dialog.
	 *
	 * @param message the message
	 * @param title the title
	 * @return the string
	 */
	private String showInputTextDialog(String message, String title) {
		String out = "";
		TextInputDialog dialog = new TextInputDialog();

		dialog.setTitle(title);
		dialog.setContentText(message);

		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {
			out = result.get();
		}
		return out;
	}

}
