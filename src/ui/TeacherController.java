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

public class TeacherController {

	@FXML
	private Label infoTeacherLabel;
	@FXML
	private TableView<Student> tableViewStudents;

	@FXML
	private TableColumn<Student, String> codeTC;
	@FXML
	private TableColumn<Student, String> nameTC;
	@FXML
	private TableColumn<Student, String> lastnameTC;

	private Teacher teacher;
	private MainController mainController;
	private LoginController lastController;

	public LoginController getLastController() {
		return lastController;
	}

	public void setLastController(LoginController lastController) {
		this.lastController = lastController;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
		initTableView();
		infoTeacherLabel.setText("Teacher: " + teacher.getName() + " " + teacher.getLastName());
	}

	public MainController getMainController() {
		return mainController;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	@FXML
	void addStudents(ActionEvent e) {
		loadStudentsView();
	}

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

	private void initTableView() {
		codeTC.setCellValueFactory(new PropertyValueFactory<Student, String>("code"));
		nameTC.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
		lastnameTC.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
		tableViewStudents.getItems().clear();
		tableViewStudents.getItems().addAll(teacher.getStudents());
		tableViewStudents.refresh();
	}

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
