/**
 *
 */
package games.closetmonster.trpg.storywriter;

import java.net.URL;
import java.util.ResourceBundle;

import games.closetmonster.javafx.view.Dialogs;
import games.closetmonster.trpg.storywriter.i18n.Messages;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * @author Jonathan Radliff
 *
 */
public class ItemsController implements Initializable {

	private static boolean showConfirmDeleteDialog() {
		return Dialogs.showConfirmation(Messages.getString("items.dialog.confirmation.delete.title"), //$NON-NLS-1$
				Messages.getString("items.dialog.confirmation.delete.header"), //$NON-NLS-1$
				Messages.getString("items.dialog.confirmation.delete.content")); //$NON-NLS-1$
	}

	@FXML
	private ListView<Item> itemsListView;

	@FXML
	private ScrollPane itemEditPane;

	@FXML
	private Label itemIdLabel;

	@FXML
	private TextField itemNameTextField;

	@FXML
	private TextArea itemDescriptionTextArea;

	@FXML
	private AnchorPane itemDetailsPane;

	private ObjectProperty<Item> currentItem = new SimpleObjectProperty<>();
	private Modellable model;

	public ItemsController(Modellable model) {
		this.model = model;
	}

	public Modellable getModel() {
		return model;
	}

	public void setModel(Modellable model) {
		this.model = model;
	}

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		itemsListView.setCellFactory(listView -> new ItemCell());
		itemsListView.setItems(model.getItems());
		itemsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		itemsListView.getSelectionModel().selectedItemProperty().addListener(this::selectedItemChanged);
		currentItem.addListener(this::currentItemChanged);
		itemEditPane.disableProperty().bind(currentItem.isNull());
		itemDetailsPane.disableProperty().bind(currentItem.isNull());
		itemNameTextField.focusedProperty().addListener(new ItemNameFocusedListener());
		itemDescriptionTextArea.focusedProperty().addListener(new ItemDescriptionFocusedListener());
	}

	private void selectedItemChanged(ObservableValue<? extends Item> observable, Item oldValue, Item newValue) {
		currentItem.set(newValue);
	}

	private void currentItemChanged(ObservableValue<? extends Item> observable, Item oldValue, Item newValue) {
		if (oldValue != null) {
			oldValue.setName(itemNameTextField.getText().trim());
			oldValue.setDescription(itemDescriptionTextArea.getText().trim());
		}
		if (newValue != null) {
			itemIdLabel.textProperty().bind(newValue.idProperty().asString());
			itemNameTextField.textProperty().set(newValue.getName());
			itemDescriptionTextArea.textProperty().set(newValue.getDescription());
		} else {
			itemIdLabel.textProperty().unbind();
			itemIdLabel.setText("");
			itemNameTextField.textProperty().unbind();
			itemNameTextField.setText("");
			itemDescriptionTextArea.textProperty().unbind();
			itemDescriptionTextArea.setText("");
		}
	}

	@FXML
	private void newItem() {
		Item item = new Item();
		model.getItems().add(item);
		itemsListView.getSelectionModel().clearSelection();
		itemsListView.getSelectionModel().select(item);
		itemsListView.scrollTo(item);
	}

	@FXML
	private void deleteItem() {
		if (showConfirmDeleteDialog()) {
			model.getItems().removeAll(itemsListView.getSelectionModel().getSelectedItems());
		}
	}

	@FXML
	private void escape(KeyEvent event) {
		if (event.getCode() == KeyCode.ESCAPE) {
			if (event.getTarget().equals(itemNameTextField)) {
				itemNameTextField.setText(currentItem.get().getName());
				Platform.runLater(itemNameTextField::selectAll);
			} else if (event.getTarget().equals(itemDescriptionTextArea)) {
				itemDescriptionTextArea.setText(currentItem.get().getDescription());
				Platform.runLater(itemDescriptionTextArea::selectAll);
			}
		}
	}

	@FXML
	private void onActionItemName(ActionEvent event) {
		Platform.runLater(itemDescriptionTextArea::requestFocus);
	}

	private class ItemCell extends ListCell<Item> {

		@Override
		protected void updateItem(Item item, boolean empty) {
			super.updateItem(item, empty);
			if (item == null || empty) {
				setText(null);
			} else {
				if (item.getName() == null || item.getName().isEmpty()) {
					setText("Item (ID: " + item.getId() + ")");
				} else {
					setText(item.getName() + " (ID: " + item.getId() + ")");
				}
			}
		}

	}

	private class ItemNameFocusedListener implements ChangeListener<Boolean> {

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (itemNameTextField.isVisible() && !itemNameTextField.isDisabled()) {
				boolean hadFocus = (oldValue != null && oldValue.booleanValue() == true);
				boolean notFocused = (newValue != null && newValue.booleanValue() == false);
				if (hadFocus && notFocused) {
					currentItem.get().setName(itemNameTextField.getText().trim());
					itemsListView.refresh();
				}
			}
		}

	}

	private class ItemDescriptionFocusedListener implements ChangeListener<Boolean> {

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (itemDescriptionTextArea.isVisible() && !itemDescriptionTextArea.isDisabled()) {
				boolean hadFocus = (oldValue != null && oldValue.booleanValue() == true);
				boolean notFocused = (newValue != null && newValue.booleanValue() == false);
				if (hadFocus && notFocused) {
					currentItem.get().setDescription(itemDescriptionTextArea.getText().trim());
				}
			}
		}

	}

}
