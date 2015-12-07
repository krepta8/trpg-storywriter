/**
 *
 */
package games.closetmonster.jaxb;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

/**
 * @author Jonathan Radliff
 *
 */
public class JAXBValidationEventHandler implements ValidationEventHandler {

	@Override
	public boolean handleEvent(ValidationEvent event) {
		System.out.println("An error was caught during validation.");
		System.out.println("Severity: " + event.getSeverity());
		System.out.println("Event: " + event.getMessage());
		System.out.println("Linked Exception: " + event.getLinkedException());
		System.out.println("Event Locator Object: " + event.getLocator().getObject());
		return false;
	}

}
