package frontend.io;

import backend.data.Model;
import backend.data.Vertex;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;
import frontend.render.*;

public class ViewerPanel extends Canvas {
    private Model model;
    private Camera camera;
    private RenderEngine renderEngine;
    private double modelCenterX;
    private double modelCenterY;
    private double modelCenterZ;
    private double modelScale;
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
        this.graphicsContext = getGraphicsContext2D();

        widthProperty().addListener((obs, oldVal, newVal) -> redraw());
        heightProperty().addListener((obs, oldVal, newVal) -> redraw());

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
        double width = getWidth();
        double height = getHeight();

        drawBackground(width, height);
        drawFrame(width, height);

        if (model == null) {
            drawEmptyState(width, height);
            return;
        }

        RenderModel renderModel = new RenderModel(
            model,
            modelCenterX,
            modelCenterY,
            modelCenterZ,
            modelScale
        );

        List<Line2D> lines = renderEngine.render(renderModel, camera, width, height);

        graphicsContext.setStroke(Color.web("#F5F5F5"));
        graphicsContext.setLineWidth(1.1);

        for (Line2D line : lines) {
            graphicsContext.strokeLine(
                line.getX1(),
                line.getY1(),
                line.getX2(),
                line.getY2()
            );
        }

        drawOverlay(width, height);
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

            if (x < minX) minX = x;
            if (y < minY) minY = y;
            if (z < minZ) minZ = z;

            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;
            if (z > maxZ) maxZ = z;
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

    private void drawBackground(double width, double height) {
        graphicsContext.setFill(Color.web("#111315"));
        graphicsContext.fillRect(0, 0, width, height);

        graphicsContext.setFill(Color.web("#15181C"));
        graphicsContext.fillRect(8, 8, width - 16, height - 16);
    }

    private void drawFrame(double width, double height) {
        graphicsContext.setStroke(Color.web("#2A2F36"));
        graphicsContext.setLineWidth(2.0);
        graphicsContext.strokeRect(1, 1, width - 2, height - 2);

        graphicsContext.setStroke(Color.web("#3A4048"));
        graphicsContext.setLineWidth(1.0);
        graphicsContext.strokeRect(8, 8, width - 16, height - 16);
    }

    private void drawEmptyState(double width, double height) {
        graphicsContext.setFill(Color.web("#9AA4B2"));
        graphicsContext.setLineWidth(1.0);
        graphicsContext.fillText("No model loaded", width / 2 - 45, height / 2 - 5);

        graphicsContext.setFill(Color.web("#6B7280"));
        graphicsContext.fillText("Click Open to load an OBJ file", width / 2 - 78, height / 2 + 18);
    }

    private void drawOverlay(double width, double height) {
        graphicsContext.setStroke(Color.color(1, 1, 1, 0.05));
        graphicsContext.setLineWidth(1.0);
        graphicsContext.strokeLine(10, 10, width - 10, 10);
    }

    public Camera getCamera() {
        return this.camera;
    }
}