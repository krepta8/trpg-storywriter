//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2015.12.15 at 12:53:38 AM CST
//

package games.closetmonster.trpg.storywriter.model.xml.world;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the
 * games.closetmonster.trpg.storywriter.model.xml.world package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _World_QNAME = new QName(
			"http://www.closetmonster.games/trpg/storywriter/model/xml/world", "world");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package:
	 * games.closetmonster.trpg.storywriter.model.xml.world
	 *
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link WorldType }
	 *
	 */
	public WorldType createWorldType() {
		return new WorldType();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link WorldType }
	 * {@code >}}
	 *
	 */
	@XmlElementDecl(namespace = "http://www.closetmonster.games/trpg/storywriter/model/xml/world", name = "world")
	public JAXBElement<WorldType> createWorld(WorldType value) {
		return new JAXBElement<WorldType>(_World_QNAME, WorldType.class, null, value);
	}

}