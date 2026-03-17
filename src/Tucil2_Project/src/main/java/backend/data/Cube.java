package backend.data;

import java.util.List;

public class Cube {
    double minX;
    double minY;
    double minZ;
    double size; // ukuran panjang / rusuk

    public Cube(double minX, double minY, double minZ, double size) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.size = size;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMinZ() {
        return minZ;
    }

    public double getSize() {
        return size;
    }

    // special getters

    public double getMaxX() {
        return minX + size;
    }

    public double getMaxY() {
        return minY + size;
    }

    public double getMaxZ() {
        return minZ + size;
    }

    public double getCenterX() {
        return minX + size/2.0;
    }

    public double getCenterY() {
        return minY + size/2.0;
    }

    public double getCenterZ() {
        return minZ + size/2.0;
    }

    // special functions
    public Cube[] split() {
        double splitSize = size/2.0;
        Cube[] childCubes = new Cube[8];
        int idx = 0;
        for (int i = 0 ; i < 2 ; i++) {
            for (int j = 0 ; j < 2 ; j++) {
                for (int k = 0 ; k < 2 ; k++) {
                    childCubes[idx++] = new Cube(
                        minX + i * splitSize, 
                        minY + j * splitSize,
                        minZ + k * splitSize,
                        splitSize
                    );
                }
            }
        }
        return childCubes;
    }

}