package frontend.io;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import backend.data.Model;

public class UIHandler extends BorderPane {
    private Stage stage;
    private ViewerPanel viewerPanel;
    private ButtonHandler buttonHandler;
    private InputHandler inputHandler;

    public UIHandler(Stage stage) {
        this.stage = stage;
        this.viewerPanel = new ViewerPanel();
        this.buttonHandler = new ButtonHandler(this);
        this.inputHandler = new InputHandler(viewerPanel);
        setupLayout();
        inputHandler.attachHandlers();
    }

    private void setupLayout() {
        HBox buttonBar = new HBox(
            10,
            buttonHandler.getOpenButton(),
            buttonHandler.getResetButton(),
            // buttonHandler.getWireframeCheckBox(),
            buttonHandler.getFilenameLabel()
        );
        setTop(buttonBar);
        setCenter(viewerPanel);

    }

    public ViewerPanel getViewerPanel() {
        return this.viewerPanel;
    }

    public ButtonHandler getButtonHandler() {
        return this.buttonHandler;
    }

    public Stage getStage() {
        return this.stage;
    }
}
