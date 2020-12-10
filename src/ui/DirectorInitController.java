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

/**
 * The Class DirectorInitController.
 */
public class DirectorInitController {

	/** The info director label. */
	@FXML // fx:id="infoDirectorLabel"
	private Label infoDirectorLabel; // Value injected by FXMLLoader

	/** The table view. */
	@FXML // fx:id="tableView"
	private TableView<Teacher> tableView; // Value injected by FXMLLoader

	/** The code TC. */
	@FXML // fx:id="codeTC"
	private TableColumn<Teacher, String> codeTC; // Value injected by FXMLLoader

	/** The name TC. */
	@FXML // fx:id="nameTC"
	private TableColumn<Teacher, String> nameTC; // Value injected by FXMLLoader

	/** The lastname TC. */
	@FXML // fx:id="lastnameTC"
	private TableColumn<Teacher, String> lastnameTC; // Value injected by FXMLLoader

	/** The salary TC. */
	@FXML // fx:id="salaryTC"
	private TableColumn<Teacher, Double> salaryTC; // Value injected by FXMLLoader
	
	/** The last controller. */
	private LoginController lastController;
	
	/** The director. */
	private Director director;

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
	 * Gets the director.
	 *
	 * @return the director
	 */
	public Director getDirector() {
		return director;
	}

	/**
	 * Sets the director.
	 *
	 * @param director the new director
	 */
	public void setDirector(Director director) {
		this.director = director;
		initTableView();
		infoDirectorLabel.setText("Director: " + director.getName() + " " + director.getLastName());
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
	 * Adds the course.
	 *
	 * @param event the event
	 */
	@FXML
	void addCourse(ActionEvent event) {
		loadAddCoursesView();
	}

	/**
	 * Adds the course to teacher.
	 *
	 * @param event the event
	 */
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

	/**
	 * Adds the teacher.
	 *
	 * @param event the event
	 */
	@FXML
	void addTeacher(ActionEvent event) {
		loadAddTeachersView();
	}

	/**
	 * Load add courses view.
	 */
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

	/**
	 * Load add teachers view.
	 */
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

	/**
	 * Sort by name.
	 *
	 * @param event the event
	 */
	@FXML
	void sortByName(ActionEvent event) {
		tableView.getItems().clear();
		tableView.getItems().addAll(director.getTeachersByNameInsertion());
		tableView.refresh();
	}

	/**
	 * Initialize.
	 */
	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
	}

	/**
	 * Inits the table view.
	 */
	public void initTableView() {
		codeTC.setCellValueFactory(new PropertyValueFactory<Teacher, String>("code"));
		nameTC.setCellValueFactory(new PropertyValueFactory<Teacher, String>("name"));
		lastnameTC.setCellValueFactory(new PropertyValueFactory<Teacher, String>("lastName"));
		salaryTC.setCellValueFactory(new PropertyValueFactory<Teacher, Double>("salary"));
		tableView.getItems().clear();
		tableView.getItems().addAll(director.getTeachers());
		tableView.refresh();
	}
}
