package frontend.io;

import java.io.File;

import backend.data.Model;
import backend.filesystem.ObjParser;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

public class ButtonHandler {
    private Button openButton;
    private Button resetButton;
    // private CheckBox wireframeCheckBox;
    private Label fileNameLabel;
    private UIHandler uiHandler;

    public ButtonHandler(UIHandler uiHandler) {
        this.uiHandler = uiHandler;
        this.openButton = new Button("Open");
        this.resetButton = new Button("Reset");
        // this.wireframeCheckBox = new CheckBox("Wireframe");
        this.fileNameLabel = new Label("No file selected");

        attachHandlers();
    }

    private void attachHandlers() {
        openButton.setOnAction(e -> handleOpenFile());
        resetButton.setOnAction(e -> handleResetFile());
        // wireframeCheckBox.setOnAction(e -> handleWireFrame());
    }

    private void handleOpenFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open OBJ File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("OBJ Files", "*.obj")
        );

        File file = fileChooser.showOpenDialog(uiHandler.getStage());

        if (file == null) {
            return;
        }

        try {
            Model model = ObjParser.parse(file.getAbsolutePath());

            uiHandler.getViewerPanel().setModel(model);
            fileNameLabel.setText(file.getName());

        } 
        catch (Exception e) {
            fileNameLabel.setText("Failed to load file");
            e.printStackTrace();
        }
    }

    private void handleResetFile() {
        uiHandler.getViewerPanel().reset();
    }

    // private void handleWireFrame() {
    // }

    public Button getOpenButton() {
        return this.openButton;
    }

    public Button getResetButton() {
        return this.resetButton;
    }

    // public CheckBox getWireframeCheckBox() {
    //     return this.wireframeCheckBox;
    // }

    public Label getFilenameLabel() {
        return this.fileNameLabel;
    }

    public void setFileName(String fileName) {
        this.fileNameLabel.setText(fileName);
    }

}
