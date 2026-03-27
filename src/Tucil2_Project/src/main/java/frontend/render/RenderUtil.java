package frontend.render;

import backend.data.Vertex;
import frontend.io.*;
import javafx.geometry.Point2D;

public class RenderUtil {
    public static Vertex translateVertex(Vertex v, double dx, double dy, double dz) {
        return new Vertex(v.getX() + dx, v.getY() + dy, v.getZ() + dz);
    }

    public static Vertex scale(Vertex v, double scaling) {
        return new Vertex(v.getX() * scaling, v.getY() * scaling, v.getZ() * scaling);
    }

    public static Vertex centerVertex(Vertex v, double centerX, double centerY, double centerZ) {
        return new Vertex(v.getX() - centerX, v.getY() - centerY, v.getZ() - centerZ);
    }

    // all rotates uses radians, not degrees
    public static Vertex rotateX(Vertex v, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double y = v.getY() * cos - v.getZ() * sin;
        double z = v.getY() * sin + v.getZ() * cos;

        return new Vertex(v.getX(), y, z);
    }

    public static Vertex rotateY(Vertex v, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double x = v.getX() * cos + v.getZ() * sin;
        double z = -v.getX() * sin + v.getZ() * cos;

        return new Vertex(x, v.getY(), z);
    }

    public static Vertex rotateZ(Vertex v, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double x = v.getX() * cos - v.getY() * sin;
        double y = v.getX() * sin + v.getY() * cos;

        return new Vertex(x, y, v.getZ());
    }

    public static Vertex worldToCamera(Vertex v, Camera camera) {
        Vertex rotateVertex = rotateY(v, camera.getYaw());
        rotateVertex = rotateX(rotateVertex, camera.getPitch());
        return new Vertex(rotateVertex.getX(), rotateVertex.getY(), rotateVertex.getZ() + 500);
    }

    public static boolean isProjectable(Vertex v) {
        return v.getZ() > 0.0001;
    }

    public static Point2D project(Vertex v, double focalLength, double width, double height) {
        if (!isProjectable(v)) {
            return null;
        }

        double projectX = (v.getX() * focalLength) / v.getZ();
        double projectY = (v.getY() * focalLength) / v.getZ();

        double resultX = projectX + width / 2.0;
        double resultY = -projectY + height / 2.0;

        return new Point2D(resultX, resultY);
    }
}
