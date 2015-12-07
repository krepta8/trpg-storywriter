/**
 *
 */
package games.closetmonster.javafx.view;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Pair;

/**
 * @author Jonathan Radliff
 *
 */
public final class Dialogs {

	public static <T> Optional<T> showChoice(String title, String content, Collection<T> choices) {
		return showChoice(title, null, content, null, choices);
	}

	public static <T> Optional<T> showChoice(String title, String header, String content, Collection<T> choices) {
		return showChoice(title, header, content, null, choices);
	}

	@SafeVarargs
	public static <T> Optional<T> showChoice(String title, String header, String content, T... choices) {
		return showChoice(title, header, content, null, choices);
	}

	public static <T> Optional<T> showChoice(String title, String header, String content, T defaultChoice,
			Collection<T> choices) {
		ChoiceDialog<T> dialog = new ChoiceDialog<>(defaultChoice, choices);
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(content);
		return dialog.showAndWait();
	}

	@SafeVarargs
	public static <T> Optional<T> showChoice(String title, String header, String content, T defaultChoice,
			T... choices) {
		ChoiceDialog<T> dialog = new ChoiceDialog<>(defaultChoice, choices);
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(content);
		return dialog.showAndWait();
	}

	@SafeVarargs
	public static <T> Optional<T> showChoice(String title, String content, T... choices) {
		return showChoice(title, null, content, null, choices);
	}

	public static <T> Optional<T> showChoice(String title, String content, T defaultChoice, Collection<T> choices) {
		return showChoice(title, null, content, defaultChoice, choices);
	}

	@SafeVarargs
	public static <T> Optional<T> showChoice(String title, String content, T defaultChoice, T... choices) {
		return showChoice(title, null, content, defaultChoice, choices);
	}

	public static boolean showConfirmation(String title, String content) {
		return showConfirmation(title, null, content);
	}

	public static Optional<ButtonType> showConfirmation(String title, String content, ButtonType... buttons) {
		return showConfirmation(title, null, content, buttons);
	}

	public static Optional<ButtonType> showConfirmation(String title, String content, Collection<ButtonType> buttons) {
		return showConfirmation(title, null, content, buttons);
	}

	public static boolean showConfirmation(String title, String header, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		}
		return false;
	}

	public static Optional<ButtonType> showConfirmation(String title, String header, String content,
			ButtonType... buttons) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.getButtonTypes().setAll(buttons);
		return alert.showAndWait();
	}

	public static Optional<ButtonType> showConfirmation(String title, String header, String content,
			Collection<ButtonType> buttons) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.getButtonTypes().setAll(buttons);
		return alert.showAndWait();
	}

	public static void showDialog(AlertType alertType, String title, String content) {
		showDialog(alertType, title, null, content);
	}

	public static void showDialog(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static void showDialog(String title, String content) {
		showDialog(AlertType.NONE, title, null, content);
	}

	public static void showDialog(String title, String header, String content) {
		showDialog(AlertType.NONE, title, header, content);
	}

	public static void showError(String title, String content) {
		showDialog(AlertType.ERROR, title, null, content);
	}

	public static void showError(String title, String header, String content) {
		showDialog(AlertType.ERROR, title, header, content);
	}

	public static void showException(Throwable exception) {
		showException(exception, "Exception Dialog", exception.getClass().getSimpleName(),
				exception.getLocalizedMessage());
	}

	public static void showException(Throwable exception, String title, String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);

		// Create expandable Exception.
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		exception.printStackTrace(printWriter);
		String exceptionText = stringWriter.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane exceptionContent = new GridPane();
		exceptionContent.setMaxWidth(Double.MAX_VALUE);
		exceptionContent.add(label, 0, 0);
		exceptionContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(exceptionContent);

		alert.showAndWait();
	}

	public static void showInformation(String title, String content) {
		showDialog(AlertType.INFORMATION, title, null, content);
	}

	public static void showInformation(String title, String header, String content) {
		showDialog(AlertType.INFORMATION, title, header, content);
	}

	public static Optional<Pair<String, String>> showLogin(String title) {
		return showLogin(title, null, null);
	}

	public static Optional<Pair<String, String>> showLogin(String title, String header) {
		return showLogin(title, header, null);
	}

	public static Optional<Pair<String, String>> showLogin(String title, String header,
			ChangeListener<String> listener) {
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle(title);
		dialog.setHeaderText(header);

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);

		// Enable/Disable login button depending on whether a username was
		// entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		if (listener == null) {
			// Do some validation (using the Java 8 lambda syntax).
			listener = (observable, oldValue, newValue) -> {
				loginButton.setDisable(newValue.trim().isEmpty());
			};
		}

		username.textProperty().addListener(listener);

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> username.requestFocus());

		// Convert the result to a username-password-pair when the login button
		// is clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(username.getText(), password.getText());
			}
			return null;
		});

		return dialog.showAndWait();
	}

	public static Optional<String> showTextInput(String title, String content) {
		return showTextInput(null, title, null, content);
	}

	public static Optional<String> showTextInput(String title, String header, String content) {
		return showTextInput(null, title, header, content);
	}

	public static Optional<String> showTextInput(String defaultValue, String title, String header, String content) {
		TextInputDialog dialog = new TextInputDialog(defaultValue);
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(content);
		return dialog.showAndWait();
	}

	public static void showWarning(String title, String content) {
		showDialog(AlertType.WARNING, title, null, content);
	}

	public static void showWarning(String title, String header, String content) {
		showDialog(AlertType.WARNING, title, header, content);
	}

	private Dialogs() {
		// Static utility class that cannot be instantiated.
	}

}
