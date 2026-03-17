package backend.filesystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import backend.data.*;


public class ObjParser {
    public static Model parse(String path) throws IOException {
        Model model = new Model();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            if (isValidVertex(line)) {
                model.addVertex(parseVertex(line));
            }
            else if (isValidFace(line)) {
                model.addFace(parseFace(line));
            }
        }

        bufferedReader.close();
        return model;
    }

    private static Vertex parseVertex(String line) {
        String[] words = line.trim().split("\\s+");
        double x = Double.parseDouble(words[1]);
        double y = Double.parseDouble(words[2]);
        double z = Double.parseDouble(words[3]);
        return new Vertex(x, y, z);
    }

    private static Face parseFace(String line) {
        String[] words = line.trim().split("\\s+");
        int v1 = Integer.parseInt(words[1]);
        int v2 = Integer.parseInt(words[2]);
        int v3 = Integer.parseInt(words[3]);
        return new Face(v1, v2, v3);
    }

    private static boolean isValidVertex(String line) {
        // TODO : complete possible additions to is valid checking
        String[] words = line.trim().split("\\s+");
        return words.length == 4 && words[0].equals("v");
    }

    public static boolean isValidFace(String line) {
        String[] parts = line.trim().split("\\s+");
        return parts.length == 4 && parts[0].equals("f");
    }


}
