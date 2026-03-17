package backend.data;

import java.util.List;
import java.util.ArrayList;

public class Model {
    List<Vertex> vertices;
    List<Face> faces;

    public Model() {
        this.vertices = new ArrayList<>();
        this.faces = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public void addFace(Face face) {
        this.faces.add(face);
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public Vertex getVertexByIndex(int idx) {
        return vertices.get(idx);
    }

    public Face getFaceByIndex(int idx) {
        return faces.get(idx);
    }
}
