package backend.data;

import java.util.List;
import java.util.ArrayList;

public class OctreeResult {
    List<Cube> voxels;
    int[] nodeCreatedPerDepth;
    int[] skippedNodesPerDepth;

    public OctreeResult(int maxDepth) {
        this.voxels = new ArrayList<>();
        this.nodeCreatedPerDepth = new int[maxDepth + 1];
        this.skippedNodesPerDepth = new int[maxDepth + 1];
    }

    public void addVoxel(Cube cube) {
        voxels.add(cube);
    }

    public void incrementCreated(int depth) {
        nodeCreatedPerDepth[depth]++;
    }

    public void incrementSkipped(int depth) {
        skippedNodesPerDepth[depth]++;
    }

    public List<Cube> getVoxels() {
        return voxels;
    }

    public int[] getNodeCreatedPerDepth() {
        return nodeCreatedPerDepth;
    }

    public int[] getSkippedNodesPerDepth() {
        return skippedNodesPerDepth;
    }

    public void mergeResult(OctreeResult octreeResult) {
        this.voxels.addAll(octreeResult.voxels);

        for (int i = 0; i < this.nodeCreatedPerDepth.length; i++) {
            this.nodeCreatedPerDepth[i] += octreeResult.nodeCreatedPerDepth[i];
            this.skippedNodesPerDepth[i] += octreeResult.skippedNodesPerDepth[i];
        }
    }
}
