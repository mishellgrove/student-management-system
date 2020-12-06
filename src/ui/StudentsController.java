package ui;

import java.util.Optional;

import customExceptions.EntityRepeatedException;
import customExceptions.NullEntityException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Register;
import model.Student;
import model.Teacher;

public class StudentsController {

	@FXML // fx:id="codeTxt"
	private TextField codeTxt; // Value injected by FXMLLoader

	@FXML // fx:id="nameTxt"
	private TextField nameTxt; // Value injected by FXMLLoader

	@FXML // fx:id="lastnameTxt"
	private TextField lastnameTxt; // Value injected by FXMLLoader

	@FXML // fx:id="passwordField"
	private PasswordField passwordField; // Value injected by FXMLLoader

	@FXML // fx:id="tableViewStudents"
	private TableView<Student> tableViewStudents; // Value injected by FXMLLoader

	@FXML // fx:id="codeTC"
	private TableColumn<Student, String> codeTC; // Value injected by FXMLLoader

	@FXML // fx:id="nameTC"
	private TableColumn<Student, String> nameTC; // Value injected by FXMLLoader

	@FXML // fx:id="lastnameTC"
	private TableColumn<Student, String> lastnameTC; // Value injected by FXMLLoader

	private Teacher teacher;
	private TeacherController lastController;

	@FXML
	void add(ActionEvent event) {
		if (isInputValid()) {
			try {
				teacher.getSchool().addStudents(codeTxt.getText(), nameTxt.getText(), lastnameTxt.getText(),
						passwordField.getText());
				MainController.showAlert("The student: " + nameTxt.getText() + " has been added!", "INFORMATION",
						AlertType.INFORMATION);
				initTableView();
			} catch (NullEntityException e) {
				MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
			}
		}
	}

	@FXML
	void addRegister(ActionEvent event) {
		if (tableViewStudents.getSelectionModel().getSelectedItem() != null) {

			String idCourse = showInputTextDialog("Add the id of the course to register", "INFORMATION");
			if (!idCourse.isEmpty()) {
				Student studentSelected = tableViewStudents.getSelectionModel().getSelectedItem();
				studentSelected.addRegister();
				Register register = studentSelected.getRegisters().get(studentSelected.getRegisters().size() - 1);
				try {
					register.addCourse(idCourse);
					MainController.showAlert("Added the course to the register", "INFORMATION", AlertType.INFORMATION);

				} catch (NullEntityException e) {
					MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
				} catch (EntityRepeatedException e) {
					MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
				}
			} else {
				MainController.showAlert("You need the id of the course to register", "WARNING", AlertType.WARNING);
			}
		} else {
			MainController.showAlert("You need the select a student", "WARNING", AlertType.WARNING);
		}
	}

	private String showInputTextDialog(String message, String title) {
		String out = "";
		TextInputDialog dialog = new TextInputDialog();

		dialog.setTitle(title);
		dialog.setContentText(message);

		Optional<String> result = dialog.showAndWait();
		if (result.get() != null) {
			out = result.get();
		}
		return out;
	}

	@FXML
	void delete(ActionEvent event) {
		if (tableViewStudents.getSelectionModel().getSelectedItem() != null) {
			Student studentSelected = tableViewStudents.getSelectionModel().getSelectedItem();
			try {
				teacher.getSchool().removeStudent(studentSelected.getCode());
				initTableView();
				MainController.showAlert("The student: " + studentSelected.getName() + " has been deleted!", "WARNING",
						AlertType.WARNING);
			} catch (NullEntityException e) {
				// Not gonna happen
				MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
			}

		} else {
			MainController.showAlert("You need to select a student", "WARNING", AlertType.WARNING);
		}
	}

	@FXML
	void goBackButton(ActionEvent event) {
		lastController.getLastController().loadTeacherView(teacher);
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {

	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
		initTableView();
	}

	public TeacherController getLastController() {
		return lastController;
	}

	public void setLastController(TeacherController lastController) {
		this.lastController = lastController;
	}

	private void initTableView() {
		codeTC.setCellValueFactory(new PropertyValueFactory<Student, String>("code"));
		nameTC.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
		lastnameTC.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
		tableViewStudents.getItems().clear();
		tableViewStudents.getItems().addAll(teacher.getStudents());
		tableViewStudents.refresh();
	}

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
