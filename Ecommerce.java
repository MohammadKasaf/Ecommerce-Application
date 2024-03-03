package org.example.Ecommerce;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Ecommerce extends Application {

    UserInterface userInterface=new UserInterface();

    // Overridden method to start the JavaFX application
    @Override
    public void start(Stage stage) {
        // Creating a scene with the content created by the UserInterface class
        Scene scene = new Scene(userInterface.createContent());
        // Setting the scene to the stage and displaying the stage
        stage.setScene(scene);
        stage.show();
    }

    // Main method to launch the JavaFX application
    public static void main(String[] args){
        // Launching the JavaFX application
        launch();
    }
}
