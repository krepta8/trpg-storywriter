//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2015.12.15 at 12:53:38 AM CST
//

package games.closetmonster.trpg.storywriter.model.xml.world;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import games.closetmonster.trpg.storywriter.model.xml.items.ItemsType;
import games.closetmonster.trpg.storywriter.model.xml.locations.LocationsType;
import games.closetmonster.trpg.storywriter.model.xml.routes.RoutesType;

/**
 * <p>
 * Java class for worldType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="worldType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.closetmonster.games/trpg/storywriter/model/xml/locations}locations"/>
 *         &lt;element ref="{http://www.closetmonster.games/trpg/storywriter/model/xml/routes}routes"/>
 *         &lt;element ref="{http://www.closetmonster.games/trpg/storywriter/model/xml/items}items"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "worldType", propOrder = { "locations", "routes", "items" })
@XmlRootElement(name = "world")
public class WorldType {

	@XmlElement(namespace = "http://www.closetmonster.games/trpg/storywriter/model/xml/locations", required = true)
	protected LocationsType locations;
	@XmlElement(namespace = "http://www.closetmonster.games/trpg/storywriter/model/xml/routes", required = true)
	protected RoutesType routes;
	@XmlElement(namespace = "http://www.closetmonster.games/trpg/storywriter/model/xml/items", required = true)
	protected ItemsType items;

	/**
	 * Gets the value of the locations property.
	 *
	 * @return possible object is {@link LocationsType }
	 *
	 */
	public LocationsType getLocations() {
		return locations;
	}

	/**
	 * Sets the value of the locations property.
	 *
	 * @param value
	 *            allowed object is {@link LocationsType }
	 *
	 */
	public void setLocations(LocationsType value) {
		this.locations = value;
	}

	/**
	 * Gets the value of the routes property.
	 *
	 * @return possible object is {@link RoutesType }
	 *
	 */
	public RoutesType getRoutes() {
		return routes;
	}

	/**
	 * Sets the value of the routes property.
	 *
	 * @param value
	 *            allowed object is {@link RoutesType }
	 *
	 */
	public void setRoutes(RoutesType value) {
		this.routes = value;
	}

	/**
	 * Gets the value of the items property.
	 *
	 * @return possible object is {@link ItemsType }
	 *
	 */
	public ItemsType getItems() {
		return items;
	}

	/**
	 * Sets the value of the items property.
	 *
	 * @param value
	 *            allowed object is {@link ItemsType }
	 *
	 */
	public void setItems(ItemsType value) {
		this.items = value;
	}

}
