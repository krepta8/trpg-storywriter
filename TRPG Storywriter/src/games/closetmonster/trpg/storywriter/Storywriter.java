/**
 *
 */
package games.closetmonster.trpg.storywriter;

import java.util.Locale;

import games.closetmonster.trpg.storywriter.i18n.Messages;
import games.closetmonster.trpg.storywriter.model.Modellable;
import games.closetmonster.trpg.storywriter.model.xml.XMLBinder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author Jonathan Radliff
 *
 */
public class Storywriter extends Application {

	private static enum Parameter {

		FILE("file"), //$NON-NLS-1$
		LANGUAGE("lang"); //$NON-NLS-1$

		private String key;

		private Parameter(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}

	}

	public static final Locale[] SUPPORTED_LANGUAGES = { Locale.ENGLISH, Locale.FRENCH, Locale.GERMAN,
			new Locale("es") };

	public static boolean changeLanguage(Locale locale) {
		if (isSupportedLanguage(locale)) {
			Messages.setLocale(locale);
			return true;
		}
		return false;
	}

	public static boolean isSupportedLanguage(Locale locale) {
		for (Locale supportedLocale : SUPPORTED_LANGUAGES) {
			if (locale.getLanguage().equals(supportedLocale.getLanguage())) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	private StorywriterController controller;
	private Modellable model;
	private Stage primaryStage;

	public Storywriter() {
		this.model = XMLBinder.getWorld();
		this.controller = new StorywriterController(this, model);
	}

	@Override
	public void init() {
		// TODO Use Parameters to initialize application: i.e. set localization
		// settings and load project file if passed as an argument.
		Locale locale = Locale.getDefault();
		String lang = getParameters().getNamed().get(Parameter.LANGUAGE.getKey());
		// Parse the value stored in lang for a valid Language Tag. This helps
		// ensure forward compatibility as language codes inevitably change.
		if (lang != null) {
			// If a valid language was supplied, let take precedence.
			locale = Locale.forLanguageTag(lang);
		}
		changeLanguage(locale);
	}

	public void restart() {
		this.model = XMLBinder.getWorld();
		this.controller = new StorywriterController(this, model);
		start(primaryStage);
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("Storywriter.fxml"));
			fxmlLoader.setResources(Messages.getResourceBundle());
			fxmlLoader.setControllerFactory(new ControllerFactory());
			BorderPane root = (BorderPane) fxmlLoader.load();
			Scene scene = new Scene(root, 640, 480);
			scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class ControllerFactory implements Callback<Class<?>, Object> {

		@Override
		public Object call(Class<?> type) {
			if (type == StorywriterController.class) {
				return controller;
			} else if (type == LocationsController.class) {
				return controller.getLocationsController();
			} else if (type == RoutesController.class) {
				return controller.getRoutesController();
			} else if (type == ItemsController.class) {
				return controller.getItemsController();
			} else {
				try {
					// Default behavior: Invoke no-argument constructor.
					return type.newInstance();
				} catch (Exception e) {
					System.err.println("Could not create controller for " + type.getName());
					throw new RuntimeException(e);
				}
			}
		}

	}

}
