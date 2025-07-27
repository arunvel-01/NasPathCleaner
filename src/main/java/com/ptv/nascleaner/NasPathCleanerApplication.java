package com.ptv.nascleaner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class NasPathCleanerApplication extends Application {

    private AnnotationConfigApplicationContext context;

    public static void main(String[] args) {
        launch(args); // Start JavaFX application
    }

    @Override
    public void init() {
        // Initialize Spring context
        context = new AnnotationConfigApplicationContext(NasPathCleanerApplication.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML and inject Spring beans as controllers
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui.fxml"));
        loader.setControllerFactory(context::getBean);

        // Set scene and show stage
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle("NAS Path File Cleaner");
        primaryStage.show();
    }

    @Override
    public void stop() {
        // Close Spring context on exit
        context.close();
    }
}
