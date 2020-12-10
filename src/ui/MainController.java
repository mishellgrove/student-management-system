package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

/**
 * The Class MainController.
 */
public class MainController {

	/** The pane. */
	@FXML
	private BorderPane pane;
	
	/** The login controller. */
	@FXML
	private LoginController loginController;

	/**
	 * Sets the pane.
	 *
	 * @param pane the new pane
	 */
	public void setPane(BorderPane pane) {
		this.pane = pane;
	}

	/**
	 * Gets the login controller.
	 *
	 * @return the login controller
	 */
	public LoginController getLoginController() {
		return loginController;
	}

	/**
	 * Sets the login controller.
	 *
	 * @param loginController the new login controller
	 */
	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	/** The main. */
	private Main main;

	/**
	 * Sets the main.
	 *
	 * @param main the new main
	 */
	void setMain(Main main) {
		this.main = main;
	}

	/**
	 * Gets the main.
	 *
	 * @return the main
	 */
	public Main getMain() {
		return main;
	}

	/**
	 * Gets the pane.
	 *
	 * @return the pane
	 */
	public BorderPane getPane() {
		return pane;
	}

	/**
	 * Load login.
	 */
	public void loadLogin() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
			Parent root = loader.load();
			LoginController controller = loader.getController();
			controller.setMainController(this);
			pane.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Show alert.
	 *
	 * @param message the message
	 * @param title the title
	 * @param alertType the alert type
	 */
	public static void showAlert(String message, String title, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setContentText(message);
		alert.setTitle(title);
		alert.setHeaderText("");
		alert.showAndWait();
	}
}
