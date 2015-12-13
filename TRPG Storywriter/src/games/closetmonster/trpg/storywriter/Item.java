/**
 *
 */
package games.closetmonster.trpg.storywriter;

import games.closetmonster.trpg.storywriter.xml.XMLBinder;
import games.closetmonster.trpg.storywriter.xml.items.ItemType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Jonathan Radliff
 *
 */
public class Item {

	private static int nextId = 0;

	public static void resetNextId() {
		nextId = 0;
	}

	private IntegerProperty id = new IdProperty(nextId++);
	private StringProperty name = new SimpleStringProperty("");
	private StringProperty description = new SimpleStringProperty("");
	private final ItemType itemType;

	public Item() {
		itemType = new ItemType();
		itemType.setId(XMLBinder.ITEM_ID_PREFIX + getId());
		itemType.setName(getName());
		itemType.setDescription(getDescription());
		bind();
	}

	public Item(ItemType itemType) {
		this.itemType = itemType;
		setId(XMLBinder.parseId(itemType.getId(), XMLBinder.ITEM_ID_PREFIX));
		setName(itemType.getName());
		setDescription(itemType.getDescription());
		bind();
	}

	public ItemType getItemType() {
		return itemType;
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

	private void bind() {
		id.addListener((obs, o, n) -> itemType.setId(XMLBinder.ITEM_ID_PREFIX + n));
		name.addListener((obs, o, n) -> itemType.setName(n));
		description.addListener((obs, o, n) -> itemType.setDescription(n));
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

}
