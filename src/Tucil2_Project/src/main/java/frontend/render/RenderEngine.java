package frontend.render;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.data.*;

import frontend.io.Camera;
import javafx.geometry.Point2D;

public class RenderEngine {
    public List<Line2D> render(RenderModel renderModel, Camera camera, double width, double height) {
        List<Line2D> renderedResult = new ArrayList<>();
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

        Set<EdgeKey> drawnEdges = new HashSet<>();

        for (Face face : faces) {
            int v1 = face.getV1() - 1;
            int v2 = face.getV2() - 1;
            int v3 = face.getV3() - 1;

            addEdgeIfVisible(v1, v2, projectionList, drawnEdges, renderedResult);
            addEdgeIfVisible(v1, v3, projectionList, drawnEdges, renderedResult);
            addEdgeIfVisible(v2, v3, projectionList, drawnEdges, renderedResult);
        }

        return renderedResult;
    }

    private void addEdgeIfVisible(int v1, int v2, List<Point2D> projectionList, Set<EdgeKey> drawnEdges, List<Line2D> renderedResult) {
        EdgeKey edgeKey = new EdgeKey(v1, v2);

        if (drawnEdges.contains(edgeKey)) {
            return;
        }

        Point2D p1 = projectionList.get(v1);
        Point2D p2 = projectionList.get(v2);

        if (p1 == null || p2 == null) {
            return;
        }

        drawnEdges.add(edgeKey);
        renderedResult.add(new Line2D(p1.getX(), p1.getY(), p2.getX(), p2.getY()));
    }
}