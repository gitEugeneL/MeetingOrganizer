package com.euglihon.meetingorganizer;

import com.euglihon.meetingorganizer.controller.HomeController;
import com.euglihon.meetingorganizer.data.DbContext;
import com.euglihon.meetingorganizer.data.DbInitialization;
import com.euglihon.meetingorganizer.repository.category.CategoryRepository;
import com.euglihon.meetingorganizer.repository.category.ICategoryRepository;
import com.euglihon.meetingorganizer.services.category.CategoryService;
import com.euglihon.meetingorganizer.services.category.ICategoryService;
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

    // services
    ICategoryService categoryService = new CategoryService(categoryRepository);

    // controllers
    HomeController homeController = new HomeController(categoryService);

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        // db init
        DbInitialization dbInitialization = new DbInitialization(categoryRepository);
        dbInitialization.init();

        FXMLLoader homePage = new FXMLLoader(App.class.getResource("home-view.fxml"));
        homePage.setController(homeController);

        Scene scene = new Scene(homePage.load(), 760, 640);
        stage.setTitle("Meeting organizer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}