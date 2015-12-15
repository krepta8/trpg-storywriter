/**
 *
 */
package games.closetmonster.trpg.storywriter.model;

import games.closetmonster.trpg.storywriter.model.xml.items.ItemsType;
import games.closetmonster.trpg.storywriter.model.xml.locations.LocationsType;
import games.closetmonster.trpg.storywriter.model.xml.routes.RoutesType;
import games.closetmonster.trpg.storywriter.model.xml.world.WorldType;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * @author Jonathan Radliff
 *
 */
public class World implements Modellable {

	private ListProperty<Location> locations = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<Route> routes = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<Item> items = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final WorldType worldType;

	public World() {
		worldType = new WorldType();
		worldType.setLocations(new LocationsType());
		worldType.setRoutes(new RoutesType());
		worldType.setItems(new ItemsType());
		resetNextIds();
		bind();
	}

	public WorldType getWorldType() {
		return worldType;
	}

	@Override
	public ObservableList<Location> getLocations() {
		return locations.get();
	}

	@Override
	public void setLocations(ObservableList<Location> locations) {
		this.locations.set(locations);
	}

	@Override
	public ListProperty<Location> locationsProperty() {
		return locations;
	}

	@Override
	public ObservableList<Route> getRoutes() {
		return routes.get();
	}

	@Override
	public void setRoutes(ObservableList<Route> routes) {
		this.routes.set(routes);
	}

	@Override
	public ListProperty<Route> routesProperty() {
		return routes;
	}

	@Override
	public ObservableList<Item> getItems() {
		return items.get();
	}

	@Override
	public void setItems(ObservableList<Item> items) {
		this.items.set(items);
	}

	@Override
	public ListProperty<Item> itemsProperty() {
		return items;
	}

	private void bind() {
		locations.addListener(new LocationsChangeListener());
		routes.addListener(new RoutesChangeListener());
		items.addListener(new ItemsChangeListener());
	}

	private void resetNextIds() {
		Location.resetNextId();
		Route.resetNextId();
		Item.resetNextId();
	}

	private class LocationsChangeListener implements ListChangeListener<Location> {

		@Override
		public void onChanged(Change<? extends Location> change) {
			while (change.next()) {
				if (change.wasPermutated()) {
					for (int i = change.getFrom(); i < change.getTo(); ++i) {
						System.out.println("LocationsChangeListener: Location was permutated. Index: " + i);
					}
				} else if (change.wasUpdated()) {
					System.out.println("LocationsChangeListener: Location was updated.");
				} else {
					for (Location location : change.getRemoved()) {
						worldType.getLocations().getLocation().remove(location.getLocationType());
						System.out.println("LocationsChangeListener: LocationType was removed from WorldType. ID: "
								+ location.getLocationType().getId());
					}
					for (Location location : change.getAddedSubList()) {
						worldType.getLocations().getLocation().add(location.getLocationType());
						System.out.println("LocationsChangeListener: LocationType was added to WorldType. ID: "
								+ location.getLocationType().getId());
					}
				}
			}
		}

	}

	private class RoutesChangeListener implements ListChangeListener<Route> {

		@Override
		public void onChanged(Change<? extends Route> change) {
			while (change.next()) {
				if (change.wasPermutated()) {
					for (int i = change.getFrom(); i < change.getTo(); ++i) {
						System.out.println("RoutesChangeListener: Route was permutated. Index: " + i);
					}
				} else if (change.wasUpdated()) {
					System.out.println("RoutesChangeListener: Route was updated.");
				} else {
					for (Route route : change.getRemoved()) {
						worldType.getRoutes().getRoute().remove(route.getRouteType());
						System.out.println("RoutesChangeListener: RouteType was removed from WorldType. ID: "
								+ route.getRouteType().getId());
					}
					for (Route route : change.getAddedSubList()) {
						worldType.getRoutes().getRoute().add(route.getRouteType());
						System.out.println("RoutesChangeListener: RouteType was added to WorldType. ID: "
								+ route.getRouteType().getId());
					}
				}
			}
		}

	}

	private class ItemsChangeListener implements ListChangeListener<Item> {

		@Override
		public void onChanged(Change<? extends Item> change) {
			while (change.next()) {
				if (change.wasPermutated()) {
					for (int i = change.getFrom(); i < change.getTo(); ++i) {
						System.out.println("ItemsChangeListener: Item was permutated. Index: " + i);
					}
				} else if (change.wasUpdated()) {
					System.out.println("ItemsChangeListener: Item was updated.");
				} else {
					for (Item item : change.getRemoved()) {
						worldType.getItems().getItem().remove(item.getItemType());
						System.out.println("ItemsChangeListener: ItemType was removed from WorldType. ID: "
								+ item.getItemType().getId());
					}
					for (Item item : change.getAddedSubList()) {
						worldType.getItems().getItem().add(item.getItemType());
						System.out.println("ItemsChangeListener: ItemType was added to WorldType. ID: "
								+ item.getItemType().getId());
					}
				}
			}
		}

	}

}
