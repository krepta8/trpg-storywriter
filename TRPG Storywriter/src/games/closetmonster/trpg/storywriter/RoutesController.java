/**
 *
 */
package games.closetmonster.trpg.storywriter;

import java.net.URL;
import java.util.ResourceBundle;

import games.closetmonster.javafx.view.Dialogs;
import games.closetmonster.trpg.storywriter.i18n.Messages;
import games.closetmonster.trpg.storywriter.model.Direction;
import games.closetmonster.trpg.storywriter.model.Item;
import games.closetmonster.trpg.storywriter.model.Location;
import games.closetmonster.trpg.storywriter.model.Modellable;
import games.closetmonster.trpg.storywriter.model.Route;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

/**
 * @author Jonathan Radliff
 *
 */
public class RoutesController implements Initializable {

	private static boolean showConfirmDeleteDialog() {
		return Dialogs.showConfirmation(Messages.getString("routes.dialog.confirmation.delete.title"), //$NON-NLS-1$
				Messages.getString("routes.dialog.confirmation.delete.header"), //$NON-NLS-1$
				Messages.getString("routes.dialog.confirmation.delete.content")); //$NON-NLS-1$
	}

	@FXML
	private ListView<Route> routesListView;

	@FXML
	private ScrollPane routeEditPane;

	@FXML
	private Label routeIdLabel;

	@FXML
	private ComboBox<Location> fromLocationComboBox;

	@FXML
	private ComboBox<Direction> directionComboBox;

	@FXML
	private CheckBox bidirectionalCheckBox;

	@FXML
	private ComboBox<Location> toLocationComboBox;

	@FXML
	private CheckBox lockedCheckBox;

	@FXML
	private ComboBox<Item> requiredItemComboBox;

	@FXML
	private CheckBox itemConsumedCheckBox;

	@FXML
	private AnchorPane routeDetailsPane;

	private ObjectProperty<Route> currentRoute = new SimpleObjectProperty<>();
	private Modellable model;

	public RoutesController(Modellable model) {
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
		routesListView.setCellFactory(listView -> new RouteCell());
		routesListView.setItems(model.getRoutes());
		routesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		routesListView.getSelectionModel().selectedItemProperty().addListener(this::selectedItemChanged);
		currentRoute.addListener(this::currentRouteChanged);
		routeEditPane.disableProperty().bind(currentRoute.isNull());
		routeDetailsPane.disableProperty().bind(currentRoute.isNull());
		fromLocationComboBox.setCellFactory(listView -> new LocationCell());
		fromLocationComboBox.setConverter(new LocationConverter());
		fromLocationComboBox.setItems(model.getLocations());
		directionComboBox.setItems(FXCollections.observableArrayList(Direction.values()));
		toLocationComboBox.setCellFactory(listView -> new LocationCell());
		toLocationComboBox.setConverter(new LocationConverter());
		toLocationComboBox.setItems(model.getLocations());
		requiredItemComboBox.setCellFactory(listView -> new ItemCell());
		requiredItemComboBox.setConverter(new ItemConverter());
		requiredItemComboBox.setItems(model.getItems());
		requiredItemComboBox.disableProperty().bind(lockedCheckBox.selectedProperty().not());
		itemConsumedCheckBox.disableProperty().bind(requiredItemComboBox.disabledProperty());
	}

	private void selectedItemChanged(ObservableValue<? extends Route> observable, Route oldValue, Route newValue) {
		currentRoute.set(newValue);
	}

	private void currentRouteChanged(ObservableValue<? extends Route> observable, Route oldValue, Route newValue) {
		if (oldValue != null) {
			// TODO Any changes to commit before moving on?
		}
		if (newValue != null) {
			routeIdLabel.textProperty().bind(newValue.idProperty().asString());
			fromLocationComboBox.getSelectionModel().select(newValue.getFromLocation());
			directionComboBox.getSelectionModel().select(newValue.getDirection());
			bidirectionalCheckBox.setSelected(newValue.isBidirectional());
			toLocationComboBox.getSelectionModel().select(newValue.getToLocation());
			lockedCheckBox.setSelected(newValue.isLocked());
			requiredItemComboBox.getSelectionModel().select(newValue.getRequiredItem());
			itemConsumedCheckBox.setSelected(newValue.isItemConsumed());
		} else {
			routeIdLabel.textProperty().unbind();
			routeIdLabel.setText("");
			fromLocationComboBox.getSelectionModel().select(-1);
			directionComboBox.getSelectionModel().select(-1);
			bidirectionalCheckBox.setSelected(true);
			toLocationComboBox.getSelectionModel().select(-1);
			lockedCheckBox.setSelected(false);
			requiredItemComboBox.getSelectionModel().select(-1);
			itemConsumedCheckBox.setSelected(false);
		}
	}

	@FXML
	private void newRoute() {
		Route route = new Route();
		model.getRoutes().add(route);
		routesListView.getSelectionModel().clearSelection();
		routesListView.getSelectionModel().select(route);
		routesListView.scrollTo(route);
	}

	@FXML
	private void deleteRoute() {
		if (showConfirmDeleteDialog()) {
			model.getRoutes().removeAll(routesListView.getSelectionModel().getSelectedItems());
		}
	}

	@FXML
	private void onActionFromLocation(ActionEvent event) {
		currentRoute.get().setFromLocation(fromLocationComboBox.getSelectionModel().getSelectedItem());
	}

	@FXML
	private void onActionDirection(ActionEvent event) {
		currentRoute.get().setDirection(directionComboBox.getSelectionModel().getSelectedItem());
	}

	@FXML
	private void onActionBidirectional(ActionEvent event) {
		// TODO Unimplemented feature.
	}

	@FXML
	private void onActionToLocation(ActionEvent event) {
		currentRoute.get().setToLocation(toLocationComboBox.getSelectionModel().getSelectedItem());
	}

	@FXML
	private void onActionLocked(ActionEvent event) {
		currentRoute.get().setLocked(lockedCheckBox.isSelected());
	}

	@FXML
	private void onActionRequiredItem(ActionEvent event) {
		currentRoute.get().setRequiredItem(requiredItemComboBox.getSelectionModel().getSelectedItem());
	}

	@FXML
	private void onActionItemConsumed(ActionEvent event) {
		currentRoute.get().setItemConsumed(itemConsumedCheckBox.isSelected());
	}

	private class RouteCell extends ListCell<Route> {

		@Override
		protected void updateItem(Route route, boolean empty) {
			super.updateItem(route, empty);
			if (route == null || empty) {
				setText(null);
			} else {
				setText("Route (ID: " + route.getId() + ")");
			}
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

	private class LocationConverter extends StringConverter<Location> {

		@Override
		public String toString(Location location) {
			if (location.getName() == null || location.getName().isEmpty()) {
				return "Location (ID: " + location.getId() + ")";
			} else {
				return location.getName() + " (ID: " + location.getId() + ")";
			}
		}

		@Override
		public Location fromString(String locationString) {
			throw new UnsupportedOperationException();
		}

	}

	private class ItemConverter extends StringConverter<Item> {

		@Override
		public String toString(Item item) {
			if (item.getName() == null || item.getName().isEmpty()) {
				return "Item (ID: " + item.getId() + ")";
			} else {
				return item.getName() + " (ID: " + item.getId() + ")";
			}
		}

		@Override
		public Item fromString(String itemString) {
			throw new UnsupportedOperationException();
		}

	}

}
