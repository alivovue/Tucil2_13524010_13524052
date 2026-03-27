package frontend;

import frontend.io.UIHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIMain extends Application {
    @Override
    public void start(Stage stage) {
        UIHandler uiHandler = new UIHandler(stage);
        Scene scene = new Scene(uiHandler, 800, 600);
        stage.setTitle(".obj Viewer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

