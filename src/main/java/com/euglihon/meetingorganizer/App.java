package com.euglihon.meetingorganizer;

import com.euglihon.meetingorganizer.controller.CategoryController;
import com.euglihon.meetingorganizer.controller.ContactController;
import com.euglihon.meetingorganizer.controller.EventController;
import com.euglihon.meetingorganizer.data.DbContext;
import com.euglihon.meetingorganizer.data.DbInitialization;
import com.euglihon.meetingorganizer.repository.IContactRepository;
import com.euglihon.meetingorganizer.repository.IEventRepository;
import com.euglihon.meetingorganizer.repository.impl.CategoryRepository;
import com.euglihon.meetingorganizer.repository.ICategoryRepository;
import com.euglihon.meetingorganizer.repository.impl.ContactRepository;
import com.euglihon.meetingorganizer.repository.impl.EventRepository;
import com.euglihon.meetingorganizer.service.IContactService;
import com.euglihon.meetingorganizer.service.IEventService;
import com.euglihon.meetingorganizer.service.impl.CategoryService;
import com.euglihon.meetingorganizer.service.ICategoryService;
import com.euglihon.meetingorganizer.service.impl.ContactService;
import com.euglihon.meetingorganizer.service.impl.EventService;
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
    IEventRepository eventRepository = new EventRepository(dbContext);

    // Services
    ICategoryService categoryService = new CategoryService(categoryRepository);
    IContactService contactService = new ContactService(contactRepository);
    IEventService eventService = new EventService(eventRepository);

    /**
     * Starts the application.
     *
     * @param stage the primary stage for this application
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void start(Stage stage) throws IOException {

        // Initialize the database
        DbInitialization dbInitialization = new DbInitialization(categoryRepository, contactRepository, eventRepository);
        dbInitialization.init();

        // Set up the root layout and scene
        this.root = new BorderPane();
        Scene scene = new Scene(root, 900, 950);

        // Add css styles
        scene.getStylesheets().add(App.class.getResource("css/main.css").toExternalForm());
        scene.getStylesheets().add(App.class.getResource("css/menu.css").toExternalForm());

        // Create and add the menu bar to the top of the layout
        MenuBar menuBar = createMenuBar();
        this.root.setTop(menuBar);

        // Show the initial home page
        this.showEventPage();

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

        // Events menu item with action to show the home page
        MenuItem homeMenuItem = new MenuItem("Events");
        homeMenuItem.setOnAction(actionEvent -> {
            try {
                showEventPage();
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

        // Category menu item with action to show the contact page
        MenuItem categoryMenuItem = new MenuItem("Category");
        categoryMenuItem.setOnAction(actionEvent -> {
            try {
                showCategoryPage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Add menu items to the menu and return the menu bar
        menu.getItems().addAll(homeMenuItem, contactsMenuItem, categoryMenuItem);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu);
        return menuBar;
    }

    /**
     * Loads and displays the home page.
     *
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
    private void showEventPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("event-view.fxml"));
        EventController eventController = new EventController(this.eventService, this.categoryService, this.contactService);
        loader.setController(eventController);
        this.root.setCenter(loader.load());
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
        this.root.setCenter(loader.load());
    }

    /**
     * Loads and displays the category page.
     *
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
    private void showCategoryPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("category-view.fxml"));
        CategoryController categoryController = new CategoryController(this.categoryService);
        loader.setController(categoryController);
        this.root.setCenter(loader.load());
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
