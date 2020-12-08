package ui;

import java.io.IOException;

import java.util.Optional;

import customExceptions.EntityRepeatedException;
import customExceptions.NullEntityException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Director;
import model.Teacher;

public class DirectorInitController {

	@FXML // fx:id="infoDirectorLabel"
	private Label infoDirectorLabel; // Value injected by FXMLLoader

	@FXML // fx:id="tableView"
	private TableView<Teacher> tableView; // Value injected by FXMLLoader

	@FXML // fx:id="codeTC"
	private TableColumn<Teacher, String> codeTC; // Value injected by FXMLLoader

	@FXML // fx:id="nameTC"
	private TableColumn<Teacher, String> nameTC; // Value injected by FXMLLoader

	@FXML // fx:id="lastnameTC"
	private TableColumn<Teacher, String> lastnameTC; // Value injected by FXMLLoader

	@FXML // fx:id="salaryTC"
	private TableColumn<Teacher, Double> salaryTC; // Value injected by FXMLLoader
	private LoginController lastController;
	private Director director;

	public LoginController getLastController() {
		return lastController;
	}

	public void setLastController(LoginController lastController) {
		this.lastController = lastController;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
		initTableView();
		infoDirectorLabel.setText("Director: " + director.getName() + " " + director.getLastName());
	}

	@FXML
	void goBackButton(ActionEvent event) {
		lastController.getMainController().loadLogin();
	}

	@FXML
	void addCourse(ActionEvent event) {
		loadAddCoursesView();
	}

	@FXML
	void addCourseToTeacher(ActionEvent event) {
		Teacher teacherSelected = tableView.getSelectionModel().getSelectedItem();
		if (teacherSelected != null) {
			String courseId = showInputTextDialog("Which course?\n(Specify the id of the course only)", "ADD COURSE");
			if (!courseId.isEmpty()) {

				try {
					director.addCourseToATeacher(courseId, teacherSelected.getCode());
					MainController.showAlert(
							"Added the course: " + courseId + " to the teacher: " + teacherSelected.getName(),
							"INFORMATION", AlertType.INFORMATION);
				} catch (NullEntityException e) {
					MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
				} catch (EntityRepeatedException e) {
					MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
				}

			} else {
				MainController.showAlert("You need to specify the id of the course", "WARNING", AlertType.WARNING);
			}
		} else {
			MainController.showAlert("You need to select a teacher!", "WARNING", AlertType.WARNING);
		}
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

	@FXML
	void addTeacher(ActionEvent event) {
		loadAddTeachersView();
	}

	public void loadAddCoursesView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("addCourses.fxml"));
			Parent root = loader.load();
			AddCourseController controller = loader.getController();
			controller.setDirector(director);
			controller.setLastController(this);
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Add some courses!");
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadAddTeachersView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("addTeacher.fxml"));
			Parent root = loader.load();
			AddTeacherController controller = loader.getController();
			controller.setDirector(director);
			controller.setLastController(this);
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Add the teachers!");
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void sortByName(ActionEvent event) {
		tableView.getItems().clear();
		tableView.getItems().addAll(director.getTeachersByNameInsertion());
		tableView.refresh();
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
	}

	private void initTableView() {
		codeTC.setCellValueFactory(new PropertyValueFactory<Teacher, String>("code"));
		nameTC.setCellValueFactory(new PropertyValueFactory<Teacher, String>("name"));
		lastnameTC.setCellValueFactory(new PropertyValueFactory<Teacher, String>("lastName"));
		salaryTC.setCellValueFactory(new PropertyValueFactory<Teacher, Double>("salary"));
		tableView.getItems().clear();
		tableView.getItems().addAll(director.getTeachers());
		tableView.refresh();
	}
}
