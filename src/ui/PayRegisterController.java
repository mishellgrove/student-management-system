package ui;

import java.util.Date;
import java.util.Optional;

import customExceptions.AmountInputException;
import customExceptions.NotEnoughMoneyException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Register;
import model.Student;

/**
 * The Class PayRegisterController.
 */
public class PayRegisterController {

	/** The amount label. */
	@FXML // fx:id="amountLabel"
	private Label amountLabel; // Value injected by FXMLLoader

	/** The table view. */
	@FXML // fx:id="tableView"
	private TableView<Register> tableView; // Value injected by FXMLLoader

	/** The code TC. */
	@FXML // fx:id="codeTC"
	private TableColumn<Register, String> codeTC; // Value injected by FXMLLoader

	/** The date TC. */
	@FXML // fx:id="dateTC"
	private TableColumn<Register, Date> dateTC; // Value injected by FXMLLoader

	/** The total TC. */
	@FXML // fx:id="totalTC"
	private TableColumn<Register, Double> totalTC; // Value injected by FXMLLoader

	/** The state TC. */
	@FXML // fx:id="stateTC"
	private TableColumn<Register, String> stateTC; // Value injected by FXMLLoader

	/** The student. */
	private Student student;

	/** The last controller. */
	private StudentsViewController lastController;

	/**
	 * Gets the student.
	 *
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * Sets the student.
	 *
	 * @param student the new student
	 */
	public void setStudent(Student student) {
		this.student = student;
		initTableView();

		amountLabel.setText("$ " + student.getAccount().getAmount());
	}

	/**
	 * Gets the last controller.
	 *
	 * @return the last controller
	 */
	public StudentsViewController getLastController() {
		return lastController;
	}

	/**
	 * Sets the last controller.
	 *
	 * @param lastController the new last controller
	 */
	public void setLastController(StudentsViewController lastController) {
		this.lastController = lastController;
	}

	/**
	 * Deposit money.
	 *
	 * @param event the event
	 */
	@FXML
	void depositMoney(ActionEvent event) {
		String amount = showInputTextDialog("Add the amount \n(Only numbers please!)", "Amount");
		if (!amount.isEmpty()) {
			try {
				double amountDouble = Double.parseDouble(amount);
				student.getAccount().depositMoney(amountDouble);
				MainController.showAlert("Added the amount: " + amount + " to your account", "INFORMATION",
						AlertType.WARNING);
				amountLabel.setText("$" + student.getAccount().getAmount());
			} catch (NumberFormatException e) {
				MainController.showAlert("The amount must be only numbers!", "ERROR", AlertType.ERROR);
			} catch (AmountInputException e) {
				MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
			}
		}
	}

	/**
	 * Show input text dialog.
	 *
	 * @param message the message
	 * @param title   the title
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
	 * Pay.
	 *
	 * @param event the event
	 */
	@FXML
	void pay(ActionEvent event) {
		Register registerSelected = tableView.getSelectionModel().getSelectedItem();
		if (registerSelected != null) {
			try {
				if (registerSelected.getState().equals("Active")) {
					MainController.showAlert("The register is already paid", "INFORMATION", AlertType.WARNING);
				} else {
					student.getAccount().withdrawMoney(registerSelected.getTotal());
					MainController.showAlert(
							"The register: " + registerSelected.getId() + " was paid\n Thanks! enjoy the course",
							"INFORMATION", AlertType.INFORMATION);
					registerSelected.setState("Active");
					initTableView();
					amountLabel.setText("$" + student.getAccount().getAmount());
				}
			} catch (NotEnoughMoneyException e) {
				MainController.showAlert(e.getMessage(), "ERROR", AlertType.ERROR);
			}
		} else {
			MainController.showAlert("You need to select a register", "WARNING", AlertType.WARNING);
		}
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
		codeTC.setCellValueFactory(new PropertyValueFactory<Register, String>("id"));
		dateTC.setCellValueFactory(new PropertyValueFactory<Register, Date>("date"));
		totalTC.setCellValueFactory(new PropertyValueFactory<Register, Double>("total"));
		stateTC.setCellValueFactory(new PropertyValueFactory<Register, String>("state"));
		tableView.getItems().clear();
		tableView.getItems().addAll(student.getRegisters());
	}
}
