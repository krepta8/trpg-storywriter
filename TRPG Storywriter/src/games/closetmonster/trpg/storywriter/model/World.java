/**
 *
 */
package games.closetmonster.trpg.storywriter;

import games.closetmonster.trpg.storywriter.xml.items.ItemsType;
import games.closetmonster.trpg.storywriter.xml.locations.LocationsType;
import games.closetmonster.trpg.storywriter.xml.model.ModelType;
import games.closetmonster.trpg.storywriter.xml.routes.RoutesType;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * @author Jonathan Radliff
 *
 */
public class Model implements Modellable {

	private ListProperty<Item> items = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<Location> locations = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<Route> routes = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final ModelType modelType;

	public Model() {
		modelType = new ModelType();
		modelType.setItems(new ItemsType());
		modelType.setLocations(new LocationsType());
		modelType.setRoutes(new RoutesType());
		resetNextIds();
		bind();
	}

	public Model(ModelType modelType) {
		this.modelType = modelType;
		modelType.getItems().getItem().forEach(e -> items.add(new Item(e)));
		modelType.getLocations().getLocation().forEach(e -> locations.add(new Location(e)));
		modelType.getRoutes().getRoute().forEach(e -> routes.add(new Route(e)));
		resetNextIds();
		bind();
	}

	public ModelType getModelType() {
		return modelType;
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
		items.addListener(new ItemsChangeListener());
		locations.addListener(new LocationsChangeListener());
		routes.addListener(new RoutesChangeListener());
	}

	private void resetNextIds() {
		Item.resetNextId();
		Location.resetNextId();
		Route.resetNextId();
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
						modelType.getLocations().getLocation().remove(location.getLocationType());
						System.out.println("LocationsChangeListener: LocationType was removed from ModelType. ID: "
								+ location.getLocationType().getId());
					}
					for (Location location : change.getAddedSubList()) {
						modelType.getLocations().getLocation().add(location.getLocationType());
						System.out.println("LocationsChangeListener: LocationType was added to ModelType. ID: "
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
						modelType.getRoutes().getRoute().remove(route.getRouteType());
						System.out.println("RoutesChangeListener: RouteType was removed from ModelType. ID: "
								+ route.getRouteType().getId());
					}
					for (Route route : change.getAddedSubList()) {
						modelType.getRoutes().getRoute().add(route.getRouteType());
						System.out.println("RoutesChangeListener: RouteType was added to ModelType. ID: "
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
						modelType.getItems().getItem().remove(item.getItemType());
						System.out.println("ItemsChangeListener: ItemType was removed from ModelType. ID: "
								+ item.getItemType().getId());
					}
					for (Item item : change.getAddedSubList()) {
						modelType.getItems().getItem().add(item.getItemType());
						System.out.println("ItemsChangeListener: ItemType was added to ModelType. ID: "
								+ item.getItemType().getId());
					}
				}
			}
		}

	}

}
