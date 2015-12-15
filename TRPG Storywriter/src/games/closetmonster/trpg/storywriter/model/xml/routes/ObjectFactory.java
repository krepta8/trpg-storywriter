//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2015.12.15 at 12:53:38 AM CST
//

package games.closetmonster.trpg.storywriter.model.xml.routes;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the
 * games.closetmonster.trpg.storywriter.model.xml.routes package.
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

	private final static QName _Routes_QNAME = new QName(
			"http://www.closetmonster.games/trpg/storywriter/model/xml/routes", "routes");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package:
	 * games.closetmonster.trpg.storywriter.model.xml.routes
	 *
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link RoutesType }
	 *
	 */
	public RoutesType createRoutesType() {
		return new RoutesType();
	}

	/**
	 * Create an instance of {@link LockType }
	 *
	 */
	public LockType createLockType() {
		return new LockType();
	}

	/**
	 * Create an instance of {@link RouteType }
	 *
	 */
	public RouteType createRouteType() {
		return new RouteType();
	}

	/**
	 * Create an instance of {@link RequiredItemType }
	 *
	 */
	public RequiredItemType createRequiredItemType() {
		return new RequiredItemType();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link RoutesType }
	 * {@code >}}
	 *
	 */
	@XmlElementDecl(namespace = "http://www.closetmonster.games/trpg/storywriter/model/xml/routes", name = "routes")
	public JAXBElement<RoutesType> createRoutes(RoutesType value) {
		return new JAXBElement<RoutesType>(_Routes_QNAME, RoutesType.class, null, value);
	}

}
