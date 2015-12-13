/**
 *
 */
package games.closetmonster.trpg.storywriter;

import javafx.beans.property.ListProperty;
import javafx.collections.ObservableList;

/**
 * @author Jonathan Radliff
 *
 */
public interface Modellable {

	public ObservableList<Location> getLocations();

	public void setLocations(ObservableList<Location> locations);

	public ListProperty<Location> locationsProperty();

	public ObservableList<Route> getRoutes();

	public void setRoutes(ObservableList<Route> routes);

	public ListProperty<Route> routesProperty();

	public ObservableList<Item> getItems();

	public void setItems(ObservableList<Item> items);

	public ListProperty<Item> itemsProperty();

}
