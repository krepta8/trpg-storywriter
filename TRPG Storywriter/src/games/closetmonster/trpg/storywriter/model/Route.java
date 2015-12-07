/**
 *
 */
package games.closetmonster.trpg.storywriter.model;

import games.closetmonster.trpg.storywriter.xml.XMLBinder;
import games.closetmonster.trpg.storywriter.xml.routes.DirectionType;
import games.closetmonster.trpg.storywriter.xml.routes.LockType;
import games.closetmonster.trpg.storywriter.xml.routes.RequiredItemType;
import games.closetmonster.trpg.storywriter.xml.routes.RouteType;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Jonathan Radliff
 *
 */
public class Route {

	private static int nextId = 0;

	public static void resetNextId() {
		nextId = 0;
	}

	private IntegerProperty id = new IdProperty(nextId++);
	ObjectProperty<Location> fromLocation = new SimpleObjectProperty<>(null);
	ObjectProperty<Location> toLocation = new SimpleObjectProperty<>(null);
	ObjectProperty<Direction> direction = new SimpleObjectProperty<>(null);
	BooleanProperty bidirectional = new SimpleBooleanProperty(true);
	BooleanProperty locked = new SimpleBooleanProperty(false);
	ObjectProperty<Item> requiredItem = new SimpleObjectProperty<>(null);
	BooleanProperty itemConsumed = new SimpleBooleanProperty(false);
	private final RouteType routeType;

	public Route() {
		routeType = new RouteType();
		routeType.setId(XMLBinder.ROUTE_ID_PREFIX + getId());
		routeType.setLock(new LockType());
		routeType.getLock().setLocked(isLocked());
		routeType.getLock().setRequiredItem(new RequiredItemType());
		routeType.getLock().getRequiredItem().setItemConsumed(isItemConsumed());
		bind();
	}

	public Route(RouteType routeType) {
		this.routeType = routeType;
		setId(XMLBinder.parseId(routeType.getId(), XMLBinder.ROUTE_ID_PREFIX));
		setFromLocation(XMLBinder.lookupLocation(routeType.getFromLocationId()));
		// Overwrite location idref with LocationType object that has getId() method.
		routeType.setFromLocationId(XMLBinder.lookupLocationType(routeType.getFromLocationId()));
		setToLocation(XMLBinder.lookupLocation(routeType.getToLocationId()));
		// Overwrite location idref with LocationType object that has getId() method.
		routeType.setToLocationId(XMLBinder.lookupLocationType(routeType.getToLocationId()));
		setDirection(Direction.valueOf(routeType.getDirection().name()));
		// Optional element.
		if (routeType.getLock() != null) {
			setLocked(routeType.getLock().isLocked());
			setRequiredItem(XMLBinder.lookupItem(routeType.getLock().getRequiredItem().getItemId()));
			// Overwrite item idref with ItemType object that has getId() method.
			routeType.getLock().getRequiredItem().setItemId(XMLBinder.lookupItemType(routeType.getLock().getRequiredItem().getItemId()));
			setItemConsumed(routeType.getLock().getRequiredItem().isItemConsumed());
		}
		bind();
	}

	public RouteType getRouteType() {
		return routeType;
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

	public Location getFromLocation() {
		return fromLocation.get();
	}

	public void setFromLocation(Location fromLocation) {
		this.fromLocation.set(fromLocation);
	}

	public ObjectProperty<Location> fromLocationProperty() {
		return fromLocation;
	}

	public Location getToLocation() {
		return toLocation.get();
	}

	public void setToLocation(Location toLocation) {
		this.toLocation.set(toLocation);
	}

	public ObjectProperty<Location> toLocationProperty() {
		return toLocation;
	}

	public Direction getDirection() {
		return direction.get();
	}

	public void setDirection(Direction direction) {
		this.direction.set(direction);
	}

	public ObjectProperty<Direction> directionProperty() {
		return direction;
	}

	public boolean isBidirectional() {
		return bidirectional.get();
	}

	public void setBidirectional(boolean bidirectional) {
		this.bidirectional.set(bidirectional);
	}

	public BooleanProperty bidirectionalProperty() {
		return bidirectional;
	}

	public boolean isLocked() {
		return locked.get();
	}

	public void setLocked(boolean locked) {
		this.locked.set(locked);
	}

	public BooleanProperty lockedProperty() {
		return locked;
	}

	public Item getRequiredItem() {
		return requiredItem.get();
	}

	public void setRequiredItem(Item requiredItem) {
		this.requiredItem.set(requiredItem);
	}

	public ObjectProperty<Item> requiredItemProperty() {
		return requiredItem;
	}

	public boolean isItemConsumed() {
		return itemConsumed.get();
	}

	public void setItemConsumed(boolean itemConsumed) {
		this.itemConsumed.set(itemConsumed);
	}

	public BooleanProperty itemConsumedProperty() {
		return itemConsumed;
	}

	private void bind() {
		id.addListener((obs, o, n) -> routeType.setId(XMLBinder.ROUTE_ID_PREFIX + n));
		fromLocation.addListener((obs, o, n) -> routeType.setFromLocationId(n.getLocationType()));
		toLocation.addListener((obs, o, n) -> routeType.setToLocationId(n.getLocationType()));
		direction.addListener((obs, o, n) -> routeType.setDirection(DirectionType.valueOf(n.name())));
		locked.addListener((obs, o, n) -> routeType.getLock().setLocked(n));
		requiredItem.addListener((obs, o, n) -> routeType.getLock().getRequiredItem().setItemId(n.getItemType()));
		itemConsumed.addListener((obs, o, n) -> routeType.getLock().getRequiredItem().setItemConsumed(n));
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
