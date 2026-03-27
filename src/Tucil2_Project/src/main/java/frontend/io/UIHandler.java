package frontend.io;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
        applyStyling();
        inputHandler.attachHandlers();
    }

    private void setupLayout() {
        HBox buttonBar = new HBox(
            12,
            buttonHandler.getOpenButton(),
            buttonHandler.getResetButton(),
            buttonHandler.getFilenameLabel()
        );

        buttonBar.setAlignment(Pos.CENTER_LEFT);
        buttonBar.setPadding(new Insets(12, 16, 12, 16));

        setTop(buttonBar);
        setCenter(viewerPanel);
    }

    private void applyStyling() {
        setStyle("""
            -fx-background-color: #121212;
        """);

        if (getTop() instanceof HBox buttonBar) {
            buttonBar.setStyle("""
                -fx-background-color: linear-gradient(to bottom, #1f1f1f, #181818);
                -fx-border-color: #2c2c2c;
                -fx-border-width: 0 0 1 0;
            """);
        }

        buttonHandler.getOpenButton().setStyle("""
            -fx-background-color: #2d6cdf;
            -fx-text-fill: white;
            -fx-font-size: 13px;
            -fx-font-weight: bold;
            -fx-background-radius: 8;
            -fx-padding: 8 16 8 16;
            -fx-cursor: hand;
        """);

        buttonHandler.getResetButton().setStyle("""
            -fx-background-color: #2a2a2a;
            -fx-text-fill: #f0f0f0;
            -fx-font-size: 13px;
            -fx-font-weight: bold;
            -fx-background-radius: 8;
            -fx-border-color: #444444;
            -fx-border-radius: 8;
            -fx-padding: 8 16 8 16;
            -fx-cursor: hand;
        """);

        Label filenameLabel = buttonHandler.getFilenameLabel();
        filenameLabel.setStyle("""
            -fx-background-color: #252525;
            -fx-text-fill: #d8d8d8;
            -fx-font-size: 13px;
            -fx-background-radius: 8;
            -fx-padding: 8 14 8 14;
            -fx-border-color: #3a3a3a;
            -fx-border-radius: 8;
        """);

        viewerPanel.setStyle("""
            -fx-background-color: black;
        """);
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