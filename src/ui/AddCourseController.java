
package ui;

import java.util.Optional;

import customExceptions.EntityRepeatedException;
import customExceptions.NullEntityException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Course;
import model.Director;
import model.Teacher;

/**
 * The Class AddCourseController.
 */
public class AddCourseController {

	/** The code txt. */
	@FXML // fx:id="codeTxt"
	private TextField codeTxt; // Value injected by FXMLLoader

	/** The name txt. */
	@FXML // fx:id="nameTxt"
	private TextField nameTxt; // Value injected by FXMLLoader

	/** The description txt. */
	@FXML // fx:id="descriptionTxt"
	private TextField descriptionTxt; // Value injected by FXMLLoader

	/** The table view. */
	@FXML // fx:id="tableView"
	private TableView<Course> tableView; // Value injected by FXMLLoader

	/** The code TC. */
	@FXML // fx:id="codeTC"
	private TableColumn<Course, String> codeTC; // Value injected by FXMLLoader

	/** The name TC. */
	@FXML // fx:id="nameTC"
	private TableColumn<Course, String> nameTC; // Value injected by FXMLLoader

	/** The description TC. */
	@FXML // fx:id="descriptionTC"
	private TableColumn<Course, String> descriptionTC; // Value injected by FXMLLoader

	/** The teacher TC. */
	@FXML // fx:id="teacherTC"
	private TableColumn<Course, Teacher> teacherTC; // Value injected by FXMLLoader
	
	/** The director. */
	private Director director;
	
	/** The last controller. */
	private DirectorInitController lastController;

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
	}

	/**
	 * Gets the last controller.
	 *
	 * @return the last controller
	 */
	public DirectorInitController getLastController() {
		return lastController;
	}

	/**
	 * Sets the last controller.
	 *
	 * @param lastController the new last controller
	 */
	public void setLastController(DirectorInitController lastController) {
		this.lastController = lastController;
	}

	/**
	 * Adds the.
	 *
	 * @param event the event
	 */
	@FXML
	void add(ActionEvent event) {
		if (isInputValid()) {
			try {
				director.addCourse(codeTxt.getText(), nameTxt.getText(), descriptionTxt.getText());
				initTableView();
				codeTxt.setText("");
				nameTxt.setText("");
				descriptionTxt.setText("");
				MainController.showAlert("Added the course: " + nameTxt.getText(), "INFORMATION",
						AlertType.CONFIRMATION);
				lastController.initTableView();
			} catch (NullEntityException e) {
				MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
			}
		}
	}

	/**
	 * Change teacher.
	 *
	 * @param event the event
	 */
	@FXML
	void changeTeacher(ActionEvent event) {
		Course course = tableView.getSelectionModel().getSelectedItem();
		if (course != null) {
			String codeTeacher = showInputTextDialog("Add the teacher id", "INFORMATION");
			if (!codeTeacher.isEmpty()) {
				try {
					director.addCourseToATeacher(course.getId(), codeTeacher);
					initTableView();
					MainController.showAlert(
							"The teacher: " + codeTeacher + " has been assigned to the course: " + course.getId(),
							"INFORMATION", AlertType.INFORMATION);
				} catch (NullEntityException e) {
					MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
				} catch (EntityRepeatedException e) {
					MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
				}
			} else {
				MainController.showAlert("Need the id of the teacher to assign the course: " + course.getId(),
						"WARNING", AlertType.WARNING);
			}
		} else {
			MainController.showAlert("You need to select a course to add or change the teacher", "WARNING",
					AlertType.WARNING);
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
	 * Removes the.
	 *
	 * @param event the event
	 */
	@FXML
	void remove(ActionEvent event) {
		Course courseSelected = tableView.getSelectionModel().getSelectedItem();
		if (courseSelected != null) {
			if (courseSelected.getRegisters().isEmpty()) {
				try {
					director.removeCourse(courseSelected.getId());
					MainController.showAlert("The course: " + courseSelected.getId() + " has been removed", "WARNING",
							AlertType.WARNING);
					initTableView();
				} catch (NullEntityException e) {
					MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
				}
			} else {
				MainController.showAlert("You can't remove the course because there are registers there!", "ERROR",
						AlertType.ERROR);
			}
		} else {
			MainController.showAlert("You need to select a course", "WARNING", AlertType.WARNING);
		}
	}

	/**
	 * Initialize.
	 */
	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
	}

	/**
	 * Checks if is input valid.
	 *
	 * @return true, if is input valid
	 */
	public boolean isInputValid() {
		String errorMessage = "";
		boolean isValid = false;
		if (codeTxt.getText().isEmpty()) {
			errorMessage += "Need the code\n";
		} else {
			try {
				Integer.parseInt(codeTxt.getText());
			} catch (NumberFormatException e) {
				errorMessage += "The code must be a numeric value \n";
			}
		}
		if (nameTxt.getText().isEmpty()) {
			errorMessage += "Need the name\n";
		}
		if (descriptionTxt.getText().isEmpty()) {
			errorMessage += "Need the description\n";
		}
		if (errorMessage.isEmpty()) {
			isValid = true;
		} else {
			MainController.showAlert(errorMessage, "WARNING", AlertType.WARNING);
		}
		return isValid;
	}

	/**
	 * Inits the table view.
	 */
	private void initTableView() {
		codeTC.setCellValueFactory(new PropertyValueFactory<Course, String>("id"));
		nameTC.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
		descriptionTC.setCellValueFactory(new PropertyValueFactory<Course, String>("description"));
		teacherTC.setCellValueFactory(new PropertyValueFactory<Course, Teacher>("teacher"));
		tableView.getItems().clear();
		tableView.getItems().addAll(director.getCoursesByNameSelection());
		tableView.refresh();
	}
}
