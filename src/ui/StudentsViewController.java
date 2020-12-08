package ui;

import java.io.IOException;
import java.util.Optional;

import customExceptions.EmptySearchException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Course;
import model.Student;
import model.Teacher;

public class StudentsViewController {

	@FXML // fx:id="imageView"
	private ImageView imageView; // Value injected by FXMLLoader

	@FXML // fx:id="codeLabel"
	private Label codeLabel; // Value injected by FXMLLoader

	@FXML // fx:id="nameLabel"
	private Label nameLabel; // Value injected by FXMLLoader

	@FXML // fx:id="lastNameLabel"
	private Label lastNameLabel; // Value injected by FXMLLoader

	@FXML // fx:id="courseTxt"
	private TextField courseTxt; // Value injected by FXMLLoader

	@FXML // fx:id="tableView"
	private TableView<Course> tableView; // Value injected by FXMLLoader

	@FXML // fx:id="codeTC"
	private TableColumn<Course, String> codeTC; // Value injected by FXMLLoader

	@FXML // fx:id="nameTC"
	private TableColumn<Course, String> nameTC; // Value injected by FXMLLoader

	@FXML // fx:id="descriptionTC"
	private TableColumn<Course, String> descriptionTC; // Value injected by FXMLLoader

	@FXML // fx:id="teacherTC"
	private TableColumn<Course, Teacher> teacherTC; // Value injected by FXMLLoader

	@FXML // fx:id="stateTC"
	private TableColumn<Course, String> stateTC; // Value injected by FXMLLoader

	private LoginController loginController;
	private Student student;

	@FXML
	void changePassword(ActionEvent event) {
		String newPassword = showInputTextDialog("Change your password \n (Don't let anybody sees it!)",
				"Change your password");
		if (!newPassword.isEmpty()) {
			student.setPassword(newPassword);
			MainController.showAlert("Password changed!", "INFORMATION", AlertType.WARNING);
		} else {
			MainController.showAlert("You must add your new password to change it", "WARNING", AlertType.WARNING);
		}
	}

    @FXML
    void goBackButton(ActionEvent event) {
    	loginController.getMainController().loadLogin();
    }

	@FXML
	void payRegister(ActionEvent event) {
		loadPayRegisterView();
	}

	public void loadPayRegisterView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("payRegister.fxml"));
			Parent root = loader.load();
			PayRegisterController controller = loader.getController();
			controller.setLastController(this);
			controller.setStudent(student);
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Paying the registers");
			stage.showAndWait(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void searchCourse(ActionEvent event) {
		if (!courseTxt.getText().isEmpty()) {
			String course = courseTxt.getText();
			try {
				tableView.getItems().clear();
				tableView.getItems().addAll(student.getCourses(course));
				tableView.refresh();
			} catch (EmptySearchException e) {
				MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
				initTableView();
			}
		} else {
			MainController.showAlert("You need to specify the search criteria!", "WARNING", AlertType.WARNING);
		}
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {

	}

	private void initTableView() {
		codeTC.setCellValueFactory(new PropertyValueFactory<Course, String>("id"));
		nameTC.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
		descriptionTC.setCellValueFactory(new PropertyValueFactory<Course, String>("description"));
		teacherTC.setCellValueFactory(new PropertyValueFactory<Course, Teacher>("teacher"));
		stateTC.setCellValueFactory(new PropertyValueFactory<Course, String>("state"));
		tableView.getItems().clear();
		tableView.getItems().addAll(student.getAllCourses());
		tableView.refresh();
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
		codeLabel.setText(student.getCode());
		nameLabel.setText(student.getName());
		lastNameLabel.setText(student.getLastName());
		initTableView();
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
