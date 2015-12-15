/**
 *
 */
package games.closetmonster.trpg.storywriter.model.xml;

import java.util.List;
import java.util.stream.Collectors;

import games.closetmonster.jaxb.JAXBParser;
import games.closetmonster.trpg.storywriter.model.Item;
import games.closetmonster.trpg.storywriter.model.Location;
import games.closetmonster.trpg.storywriter.model.Modellable;
import games.closetmonster.trpg.storywriter.model.Route;
import games.closetmonster.trpg.storywriter.model.World;
import games.closetmonster.trpg.storywriter.model.xml.items.ItemType;
import games.closetmonster.trpg.storywriter.model.xml.locations.LocationType;
import games.closetmonster.trpg.storywriter.model.xml.world.WorldType;

/**
 * @author Jonathan Radliff
 *
 */
public class XMLBinder {

	public static final String ITEM_ID_PREFIX = "item-";
	public static final String LOCATION_ID_PREFIX = "location-";
	public static final String ROUTE_ID_PREFIX = "route-";
	public static final String XML_DIRECTORY = "resources/data/xml/";
	public static final String XML_FILENAME = "test.xml";
	public static final String XSD_PATHNAME = "src/games/closetmonster/trpg/storywriter/model/xml/world.xsd";
	private static World world = new World();

	public static Modellable getWorld() {
		return world;
	}

	public static boolean load() {
		return load(XML_FILENAME);
	}

	public static boolean load(String fileName) {
		boolean success = false;
		WorldType worldType = JAXBParser.unmarshal(XML_DIRECTORY + fileName, XSD_PATHNAME, WorldType.class);
		// TODO Need to add real checks that model is valid before assigning.
		if (worldType != null) {
			newWorld(worldType);
			success = true;
		}
		return success;
	}

	public static void newWorld() {
		XMLBinder.world = new World();
	}

	private static void newWorld(WorldType worldType) {
		XMLBinder.world = new World();
		worldType.getItems().getItem().forEach(e -> world.getItems().add(new Item(e)));
		worldType.getLocations().getLocation().forEach(e -> world.getLocations().add(new Location(e)));
		worldType.getRoutes().getRoute().forEach(e -> world.getRoutes().add(new Route(e)));
	}

	public static boolean save() {
		return save(XML_FILENAME);
	}

	public static boolean save(String fileName) {
		return JAXBParser.marshal(XML_DIRECTORY + fileName, XSD_PATHNAME, world.getWorldType());
	}

	public static Item lookupItem(Object idref) {
		Item foundItem = null;
		if (idref instanceof ItemType) {
			ItemType itemType = (ItemType) idref;
			int itemId = parseId(itemType.getId(), ITEM_ID_PREFIX);
			if (itemId > -1) {
				List<Item> list = world.getItems().filtered(item -> item.getId() == itemId);
				if (!list.isEmpty()) {
					foundItem = list.get(0);
				}
			}
		}
		return foundItem;
	}

	public static List<Item> lookupItems(List<Object> idrefs) {
		return idrefs.stream().map(XMLBinder::lookupItem).collect(Collectors.toList());
	}

	public static Location lookupLocation(Object idref) {
		Location foundLocation = null;
		if (idref instanceof LocationType) {
			LocationType locationType = (LocationType) idref;
			int locationId = parseId(locationType.getId(), LOCATION_ID_PREFIX);
			if (locationId > -1) {
				List<Location> list = world.getLocations().filtered(location -> location.getId() == locationId);
				if (!list.isEmpty()) {
					foundLocation = list.get(0);
				}
			}
		}
		return foundLocation;
	}

	public static int parseId(Object idref, String idPrefix) {
		int id = -1;
		if (idref != null) {
			if (idref.toString().startsWith(idPrefix)) {
				id = Integer.parseInt(idref.toString().split(idPrefix, 2)[1]);
			}
		}
		return id;
	}

}
