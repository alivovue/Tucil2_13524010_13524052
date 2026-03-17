package backend.filesystem;

import backend.data.Cube;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ObjWriter {

    public static String write(List<Cube> voxels, String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));

        for (Cube cube : voxels) {
            double minX = cube.getMinX();
            double minY = cube.getMinY();
            double minZ = cube.getMinZ();
            double maxX = cube.getMaxX();
            double maxY = cube.getMaxY();
            double maxZ = cube.getMaxZ();

            writer.write("v " + minX + " " + minY + " " + minZ); 
            writer.newLine();
            writer.write("v " + maxX + " " + minY + " " + minZ); 
            writer.newLine();
            writer.write("v " + maxX + " " + maxY + " " + minZ); 
            writer.newLine();
            writer.write("v " + minX + " " + maxY + " " + minZ); 
            writer.newLine();
            writer.write("v " + minX + " " + minY + " " + maxZ); 
            writer.newLine();
            writer.write("v " + maxX + " " + minY + " " + maxZ); 
            writer.newLine();
            writer.write("v " + maxX + " " + maxY + " " + maxZ); 
            writer.newLine();
            writer.write("v " + minX + " " + maxY + " " + maxZ); 
            writer.newLine();
        }

        for (int i = 0; i < voxels.size(); i++) {
            int base = i * 8 + 1;

            writer.write("f " + (base + 0) + " " + (base + 1) + " " + (base + 2)); 
            writer.newLine();
            writer.write("f " + (base + 0) + " " + (base + 2) + " " + (base + 3)); 
            writer.newLine();

            writer.write("f " + (base + 4) + " " + (base + 5) + " " + (base + 6)); 
            writer.newLine();
            writer.write("f " + (base + 4) + " " + (base + 6) + " " + (base + 7)); 
            writer.newLine();

            writer.write("f " + (base + 0) + " " + (base + 1) + " " + (base + 5)); 
            writer.newLine();
            writer.write("f " + (base + 0) + " " + (base + 5) + " " + (base + 4)); 
            writer.newLine();

            writer.write("f " + (base + 1) + " " + (base + 2) + " " + (base + 6)); 
            writer.newLine();
            writer.write("f " + (base + 1) + " " + (base + 6) + " " + (base + 5)); 
            writer.newLine();

            writer.write("f " + (base + 2) + " " + (base + 3) + " " + (base + 7)); 
            writer.newLine();
            writer.write("f " + (base + 2) + " " + (base + 7) + " " + (base + 6)); 
            writer.newLine();

            writer.write("f " + (base + 3) + " " + (base + 0) + " " + (base + 4)); 
            writer.newLine();
            writer.write("f " + (base + 3) + " " + (base + 4) + " " + (base + 7)); 
            writer.newLine();
        }

        writer.close();
        return path;
    }
}