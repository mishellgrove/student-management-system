package ui;

import customExceptions.NullEntityException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Director;
import model.Teacher;

/**
 * The Class AddTeacherController.
 */
public class AddTeacherController {

	/** The code txt. */
	@FXML // fx:id="codeTxt"
	private TextField codeTxt; // Value injected by FXMLLoader

	/** The name txt. */
	@FXML // fx:id="nameTxt"
	private TextField nameTxt; // Value injected by FXMLLoader

	/** The last name txt. */
	@FXML // fx:id="lastNameTxt"
	private TextField lastNameTxt; // Value injected by FXMLLoader

	/** The password field. */
	@FXML // fx:id="passwordField"
	private PasswordField passwordField; // Value injected by FXMLLoader

	/** The salary txt. */
	@FXML // fx:id="salaryTxt"
	private TextField salaryTxt; // Value injected by FXMLLoader

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
	private DirectorInitController lastController;
	
	/** The director. */
	private Director director;

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
	 * Adds the.
	 *
	 * @param event the event
	 */
	@FXML
	void add(ActionEvent event) {
		if (isInputValid()) {
			try {
				director.addTeacher(codeTxt.getText(), nameTxt.getText(), lastNameTxt.getText(),
						passwordField.getText(), Double.parseDouble(salaryTxt.getText()));
				MainController.showAlert("Added the teacher: " + nameTxt.getText(), "INFORMATION",
						AlertType.INFORMATION);
				initTableView();
				passwordField.setText("");
				codeTxt.setText("");
				nameTxt.setText("");
				lastNameTxt.setText("");
				salaryTxt.setText("");
			} catch (NullEntityException e) {
				MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
			}
		}
	}

	/**
	 * Removes the.
	 *
	 * @param event the event
	 */
	@FXML
	void remove(ActionEvent event) {
		Teacher teacherSelected = tableView.getSelectionModel().getSelectedItem();
		if (teacherSelected != null) {
			String code = teacherSelected.getCode();
			try {
				director.removeCourse(code);
				MainController.showAlert("The teacher: " + code + " was removed!", "WARNING", AlertType.WARNING);
				initTableView();
			} catch (NullEntityException e) {
				MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
			}
		} else {
			MainController.showAlert("You need to select the teacher to remove", "WARNING", AlertType.WARNING);
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
				errorMessage += "The code need to be a numeric value\n";
			}
		}
		if (nameTxt.getText().isEmpty()) {
			errorMessage += "Need the name\n";
		}
		if (lastNameTxt.getText().isEmpty()) {
			errorMessage += "Need the last name\n";
		}
		if (salaryTxt.getText().isEmpty()) {
			errorMessage += "Need the salary\n";
		} else {
			try {
				Double.parseDouble(salaryTxt.getText());
			} catch (NumberFormatException e) {
				errorMessage += "The salary must be a numeric\n";
			}
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
		codeTC.setCellValueFactory(new PropertyValueFactory<Teacher, String>("code"));
		nameTC.setCellValueFactory(new PropertyValueFactory<Teacher, String>("name"));
		lastnameTC.setCellValueFactory(new PropertyValueFactory<Teacher, String>("lastName"));
		salaryTC.setCellValueFactory(new PropertyValueFactory<Teacher, Double>("salary"));
		tableView.getItems().clear();
		tableView.getItems().addAll(director.getTeachers());
		tableView.refresh();
	}
}
