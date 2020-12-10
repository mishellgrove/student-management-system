package ui;

import java.io.IOException;
import customExceptions.NullEntityException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Register;
import model.Student;
import model.Teacher;

/**
 * The Class StudentsController.
 */
public class StudentsController {

	/** The code txt. */
	@FXML // fx:id="codeTxt"
	private TextField codeTxt; // Value injected by FXMLLoader

	/** The name txt. */
	@FXML // fx:id="nameTxt"
	private TextField nameTxt; // Value injected by FXMLLoader

	/** The lastname txt. */
	@FXML // fx:id="lastnameTxt"
	private TextField lastnameTxt; // Value injected by FXMLLoader

	/** The password field. */
	@FXML // fx:id="passwordField"
	private PasswordField passwordField; // Value injected by FXMLLoader

	/** The table view students. */
	@FXML // fx:id="tableViewStudents"
	private TableView<Student> tableViewStudents; // Value injected by FXMLLoader

	/** The code TC. */
	@FXML // fx:id="codeTC"
	private TableColumn<Student, String> codeTC; // Value injected by FXMLLoader

	/** The name TC. */
	@FXML // fx:id="nameTC"
	private TableColumn<Student, String> nameTC; // Value injected by FXMLLoader

	/** The lastname TC. */
	@FXML // fx:id="lastnameTC"
	private TableColumn<Student, String> lastnameTC; // Value injected by FXMLLoader

	/** The teacher. */
	private Teacher teacher;
	
	/** The last controller. */
	private TeacherController lastController;



	/**
	 * Adds the.
	 *
	 * @param event the event
	 */
	@FXML
	void add(ActionEvent event) {
		if (isInputValid()) {
			try {
				teacher.addStudents(codeTxt.getText(), nameTxt.getText(), lastnameTxt.getText(),
						passwordField.getText());
				MainController.showAlert("The student: " + nameTxt.getText() + " has been added!", "INFORMATION",
						AlertType.INFORMATION);
				initTableView();
			} catch (NullEntityException e) {
				MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
			}
		}
	}

	/**
	 * Adds the register.
	 *
	 * @param event the event
	 */
	@FXML
	void addRegister(ActionEvent event) {
		if (tableViewStudents.getSelectionModel().getSelectedItem() != null) {
			Student student = tableViewStudents.getSelectionModel().getSelectedItem();
			student.addRegister();
			Register register = student.getRegisters().get(student.getRegisters().size() - 1);
			loadStudentsView(register);
		}
	}

	/**
	 * Load students view.
	 *
	 * @param register the register
	 */
	public void loadStudentsView(Register register) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("registerStudent.fxml"));
			Parent root = loader.load();
			RegisterStudentController controller = loader.getController();
			controller.setLastController(this);
			controller.setRegister(register);
			lastController.getMainController().getPane().setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete.
	 *
	 * @param event the event
	 */
	@FXML
	void delete(ActionEvent event) {
		if (tableViewStudents.getSelectionModel().getSelectedItem() != null) {
			Student studentSelected = tableViewStudents.getSelectionModel().getSelectedItem();
			try {
				teacher.removeStudent(studentSelected.getCode());
				initTableView();
				MainController.showAlert("The student: " + studentSelected.getName() + " has been deleted!", "WARNING",
						AlertType.WARNING);
				codeTxt.setText("");
				nameTxt.setText("");
				lastnameTxt.setText("");
				passwordField.setText("");
			} catch (NullEntityException e) {
				// Not gonna happen
				MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
			}

		} else {
			MainController.showAlert("You need to select a student", "WARNING", AlertType.WARNING);
		}
	}

	/**
	 * Go back button.
	 *
	 * @param event the event
	 */
	@FXML
	void goBackButton(ActionEvent event) {
		lastController.getLastController().loadTeacherView(teacher);
	}

	/**
	 * Initialize.
	 */
	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {

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
	}

	/**
	 * Gets the last controller.
	 *
	 * @return the last controller
	 */
	public TeacherController getLastController() {
		return lastController;
	}

	/**
	 * Sets the last controller.
	 *
	 * @param lastController the new last controller
	 */
	public void setLastController(TeacherController lastController) {
		this.lastController = lastController;
	}

	/**
	 * Inits the table view.
	 */
	private void initTableView() {
		codeTC.setCellValueFactory(new PropertyValueFactory<Student, String>("code"));
		nameTC.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
		lastnameTC.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
		tableViewStudents.getItems().clear();
		tableViewStudents.getItems().addAll(teacher.getSchool().getStudents());
		tableViewStudents.refresh();
	}

	/**
	 * Checks if is input valid.
	 *
	 * @return true, if is input valid
	 */
	private boolean isInputValid() {
		boolean isValid = false;
		String errorMessage = "";
		if (codeTxt.getText().isEmpty()) {
			errorMessage += "Need the code!\n";
		} else {
			try {
				Integer.parseInt(codeTxt.getText());
			} catch (NumberFormatException e) {
				errorMessage += "The code must be a numeric value\n";
			}
		}
		if (nameTxt.getText().isEmpty()) {
			errorMessage += "Need the name\n";
		}
		if (lastnameTxt.getText().isEmpty()) {
			errorMessage += "Need the last name\n";
		}
		if (passwordField.getText().isEmpty()) {
			errorMessage += "Need the password field\n";
		}
		if (errorMessage.isEmpty()) {
			isValid = true;
		} else {
			MainController.showAlert(errorMessage, "WARNING", AlertType.WARNING);
		}
		return isValid;
	}

}
