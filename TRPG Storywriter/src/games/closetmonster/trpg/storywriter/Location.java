/**
 *
 */
package games.closetmonster.trpg.storywriter;

import games.closetmonster.trpg.storywriter.xml.XMLBinder;
import games.closetmonster.trpg.storywriter.xml.locations.LocationType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;

/**
 * @author Jonathan Radliff
 *
 */
public class Location {

	private static int nextId = 0;

	public static void resetNextId() {
		nextId = 0;
	}

	private IntegerProperty id = new IdProperty(nextId++);
	private StringProperty name = new SimpleStringProperty("");
	private StringProperty description = new SimpleStringProperty("");
	private ListProperty<Item> items = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final LocationType locationType;

	public Location() {
		locationType = new LocationType();
		locationType.setId(XMLBinder.LOCATION_ID_PREFIX + getId());
		locationType.setName(getName());
		locationType.setDescription(getDescription());
		bind();
	}

	public Location(LocationType locationType) {
		this.locationType = locationType;
		setId(XMLBinder.parseId(locationType.getId(), XMLBinder.LOCATION_ID_PREFIX));
		setName(locationType.getName());
		setDescription(locationType.getDescription());
		setItems(FXCollections.observableArrayList(XMLBinder.lookupItems(locationType.getItems())));
		// Overwrite item idrefs with ItemType objects that have getId() method.
//		locationType.getItems().replaceAll(idref -> XMLBinder.lookupItemType(idref));
		bind();
	}

	public LocationType getLocationType() {
		return locationType;
	}

	public int getId() {
		return id.get();
	}

	private void setId(int id) {
		this.id.set(id);
	}

	public ReadOnlyIntegerProperty idProperty() {
		return id;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public StringProperty descriptionProperty() {
		return description;
	}

	public ObservableList<Item> getItems() {
		return items.get();
	}

	public void setItems(ObservableList<Item> items) {
		this.items.set(items);
	}

	public ListProperty<Item> itemsProperty() {
		return items;
	}

	private void bind() {
		id.addListener((obs, o, n) -> locationType.setId(XMLBinder.LOCATION_ID_PREFIX + n));
		name.addListener((obs, o, n) -> locationType.setName(n));
		description.addListener((obs, o, n) -> locationType.setDescription(n));
		items.addListener((Change<? extends Item> change) -> {
			while (change.next()) {
				change.getRemoved().stream().map(item -> item.getItemType()).forEach(locationType.getItems()::remove);
				change.getAddedSubList().stream().map(item -> item.getItemType()).forEach(locationType.getItems()::add);
			}
		});
		// TODO Test listener. Delete this when done.
		items.addListener(new LocationItemsChangeListener());
	}

	private class IdProperty extends SimpleIntegerProperty {

		public IdProperty(int id) {
			super(id);
		}

		@Override
		public void set(int value) {
			if (value < 0) {
				throw new IllegalArgumentException("ID must be a positive integer.");
			}
			if (nextId <= value) {
				nextId = value + 1;
			}
			super.set(value);
		}

	}

	private class LocationItemsChangeListener implements ListChangeListener<Item> {

		@Override
		public void onChanged(Change<? extends Item> change) {
			while (change.next()) {
				if (change.wasPermutated()) {
					for (int i = change.getFrom(); i < change.getTo(); ++i) {
						System.out.println("LocationItemsChangeListener: Item was permutated. Index: " + i);
					}
				} else if (change.wasUpdated()) {
					System.out.println("LocationItemsChangeListener: Item was updated.");
				} else {
					for (Item item : change.getRemoved()) {
						System.out.println("LocationItemsChangeListener: Item IDREF was removed from Location. ID: "
								+ item.getItemType().getId());
					}
					for (Item item : change.getAddedSubList()) {
						System.out.println("LocationItemsChangeListener: Item IDREF was added to Location. ID: "
								+ item.getItemType().getId());
					}
				}
			}
		}

	}

}
