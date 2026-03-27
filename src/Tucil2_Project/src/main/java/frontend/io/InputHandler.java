package frontend.io;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class InputHandler {
    private ViewerPanel viewerPanel;
    private double lastMouseX;
    private double lastMouseY;
    private boolean drag;
    private double rotateSensitivity;
    private double zoomSensitivity;

    public InputHandler(ViewerPanel viewerPanel) {
        this.viewerPanel = viewerPanel;
        this.lastMouseX = 0.0;
        this.lastMouseY = 0.0;
        this.drag = false;
        this.rotateSensitivity = 0.01;
        this.zoomSensitivity = 0.1;
    }

    public void attachHandlers() {
        viewerPanel.setFocusTraversable(true);
        viewerPanel.setOnMousePressed(e -> handleMousePressed(e));
        viewerPanel.setOnMouseDragged(e -> handleMouseDragged(e));
        viewerPanel.setOnMouseReleased(e -> drag = false);
        viewerPanel.setOnScroll(e -> handleScroll(e));
        viewerPanel.setOnKeyPressed(e -> handleKeyPressed(e));
        viewerPanel.setOnMouseClicked(e -> viewerPanel.requestFocus());
    }

    private void handleMousePressed(MouseEvent e) {
        lastMouseX = e.getX();
        lastMouseY = e.getY();
        drag = true;
        viewerPanel.requestFocus();
    }

    private void handleMouseDragged(MouseEvent e) {
        if (!drag) {
            return;
        }

        double currentMouseX = e.getX();
        double currentMouseY = e.getY();
        double deltaX = currentMouseX - lastMouseX;
        double deltaY = currentMouseY - lastMouseY;
        Camera camera = viewerPanel.getCamera();
        camera.rotate(-deltaX * rotateSensitivity, -deltaY * rotateSensitivity);

        lastMouseX = currentMouseX;
        lastMouseY = currentMouseY;

        viewerPanel.redraw();
    }

    private void handleScroll(ScrollEvent e) {
        Camera camera = viewerPanel.getCamera();
        if (e.getDeltaY() > 0) {
            camera.zoom(zoomSensitivity);
        }
        else if (e.getDeltaY() < 0) {
            camera.zoom(-zoomSensitivity);
        }

        viewerPanel.redraw();
    }

    private void handleKeyPressed(KeyEvent e) {
        Camera camera = viewerPanel.getCamera();
        if (e.getCode() == KeyCode.R) {
            viewerPanel.reset();
        } 
        else if (e.getCode() == KeyCode.UP) {
            camera.rotate(0, -rotateSensitivity * 10);
            viewerPanel.redraw();
        } 
        else if (e.getCode() == KeyCode.DOWN) {
            camera.rotate(0, rotateSensitivity * 10);
            viewerPanel.redraw();
        } 
        else if (e.getCode() == KeyCode.LEFT) {
            camera.rotate(-rotateSensitivity * 10, 0);
            viewerPanel.redraw();
        } 
        else if (e.getCode() == KeyCode.RIGHT) {
            camera.rotate(rotateSensitivity * 10, 0);
            viewerPanel.redraw();
        }
    }
}
