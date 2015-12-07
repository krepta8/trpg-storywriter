package games.closetmonster.trpg.storywriter.i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private static final String BUNDLE_NAME = "games.closetmonster.trpg.storywriter.i18n.messages"; //$NON-NLS-1$
	private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, DEFAULT_LOCALE);

	public static ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public static String getString(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!'; // $NON-NLS-1$
		}
	}

	public static void setLocale(Locale locale) {
		resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
	}

	private Messages() {
		// Static utility class that cannot be instantiated.
	}

}
