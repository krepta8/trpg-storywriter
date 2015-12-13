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
public class LocationsController implements Initializable {

	private static boolean showConfirmDeleteDialog() {
		return Dialogs.showConfirmation(Messages.getString("locations.dialog.confirmation.delete.title"), //$NON-NLS-1$
				Messages.getString("locations.dialog.confirmation.delete.header"), //$NON-NLS-1$
				Messages.getString("locations.dialog.confirmation.delete.content")); //$NON-NLS-1$
	}

	@FXML
	private ListView<Location> locationsListView;

	@FXML
	private ScrollPane locationEditPane;

	@FXML
	private Label locationIdLabel;

	@FXML
	private TextField locationNameTextField;

	@FXML
	private TextArea locationDescriptionTextArea;

	@FXML
	private ListView<Item> addItemsListView;

	@FXML
	private ListView<Item> removeItemsListView;

	@FXML
	private AnchorPane locationDetailsPane;

	private ObjectProperty<Location> currentLocation = new SimpleObjectProperty<>();
	private Modellable model;

	public LocationsController(Modellable model) {
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
		locationsListView.setCellFactory(listView -> new LocationCell());
		locationsListView.setItems(model.getLocations());
		locationsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		locationsListView.getSelectionModel().selectedItemProperty().addListener(this::selectedItemChanged);
		currentLocation.addListener(this::currentLocationChanged);
		locationEditPane.disableProperty().bind(currentLocation.isNull());
		locationDetailsPane.disableProperty().bind(currentLocation.isNull());
		locationNameTextField.focusedProperty().addListener(new LocationNameFocusedListener());
		locationDescriptionTextArea.focusedProperty().addListener(new LocationDescriptionFocusedListener());
		addItemsListView.setCellFactory(listView -> new ItemCell());
		removeItemsListView.setCellFactory(listView -> new ItemCell());
		addItemsListView.setItems(model.getItems());
	}

	private void selectedItemChanged(ObservableValue<? extends Location> observable, Location oldValue,
			Location newValue) {
		currentLocation.set(newValue);
	}

	private void currentLocationChanged(ObservableValue<? extends Location> observable, Location oldValue,
			Location newValue) {
		if (oldValue != null) {
			oldValue.setName(locationNameTextField.getText().trim());
			oldValue.setDescription(locationDescriptionTextArea.getText().trim());
		}
		if (newValue != null) {
			locationIdLabel.textProperty().bind(newValue.idProperty().asString());
			locationNameTextField.textProperty().set(newValue.getName());
			locationDescriptionTextArea.textProperty().set(newValue.getDescription());
			addItemsListView.setItems(model.getItems().filtered(e -> !currentLocation.get().getItems().contains(e)));
			removeItemsListView.setItems(currentLocation.get().getItems());
		} else {
			locationIdLabel.textProperty().unbind();
			locationIdLabel.setText("");
			locationNameTextField.textProperty().unbind();
			locationNameTextField.setText("");
			locationDescriptionTextArea.textProperty().unbind();
			locationDescriptionTextArea.setText("");
		}
	}

	@FXML
	private void newLocation() {
		Location location = new Location();
		model.getLocations().add(location);
		locationsListView.getSelectionModel().clearSelection();
		locationsListView.getSelectionModel().select(location);
		locationsListView.scrollTo(location);
	}

	@FXML
	private void deleteLocation() {
		if (showConfirmDeleteDialog()) {
			model.getLocations().removeAll(locationsListView.getSelectionModel().getSelectedItems());
		}
	}

	@FXML
	private void escape(KeyEvent event) {
		if (event.getCode() == KeyCode.ESCAPE) {
			if (event.getTarget().equals(locationNameTextField)) {
				locationNameTextField.setText(currentLocation.get().getName());
				Platform.runLater(locationNameTextField::selectAll);
			} else if (event.getTarget().equals(locationDescriptionTextArea)) {
				locationDescriptionTextArea.setText(currentLocation.get().getDescription());
				Platform.runLater(locationDescriptionTextArea::selectAll);
			}
		}
	}

	@FXML
	private void onActionLocationName(ActionEvent event) {
		Platform.runLater(locationDescriptionTextArea::requestFocus);
	}

	@FXML
	private void addItemToLocation() {
		int selectedIndex = addItemsListView.getSelectionModel().getSelectedIndex();
		if (selectedIndex == -1) {
			addItemsListView.getSelectionModel().selectFirst();
		}
		Item selectedItem = addItemsListView.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			currentLocation.get().getItems().add(selectedItem);
			addItemsListView.setItems(model.getItems().filtered(e -> !currentLocation.get().getItems().contains(e)));
			if (selectedIndex < addItemsListView.getItems().size()) {
				addItemsListView.getSelectionModel().select(selectedIndex);
			} else {
				addItemsListView.getSelectionModel().selectLast();
			}
		}
	}

	@FXML
	private void removeItemFromLocation() {
		int selectedIndex = removeItemsListView.getSelectionModel().getSelectedIndex();
		if (selectedIndex == -1) {
			removeItemsListView.getSelectionModel().selectFirst();
		}
		Item selectedItem = removeItemsListView.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			currentLocation.get().getItems().remove(selectedItem);
			addItemsListView.setItems(model.getItems().filtered(e -> !currentLocation.get().getItems().contains(e)));
		}
	}

	private class LocationCell extends ListCell<Location> {

		@Override
		protected void updateItem(Location location, boolean empty) {
			super.updateItem(location, empty);
			if (location == null || empty) {
				setText(null);
			} else {
				if (location.getName() == null || location.getName().isEmpty()) {
					setText("Location (ID: " + location.getId() + ")");
				} else {
					setText(location.getName() + " (ID: " + location.getId() + ")");
				}
			}
		}

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

	private class LocationNameFocusedListener implements ChangeListener<Boolean> {

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (locationNameTextField.isVisible() && !locationNameTextField.isDisabled()) {
				boolean hadFocus = (oldValue != null && oldValue.booleanValue() == true);
				boolean notFocused = (newValue != null && newValue.booleanValue() == false);
				if (hadFocus && notFocused) {
					currentLocation.get().setName(locationNameTextField.getText().trim());
					locationsListView.refresh();
				}
			}
		}

	}

	private class LocationDescriptionFocusedListener implements ChangeListener<Boolean> {

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (locationDescriptionTextArea.isVisible() && !locationDescriptionTextArea.isDisabled()) {
				boolean hadFocus = (oldValue != null && oldValue.booleanValue() == true);
				boolean notFocused = (newValue != null && newValue.booleanValue() == false);
				if (hadFocus && notFocused) {
					currentLocation.get().setDescription(locationDescriptionTextArea.getText().trim());
				}
			}
		}

	}

}
