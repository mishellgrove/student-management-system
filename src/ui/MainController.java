package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

public class MainController {

	@FXML
	private BorderPane pane;
	@FXML
	private LoginController loginController;

	public void setPane(BorderPane pane) {
		this.pane = pane;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	private Main main;

	void setMain(Main main) {
		this.main = main;
	}

	public Main getMain() {
		return main;
	}

	public BorderPane getPane() {
		return pane;
	}

	public static void showAlert(String message, String title, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setContentText(message);
		alert.setTitle(title);
		alert.setHeaderText("");
		alert.showAndWait();
	}
}
