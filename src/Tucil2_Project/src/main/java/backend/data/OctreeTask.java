package backend.data;

public class OctreeTask {
    Cube cube;
    int depth;
    int maxDepth;
    Model model;

    public OctreeTask(Cube cube, int depth, int maxDepth, Model model) {
        this.cube = cube;
        this.depth = depth;
        this.maxDepth = maxDepth;
        this.model = model;
    }

    public Cube getCube() {
        return cube;
    }

    public int getDepth() {
        return depth;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public Model model() {
        return model;
    }
}
