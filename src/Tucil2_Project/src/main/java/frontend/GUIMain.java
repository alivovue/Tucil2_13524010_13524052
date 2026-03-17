package frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUIMain extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello, World from JavaFX!");
        StackPane root = new StackPane(label);

        Scene scene = new Scene(root, 400, 200);

        stage.setTitle("Hello JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}