package frontend.render;

import java.util.List;
import java.util.ArrayList;
import backend.data.*;

import frontend.io.Camera;
import javafx.geometry.Point2D;

public class RenderEngine {
    public List<Line2D> render(RenderModel renderModel, Camera camera, double width, double height) {
        List<Line2D> renderedResult = new ArrayList<>();
        Model model = renderModel.getModel();
        List<Vertex> vertices = renderModel.getModel().getVertices();
        List<Face> faces = renderModel.getModel().getFaces();

        List<Point2D> projectionList = new ArrayList<>();

        double focalLength = Math.min(width, height) * camera.getZoom();

        for (Vertex vertex : vertices) {
            Vertex centerVertex = RenderUtil.centerVertex(vertex, renderModel.getCenterX(), renderModel.getCenterY(), renderModel.getCenterZ());
            Vertex scaleVertex = RenderUtil.scale(centerVertex, renderModel.getScale());
            Vertex cameraVertex = RenderUtil.worldToCamera(scaleVertex, camera);
            Point2D projectVertex = RenderUtil.project(cameraVertex, focalLength, width, height);
            projectionList.add(projectVertex);
        }

        for (Face face : faces) {
            int v1 = face.getV1() - 1;
            int v2 = face.getV2() - 1;
            int v3 = face.getV3() - 1;

            Point2D point1 = projectionList.get(v1);
            Point2D point2 = projectionList.get(v2);
            Point2D point3 = projectionList.get(v3);

            if (point1 != null && point2 != null && point3 != null) {
                renderedResult.add(new Line2D(point1.getX(), point1.getY(), point2.getX(), point2.getY()));
                renderedResult.add(new Line2D(point1.getX(), point1.getY(), point3.getX(), point3.getY()));
                renderedResult.add(new Line2D(point2.getX(), point2.getY(), point3.getX(), point3.getY()));
            }
        }

        return renderedResult;
    }
}
