/**
 *
 */
package games.closetmonster.jaxb;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

/**
 * @author Jonathan Radliff
 *
 */
public final class JAXBParser {

	public static <T> boolean marshal(String xml, String xsd, T xmlObject) {
		Schema schema = null;
		if (xsd != null) {
			File xsdFile = new File(xsd);
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			try {
				schema = schemaFactory.newSchema(xsdFile);
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}
		boolean success = true;
		try {
			File xmlFile = new File(xml);
			JAXBContext context = JAXBContext.newInstance(xmlObject.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setSchema(schema);
			marshaller.setEventHandler(new JAXBValidationEventHandler());
			marshaller.marshal(xmlObject, xmlFile);
			marshaller.marshal(xmlObject, System.out);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}

	public static <T> boolean marshal(String xml, T xmlObject) {
		return marshal(xml, null, xmlObject);
	}

	public static <T> T unmarshal(String xml, Class<T> type) {
		return unmarshal(xml, null, type);
	}

	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(String xml, String xsd, Class<T> type) {
		Schema schema = null;
		if (xsd != null) {
			File xsdFile = new File(xsd);
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			try {
				schema = schemaFactory.newSchema(xsdFile);
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}
		T xmlObject = null;
		try {
			File xmlFile = new File(xml);
			JAXBContext context = JAXBContext.newInstance(type);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setSchema(schema);
			unmarshaller.setEventHandler(new JAXBValidationEventHandler());
			xmlObject = (T) unmarshaller.unmarshal(xmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlObject;
	}

	private JAXBParser() {
		// Static utility class that cannot be instantiated.
	}

}
