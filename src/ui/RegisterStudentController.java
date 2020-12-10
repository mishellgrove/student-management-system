
package ui;

import customExceptions.EntityRepeatedException;
import customExceptions.NullEntityException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Course;
import model.Register;
import model.Teacher;

/**
 * The Class RegisterStudentController.
 */
public class RegisterStudentController {

	/** The code txt. */
	@FXML // fx:id="codeTxt"
	private TextField codeTxt; // Value injected by FXMLLoader

	/** The table view. */
	@FXML // fx:id="tableView"
	private TableView<Course> tableView; // Value injected by FXMLLoader

	/** The code TC. */
	@FXML // fx:id="codeTC"
	private TableColumn<Course, String> codeTC; // Value injected by FXMLLoader

	/** The course TC. */
	@FXML // fx:id="courseTC"
	private TableColumn<Course, String> courseTC; // Value injected by FXMLLoader

	/** The price TC. */
	@FXML // fx:id="pŕiceTC"
	private TableColumn<Course, String> priceTC; // Value injected by FXMLLoader

	/** The register. */
	private Register register;

	/** The last controller. */
	private StudentsController lastController;

	/**
	 * Gets the register.
	 *
	 * @return the register
	 */
	public Register getRegister() {
		return register;
	}

	/**
	 * Sets the register.
	 *
	 * @param register the new register
	 */
	public void setRegister(Register register) {
		this.register = register;
	}

	/**
	 * Gets the last controller.
	 *
	 * @return the last controller
	 */
	public StudentsController getLastController() {
		return lastController;
	}

	/**
	 * Sets the last controller.
	 *
	 * @param lastController the new last controller
	 */
	public void setLastController(StudentsController lastController) {
		this.lastController = lastController;
		initTableView();
	}

	/**
	 * Adds the course.
	 *
	 * @param event the event
	 */
	@FXML
	void addCourse(ActionEvent event) {
		if (!codeTxt.getText().isEmpty()) {
			try {
//				int i = getLastController().getLastController().getMainController().getMain().getVirtualSchool()
//						.searchCourse(codeTxt.getText());
				register.addCourse(codeTxt.getText());
//				Course course = getLastController().getLastController().getMainController().getMain().getVirtualSchool()
//						.getCourses().get(i);
//				course.addRegister(register.getId(), register.getStudent());
				MainController.showAlert("Added the course: " + codeTxt.getText(), "INFORMATION", AlertType.WARNING);
				codeTxt.setText("");
			} catch (EntityRepeatedException e) {
				MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
			} catch (NullEntityException e) {
				MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
			}
		} else {
			MainController.showAlert("Need the code to remove the course", "WARNING", AlertType.WARNING);
		}
	}

	/**
	 * Removes the course.
	 *
	 * @param event the event
	 */
	@FXML
	void removeCourse(ActionEvent event) {
		if (!codeTxt.getText().isEmpty()) {
			try {
				register.removeCourse(codeTxt.getText());
				MainController.showAlert("Removed the course: " + codeTxt.getText(), "WARNING", AlertType.WARNING);
				codeTxt.setText("");
			} catch (NullEntityException e) {
				MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
			}
		} else {
			MainController.showAlert("Need the code to remove the course", "WARNING", AlertType.WARNING);
		}
	}

	/**
	 * Go back button.
	 *
	 * @param event the event
	 */
	@FXML
	void goBackButton(ActionEvent event) {
		lastController.getLastController().loadStudentsView();
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
	private void initTableView() {
		Teacher teacher = lastController.getTeacher();
		codeTC.setCellValueFactory(new PropertyValueFactory<Course, String>("id"));
		courseTC.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
		priceTC.setCellValueFactory(new PropertyValueFactory<Course, String>("description"));
		tableView.getItems().clear();
		tableView.getItems().addAll(lastController.getLastController().getMainController().getMain().getVirtualSchool()
				.getCoursesByTeacher(teacher.getCode()));
	}
}
