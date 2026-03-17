package backend.logic;

import java.util.List;

import backend.data.*;

public class CalculatorUtil {

    // intersection check functions
    
    public static boolean isIntersectModel(Model model, Cube cube) {
        for (Face face : model.getFaces()) {
            Vertex v1 = model.getVertexByIndex(face.getV1()-1);
            Vertex v2 = model.getVertexByIndex(face.getV2()-1);
            Vertex v3 = model.getVertexByIndex(face.getV3()-1);

            if (!triangleBoxOverlapsCube(cube, v1, v2, v3)) {
                continue;
            }
            if (triangleIntersectsCube(cube, v1, v2, v3)) {
                return true;
            }
        }
        return false;
    }

    private static boolean overlap2D(double min1, double max1, double min2, double max2) {
        return max1 >= min2 && max2 >= min1;
    }

    private static boolean triangleBoxOverlapsCube(Cube cube, Vertex v1, Vertex v2, Vertex v3) {
        double triMinX = Math.min(v1.getX(), Math.min(v2.getX(), v3.getX()));
        double triMinY = Math.min(v1.getY(), Math.min(v2.getY(), v3.getY()));
        double triMinZ = Math.min(v1.getZ(), Math.min(v2.getZ(), v3.getZ()));

        double triMaxX = Math.max(v1.getX(), Math.max(v2.getX(), v3.getX()));
        double triMaxY = Math.max(v1.getY(), Math.max(v2.getY(), v3.getY()));
        double triMaxZ = Math.max(v1.getZ(), Math.max(v2.getZ(), v3.getZ()));
        
        return overlap2D(cube.getMinX(), cube.getMaxX(), triMinX, triMaxX) &&
            overlap2D(cube.getMinY(), cube.getMaxY(), triMinY, triMaxY) && 
            overlap2D(cube.getMinZ(), cube.getMaxZ(), triMinZ, triMaxZ);
    }

    private static boolean triangleIntersectsCube(Cube cube, Vertex v1, Vertex v2, Vertex v3) {
        if (isPointInsideCube(v1, cube) || isPointInsideCube(v2, cube) || isPointInsideCube(v3, cube)) {
            return true;
        }

        if (isLineIntersectCube(v1, v2, cube)) {
            return true;
        }
        if (isLineIntersectCube(v1, v3, cube)) {
            return true;
        }
        if (isLineIntersectCube(v2, v3, cube)) {
            return true;
        }

        return isPlaneIntersectCube(cube, v1, v2, v3);
    }

    private static boolean isPointInsideCube(Vertex v, Cube cube) {
        return v.getX() >= cube.getMinX() && v.getX() <= cube.getMaxX()
            && v.getY() >= cube.getMinY() && v.getY() <= cube.getMaxY()
            && v.getZ() >= cube.getMinZ() && v.getZ() <= cube.getMaxZ();
    }

    private static boolean isLineIntersectCube(Vertex v1, Vertex v2, Cube cube) {
        double tmin = 0.0;
        double tmax = 1.0;

        double[] point = {v1.getX(), v1.getY(), v1.getZ()};
        double[] distance = {v2.getX() - v1.getX(), v2.getY() - v1.getY(), v2.getZ() - v1.getZ()};
        double[] cubeMin = {cube.getMinX(), cube.getMinY(), cube.getMinZ()};
        double[] cubeMax = {cube.getMaxX(), cube.getMaxY(), cube.getMaxZ()};

        for (int i = 0 ; i < 3 ; i++) {
            if (Math.abs(distance[i]) < 1e-12) {
                if (point[i] < cubeMin[i] || point[i] > cubeMax[i]) {
                    return false;
                }
            }
            else {
                double t1 = (cubeMin[i] - point[i]) / distance[i];
                double t2 = (cubeMax[i] - point[i]) / distance[i];

                if (t1 > t2) {
                    double temp = t1;
                    t1 = t2;
                    t2 = temp;
                }

                tmin = Math.max(tmin, t1);
                tmax = Math.min(tmax, t2);

                if (tmin > tmax) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isPlaneIntersectCube(Cube cube, Vertex v1, Vertex v2, Vertex v3) {
        double v1v2x = v2.getX() - v1.getX();
        double v1v2y = v2.getY() - v1.getY();
        double v1v2z = v2.getZ() - v1.getZ();

        double v1v3x = v3.getX() - v1.getX();
        double v1v3y = v3.getY() - v1.getY();
        double v1v3z = v3.getZ() - v1.getZ();

        double nx = v1v2y * v1v3z - v1v2z * v1v3y;
        double ny = v1v2z * v1v3x - v1v2x * v1v3z;
        double nz = v1v2x * v1v3y - v1v2y * v1v3x;

        double d = -(nx * v1.getX() + ny * v1.getY() + nz * v1.getZ());

        double[][] corners = {
            {cube.getMinX(), cube.getMinY(), cube.getMinZ()},
            {cube.getMinX(), cube.getMinY(), cube.getMaxZ()},
            {cube.getMinX(), cube.getMaxY(), cube.getMinZ()},
            {cube.getMinX(), cube.getMaxY(), cube.getMaxZ()},
            {cube.getMaxX(), cube.getMinY(), cube.getMinZ()},
            {cube.getMaxX(), cube.getMinY(), cube.getMaxZ()},
            {cube.getMaxX(), cube.getMaxY(), cube.getMinZ()},
            {cube.getMaxX(), cube.getMaxY(), cube.getMaxZ()}
        };

        boolean hasPositive = false;
        boolean hasNegative = false;

        for (double[] corner : corners) {
            double value = nx * corner[0] + ny * corner[1] + nz * corner[2] + d;
            if (value > 1e-12) hasPositive = true;
            if (value < -1e-12) hasNegative = true;
        }

        return hasPositive && hasNegative;
    }

    // main cube

    static public Cube buildRootCube(Model model) {
        List<Vertex> vertices = model.getVertices();

        double minX = vertices.get(0).getX();
        double minY = vertices.get(0).getY();
        double minZ = vertices.get(0).getZ();

        double maxX = vertices.get(0).getX();
        double maxY = vertices.get(0).getY();
        double maxZ = vertices.get(0).getZ();

        for (Vertex v : vertices) {
            minX = Math.min(minX, v.getX());
            minY = Math.min(minY, v.getY());
            minZ = Math.min(minZ, v.getZ());

            maxX = Math.max(maxX, v.getX());
            maxY = Math.max(maxY, v.getY());
            maxZ = Math.max(maxZ, v.getZ());
        }

        double sizeX = maxX - minX;
        double sizeY = maxY - minY;
        double sizeZ = maxZ - minZ;

        double size = Math.max(sizeX, Math.max(sizeY, sizeZ));

        return new Cube(minX, minY, minZ, size);
    }

}