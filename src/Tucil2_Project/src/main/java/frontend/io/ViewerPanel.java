package frontend.io;

import backend.data.Model;
import backend.data.Vertex;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.ArrayList;
import frontend.render.*;


public class ViewerPanel extends Canvas {
    private Model model;
    private Camera camera;
    private RenderEngine renderEngine;
    private double modelCenterX;
    private double modelCenterY;
    private double modelCenterZ;
    private double modelScale;
    // private boolean showWireFrame;
    private GraphicsContext graphicsContext;

    public ViewerPanel() {
        super(800, 600);
        this.model = null;
        this.camera = new Camera();
        this.renderEngine = new RenderEngine();
        this.modelCenterX = 0.0;
        this.modelCenterY = 0.0;
        this.modelCenterZ = 0.0;
        this.modelScale = 1.0;
        // this.showWireFrame = true;
        this.graphicsContext = getGraphicsContext2D();
        redraw();
    }

    public void setModel(Model model) {
        this.model = model;

        if (this.model != null) {
            computeModelBounds();
        }

        redraw();
    }

    public void redraw() {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, getWidth(), getHeight());
        graphicsContext.setStroke(Color.WHITE);

        if (model == null) {
            return;
        }

        RenderModel renderModel = new RenderModel(model, modelCenterX, modelCenterY, modelCenterZ, modelScale);

        List<Line2D> lines = renderEngine.render(renderModel, camera, getWidth(), getHeight());

        for (Line2D line : lines) {
            graphicsContext.strokeLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
        }
    }

    public void reset() {
        camera.reset();
        redraw();
    }

    public void computeModelBounds() {
        if (model == null || model.getVertices().isEmpty()) {
            modelCenterX = 0.0;
            modelCenterY = 0.0;
            modelCenterZ = 0.0;
            modelScale = 1.0;
            return;
        }

        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;

        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;

        for (Vertex vertex : model.getVertices()) {
            double x = vertex.getX();
            double y = vertex.getY();
            double z = vertex.getZ();

            if (x < minX) {
                minX = x;
            }
            if (y < minY) {
                minY = y;
            }
            if (z < minZ) {
                minZ = z;
            }

            if (x > maxX) {
                maxX = x;
            }
            if (y > maxY) {
                maxY = y;
            }
            if (z > maxZ) {
                maxZ = z;
            }
        }

        modelCenterX = (minX + maxX) / 2.0;
        modelCenterY = (minY + maxY) / 2.0;
        modelCenterZ = (minZ + maxZ) / 2.0;

        double sizeX = maxX - minX;
        double sizeY = maxY - minY;
        double sizeZ = maxZ - minZ;

        double maxSize = Math.max(sizeX, Math.max(sizeY, sizeZ));

        if (maxSize <= 0.0001) {
            modelScale = 1.0;
        } 
        else {
            modelScale = (Math.min(getWidth(), getHeight()) * 0.4) / maxSize;
        }
    }

    public Camera getCamera() {
        return this.camera;
    }
}
