//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2015.12.06 at 12:17:38 PM CST
//

package games.closetmonster.trpg.storywriter.xml.routes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for lockType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="lockType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requiredItem" type="{http://www.closetmonster.games/trpg/storywriter/xml/routes}requiredItemType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="locked" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lockType", propOrder = { "requiredItem" })
public class LockType {

	@XmlElement(required = true, nillable = true)
	protected RequiredItemType requiredItem;
	@XmlAttribute(name = "locked", required = true)
	protected boolean locked;

	/**
	 * Gets the value of the requiredItem property.
	 *
	 * @return possible object is {@link RequiredItemType }
	 *
	 */
	public RequiredItemType getRequiredItem() {
		return requiredItem;
	}

	/**
	 * Sets the value of the requiredItem property.
	 *
	 * @param value
	 *            allowed object is {@link RequiredItemType }
	 *
	 */
	public void setRequiredItem(RequiredItemType value) {
		this.requiredItem = value;
	}

	/**
	 * Gets the value of the locked property.
	 *
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * Sets the value of the locked property.
	 *
	 */
	public void setLocked(boolean value) {
		this.locked = value;
	}

}
