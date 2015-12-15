/**
 *
 */
package games.closetmonster.trpg.storywriter;

import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import games.closetmonster.javafx.view.Dialogs;
import games.closetmonster.trpg.storywriter.i18n.Messages;
import games.closetmonster.trpg.storywriter.model.Item;
import games.closetmonster.trpg.storywriter.model.Location;
import games.closetmonster.trpg.storywriter.model.Modellable;
import games.closetmonster.trpg.storywriter.model.Route;
import games.closetmonster.trpg.storywriter.model.xml.XMLBinder;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleGroup;

/**
 * @author Jonathan Radliff
 *
 */
public class StorywriterController implements Initializable {

	private static void showAboutDialog() {
		Dialogs.showInformation(Messages.getString("menu.dialog.information.about.title"), //$NON-NLS-1$
				Messages.getString("menu.dialog.information.about.header"), //$NON-NLS-1$
				Messages.getString("menu.dialog.information.about.content")); //$NON-NLS-1$
	}

	private static boolean showConfirmOpenDialog() {
		return Dialogs.showConfirmation("Confirm Open", "Open a new project?", "You will lose any unsaved changes.");
	}

	private static void showLoadFailureDialog(String directory, String fileName) {
		Dialogs.showError("Load Failure", "Your data could not be loaded.",
				"An error occured when loading your data from: " + directory + fileName);
	}

	private static void showLoadSuccessDialog(String directory, String fileName) {
		Dialogs.showInformation("Load Success", "Loaded.", "Your file was loaded from: " + directory + fileName);
	}

	private static void showSaveFailureDialog(String directory, String fileName) {
		Dialogs.showError("Save Failure", "Your data could not be saved.",
				"An error occured when saving your data to: " + directory + fileName);
	}

	private static void showSaveSuccessDialog(String directory, String fileName) {
		Dialogs.showInformation("Save Success", "Saved.", "Your file was saved to: " + directory + fileName);
	}

	@FXML
	private Menu languageMenu;

	private ToggleGroup languageToggleGroup;

	@FXML
	private SplitPane locationsPane;

	@FXML
	private SplitPane routesPane;

	@FXML
	private SplitPane itemsPane;

	private Storywriter app;
	private LocationsController locationsController;
	private RoutesController routesController;
	private ItemsController itemsController;
	private Modellable model;

	public StorywriterController(Storywriter app, Modellable model) {
		this.app = app;
		this.model = model;
		this.locationsController = new LocationsController(model);
		this.routesController = new RoutesController(model);
		this.itemsController = new ItemsController(model);
		// TODO Output list changes for testing. Delete this when done.
		model.getLocations().addListener(new LocationsChangeListener());
		model.getRoutes().addListener(new RoutesChangeListener());
		model.getItems().addListener(new ItemsChangeListener());
	}

	public Modellable getModel() {
		return model;
	}

	public LocationsController getLocationsController() {
		return locationsController;
	}

	public void setLocationsController(LocationsController locationsController) {
		this.locationsController = locationsController;
	}

	public RoutesController getRoutesController() {
		return routesController;
	}

	public void setRoutesController(RoutesController routesController) {
		this.routesController = routesController;
	}

	public ItemsController getItemsController() {
		return itemsController;
	}

	public void setItemsController(ItemsController itemsController) {
		this.itemsController = itemsController;
	}

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		// TODO Initialize language menu based on supported languages. Order
		// based upon an appropriate standard.
		languageToggleGroup = new ToggleGroup();
		for (Locale locale : Storywriter.SUPPORTED_LANGUAGES) {
			RadioMenuItem radioMenuItem = new RadioMenuItem(locale.getDisplayLanguage());
			radioMenuItem.setUserData(locale);
			radioMenuItem.setOnAction(this::changeLanguage);
			languageMenu.getItems().add(radioMenuItem);
			languageToggleGroup.getToggles().add(radioMenuItem);
			if (Messages.getResourceBundle().getLocale().equals(locale)) {
				languageToggleGroup.selectToggle(radioMenuItem);
			}
		}
	}

	@FXML
	private void about() {
		showAboutDialog();
	}

	private void changeLanguage(ActionEvent event) {
		Locale locale = (Locale) languageToggleGroup.getSelectedToggle().getUserData();
		if (Storywriter.changeLanguage(locale)) {
			app.restart();
		}
	}

	@FXML
	private void newFile() {
		if (showConfirmOpenDialog()) {
			XMLBinder.newWorld();
			app.restart();
		}
	}

	@FXML
	private void open() {
		Optional<String> fileName = Dialogs.showTextInput(XMLBinder.XML_FILENAME, "Load",
				"Input the name of the file to load.", "File name:");
		if (fileName.isPresent() && !fileName.get().isEmpty()) {
			if (showConfirmOpenDialog()) {
				if (XMLBinder.load(fileName.get())) {
					app.restart();
					showLoadSuccessDialog(XMLBinder.XML_DIRECTORY, fileName.get());
				} else {
					showLoadFailureDialog(XMLBinder.XML_DIRECTORY, fileName.get());
				}
			}
		}
	}

	@FXML
	private void save() {
		if (XMLBinder.save(XMLBinder.XML_FILENAME)) {
			showSaveSuccessDialog(XMLBinder.XML_DIRECTORY, XMLBinder.XML_FILENAME);
		} else {
			showSaveFailureDialog(XMLBinder.XML_DIRECTORY, XMLBinder.XML_FILENAME);
		}
	}

	@FXML
	private void saveAs() {
		Optional<String> fileName = Dialogs.showTextInput(XMLBinder.XML_FILENAME, "Save As",
				"Input a name for your save data file.", "File name:");
		if (fileName.isPresent() && !fileName.get().isEmpty()) {
			if (XMLBinder.save(fileName.get())) {
				showSaveSuccessDialog(XMLBinder.XML_DIRECTORY, fileName.get());
			} else {
				showSaveFailureDialog(XMLBinder.XML_DIRECTORY, fileName.get());
			}
		}
	}

	@FXML
	private void quit() {
		Platform.exit();
	}

	private class LocationsChangeListener implements ListChangeListener<Location> {

		@Override
		public void onChanged(Change<? extends Location> change) {
			while (change.next()) {
				if (change.wasPermutated()) {
					for (int i = change.getFrom(); i < change.getTo(); ++i) {
						System.out.println("Location was permutated. Index: " + i);
					}
				} else if (change.wasUpdated()) {
					System.out.println("Location was updated.");
				} else {
					for (Location location : change.getRemoved()) {
						System.out.println("Location was removed. ID: " + location.getId());
					}
					for (Location location : change.getAddedSubList()) {
						System.out.println("Location was added. ID: " + location.getId());
					}
				}
			}
		}

	}

	private class RoutesChangeListener implements ListChangeListener<Route> {

		@Override
		public void onChanged(Change<? extends Route> change) {
			while (change.next()) {
				if (change.wasPermutated()) {
					for (int i = change.getFrom(); i < change.getTo(); ++i) {
						System.out.println("Route was permutated. Index: " + i);
					}
				} else if (change.wasUpdated()) {
					System.out.println("Route was updated.");
				} else {
					for (Route route : change.getRemoved()) {
						System.out.println("Route was removed. ID: " + route.getId());
					}
					for (Route route : change.getAddedSubList()) {
						System.out.println("Route was added. ID: " + route.getId());
					}
				}
			}
		}

	}

	private class ItemsChangeListener implements ListChangeListener<Item> {

		@Override
		public void onChanged(Change<? extends Item> change) {
			while (change.next()) {
				if (change.wasPermutated()) {
					for (int i = change.getFrom(); i < change.getTo(); ++i) {
						System.out.println("Item was permutated. Index: " + i);
					}
				} else if (change.wasUpdated()) {
					System.out.println("Item was updated.");
				} else {
					for (Item item : change.getRemoved()) {
						System.out.println("Item was removed. ID: " + item.getId());
					}
					for (Item item : change.getAddedSubList()) {
						System.out.println("Item was added. ID: " + item.getId());
					}
				}
			}
		}

	}

}
