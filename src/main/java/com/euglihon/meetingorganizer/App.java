package com.euglihon.meetingorganizer;

import com.euglihon.meetingorganizer.controller.ContactController;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {

    private final DbContext dbContext = new DbContext();

    // repositories
    ICategoryRepository categoryRepository = new CategoryRepository(dbContext);
    IContactRepository contactRepository = new ContactRepository(dbContext);

    // services
    ICategoryService categoryService = new CategoryService(categoryRepository);
    IContactService contactService = new ContactService(contactRepository);

    // controllers
    ContactController homeController = new ContactController(contactService, categoryService);

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        // db init
        DbInitialization dbInitialization = new DbInitialization(categoryRepository, contactRepository);
        dbInitialization.init();

        FXMLLoader homePage = new FXMLLoader(App.class.getResource("contact-view.fxml"));
        homePage.setController(homeController);

        Scene scene = new Scene(homePage.load(), 760, 640);
        scene.getStylesheets().add(App.class.getResource("css/style.css").toExternalForm());

        stage.setTitle("Meeting organizer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}