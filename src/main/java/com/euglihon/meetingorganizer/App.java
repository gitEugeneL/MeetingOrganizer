package com.euglihon.meetingorganizer;

import com.euglihon.meetingorganizer.controller.ContactController;
import com.euglihon.meetingorganizer.controller.HomeController;
import com.euglihon.meetingorganizer.data.DbContext;
import com.euglihon.meetingorganizer.data.DbInitialization;
import com.euglihon.meetingorganizer.repository.IContactRepository;
import com.euglihon.meetingorganizer.repository.impl.CategoryRepository;
import com.euglihon.meetingorganizer.repository.ICategoryRepository;
import com.euglihon.meetingorganizer.repository.impl.ContactRepository;
import com.euglihon.meetingorganizer.service.IContactService;
import com.euglihon.meetingorganizer.service.impl.CategoryService;
import com.euglihon.meetingorganizer.service.ICategoryService;
import com.euglihon.meetingorganizer.service.impl.ContactService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Main application class for the Meeting Organizer.
 * It initializes the database, sets up the UI, and handles navigation between pages.
 */
public class App extends Application {

    // Root layout for the application
    private BorderPane root;

    // Database context
    private final DbContext dbContext = new DbContext();

    // Repositories
    ICategoryRepository categoryRepository = new CategoryRepository(dbContext);
    IContactRepository contactRepository = new ContactRepository(dbContext);

    // Services
    ICategoryService categoryService = new CategoryService(categoryRepository);
    IContactService contactService = new ContactService(contactRepository);

    /**
     * Starts the application.
     *
     * @param stage the primary stage for this application
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void start(Stage stage) throws IOException {

        // Initialize the database
        DbInitialization dbInitialization = new DbInitialization(categoryRepository, contactRepository);
        dbInitialization.init();

        // Set up the root layout and scene
        root = new BorderPane();
        Scene scene = new Scene(root, 800, 800);

        // Add css styles
        scene.getStylesheets().add(App.class.getResource("css/main.css").toExternalForm());
        scene.getStylesheets().add(App.class.getResource("css/menu.css").toExternalForm());

        // Create and add the menu bar to the top of the layout
        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        // Show the initial home page
        showContactPage();

        // Set the stage properties and show it
        stage.setTitle("Organizer Application");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates the menu bar with options to navigate between pages.
     *
     * @return the created MenuBar
     */
    private MenuBar createMenuBar() {
        Menu menu = new Menu("Menu");

        // Home menu item with action to show the home page
        MenuItem homeMenuItem = new MenuItem("Home");
        homeMenuItem.setOnAction(actionEvent -> {
            try {
                showHomePage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Contacts menu item with action to show the contact page
        MenuItem contactsMenuItem = new MenuItem("Contacts");
        contactsMenuItem.setOnAction(actionEvent -> {
            try {
                showContactPage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Add menu items to the menu and return the menu bar
        menu.getItems().addAll(homeMenuItem, contactsMenuItem);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        return menuBar;
    }

    /**
     * Loads and displays the home page.
     *
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
    private void showHomePage() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("home-view.fxml"));
        HomeController homeController = new HomeController();
        loader.setController(homeController);
        root.setCenter(loader.load());
    }

    /**
     * Loads and displays the contact page.
     *
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
    private void showContactPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("contact-view.fxml"));
        ContactController contactController = new ContactController(this.contactService, this.categoryService);
        loader.setController(contactController);
        root.setCenter(loader.load());
    }

    /**
     * The main method to launch the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
