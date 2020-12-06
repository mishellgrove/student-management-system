/**
 * Sample Skeleton for 'registerStuden.fxml' Controller Class
 */

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

public class RegisterStudentController {

	@FXML // fx:id="codeTxt"
	private TextField codeTxt; // Value injected by FXMLLoader

	@FXML // fx:id="tableView"
	private TableView<Course> tableView; // Value injected by FXMLLoader

	@FXML // fx:id="codeTC"
	private TableColumn<Course, String> codeTC; // Value injected by FXMLLoader

	@FXML // fx:id="courseTC"
	private TableColumn<Course, String> courseTC; // Value injected by FXMLLoader

	@FXML // fx:id="pŕiceTC"
	private TableColumn<Course, String> priceTC; // Value injected by FXMLLoader
	private Register register;
	private StudentsController lastController;

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public StudentsController getLastController() {
		return lastController;
	}

	public void setLastController(StudentsController lastController) {
		this.lastController = lastController;
		initTableView();
	}

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

	@FXML
	void goBackButton(ActionEvent event) {
		lastController.getLastController().loadStudentsView();
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {

	}

	private void initTableView() {
		codeTC.setCellValueFactory(new PropertyValueFactory<Course, String>("id"));
		courseTC.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
		priceTC.setCellValueFactory(new PropertyValueFactory<Course, String>("description"));
		tableView.getItems().clear();
		tableView.getItems().addAll(
				lastController.getLastController().getMainController().getMain().getVirtualSchool().getCourses());
	}
}
