/**
 *
 */
package games.closetmonster.jaxb;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author Jonathan Radliff
 *
 */
public class JAXBErrorHandler implements ErrorHandler {

	@Override
	public void error(SAXParseException exception) throws SAXException {
		throw exception;
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		throw exception;
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		throw exception;
	}

}
