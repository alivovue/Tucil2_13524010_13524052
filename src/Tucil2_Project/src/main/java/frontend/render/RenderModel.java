package frontend.render;

import backend.data.Model;

public class RenderModel {
    private Model model;
    private double centerX;
    private double centerY;
    private double centerZ;
    private double scale;

    public RenderModel(Model model, double centerX, double centerY, double centerZ, double scale) {
        this.model = model;
        this.centerX = centerX;
        this.centerY = centerY;
        this.centerZ = centerZ;
        this.scale = scale;
    }

    public Model getModel() {
        return model;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getCenterZ() {
        return centerZ;
    }

    public double getScale() {
        return scale;
    }
}