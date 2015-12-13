/**
 *
 */
package games.closetmonster.trpg.storywriter.xml;

import java.util.List;
import java.util.stream.Collectors;

import games.closetmonster.jaxb.JAXBParser;
import games.closetmonster.trpg.storywriter.Item;
import games.closetmonster.trpg.storywriter.Location;
import games.closetmonster.trpg.storywriter.Model;
import games.closetmonster.trpg.storywriter.Modellable;
import games.closetmonster.trpg.storywriter.xml.items.ItemType;
import games.closetmonster.trpg.storywriter.xml.locations.LocationType;
import games.closetmonster.trpg.storywriter.xml.model.ModelType;

/**
 * @author Jonathan Radliff
 *
 */
public class XMLBinder {

	public static final String LOCATION_ID_PREFIX = "location-";
	public static final String ROUTE_ID_PREFIX = "route-";
	public static final String ITEM_ID_PREFIX = "item-";
	public static final String XML_DIRECTORY = "resources/data/xml/";
	public static final String XML_FILENAME = "test.xml";
	public static final String XSD_PATHNAME = "src/games/closetmonster/trpg/storywriter/xml/model.xsd";
	private static Model model = new Model();

	public static Modellable getModel() {
		return model;
	}

	public static ModelType getModelType() {
		return model.getModelType();
	}

	public static boolean load() {
		return load(XML_FILENAME);
	}

	public static boolean load(String fileName) {
		boolean success = false;
		ModelType modelType = JAXBParser.unmarshal(XML_DIRECTORY + fileName, XSD_PATHNAME, ModelType.class);
		// TODO Need to add real checks that model is valid before assigning.
		if (modelType != null) {
			setModelType(modelType);
			success = true;
		}
		return success;
	}

	public static void newModel() {
		XMLBinder.model = new Model();
	}

	public static boolean save() {
		return save(XML_FILENAME);
	}

	public static boolean save(String fileName) {
		return JAXBParser.marshal(XML_DIRECTORY + fileName, XSD_PATHNAME, model.getModelType());
	}

	private static void setModelType(ModelType modelType) {
		// FIXME Constructor for Model indirectly references model property in
		// XMLBinder before it is assigned, causing a race condition.
		XMLBinder.model = new Model(modelType);
	}

	public static Item lookupItem(Object idref) {
		Item foundItem = null;
		if (idref != null) {
			int itemId = parseId(idref, ITEM_ID_PREFIX);
			if (itemId > -1) {
				List<Item> list = model.getItems().filtered(item -> item.getId() == itemId);
				if (!list.isEmpty()) {
					foundItem = list.get(0);
				}
			}
		}
		return foundItem;
	}

	public static List<Item> lookupItems(List<Object> idrefs) {
		System.out.println("XMLBinder.lookupItems: " + idrefs.toString());
		System.out.println(idrefs.stream().map(XMLBinder::lookupItem).collect(Collectors.toList()).toString());
		return idrefs.stream().map(XMLBinder::lookupItem).collect(Collectors.toList());
	}

	public static ItemType lookupItemType(Object idref) {
		ItemType foundItemType = null;
		if (idref != null) {
			List<ItemType> list = model.getModelType().getItems().getItem().stream()
					.filter(itemType -> itemType.getId().equals(idref)).collect(Collectors.toList());
			if (!list.isEmpty()) {
				foundItemType = list.get(0);
			}
		}
		return foundItemType;
	}

	public static List<ItemType> lookupItemTypes(List<Object> idrefs) {
		return idrefs.stream().map(XMLBinder::lookupItemType).collect(Collectors.toList());
	}

	public static Location lookupLocation(Object idref) {
		Location foundLocation = null;
		if (idref != null) {
			int locationId = parseId(idref, LOCATION_ID_PREFIX);
			if (locationId > -1) {
				List<Location> list = model.getLocations().filtered(location -> location.getId() == locationId);
				if (!list.isEmpty()) {
					foundLocation = list.get(0);
				}
			}
		}
		return foundLocation;
	}

	public static LocationType lookupLocationType(Object idref) {
		LocationType foundLocationType = null;
		if (idref != null) {
			List<LocationType> list = model.getModelType().getLocations().getLocation().stream()
					.filter(locationType -> locationType.getId().equals(idref)).collect(Collectors.toList());
			if (!list.isEmpty()) {
				foundLocationType = list.get(0);
			}
		}
		return foundLocationType;
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
