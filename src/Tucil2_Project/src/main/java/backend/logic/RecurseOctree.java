package backend.logic;

import backend.data.*;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.ArrayList;

public class RecurseOctree extends RecursiveTask<OctreeResult> {
    OctreeTask octreeTask;
    Model model;

    public RecurseOctree(OctreeTask octreeTask, Model model) {
        this.octreeTask = octreeTask;
        this.model = model;
    }

    @Override
    protected OctreeResult compute() {
        OctreeResult octreeResult = new OctreeResult(octreeTask.getMaxDepth());
        octreeResult.incrementCreated(octreeTask.getDepth());

        if (!CalculatorUtil.isIntersectModel(model, octreeTask.getCube())) {
            octreeResult.incrementSkipped(octreeTask.getDepth());
            return octreeResult;
        }

        if (octreeTask.getDepth() == octreeTask.getMaxDepth()) {
            octreeResult.addVoxel(octreeTask.getCube());
            return octreeResult;
        }

        Cube[] childCubes = octreeTask.getCube().split();
        List<RecurseOctree> tasks = new ArrayList<>();

        for (Cube cube : childCubes) {
            tasks.add(new RecurseOctree(new OctreeTask(cube, octreeTask.getDepth() + 1, octreeTask.getMaxDepth(), model), model));
        }

        for (int i = 0 ; i < 7 ; i++) {
            tasks.get(i).fork();
        }

        OctreeResult totalResult = tasks.get(7).compute();

        for (int i = 0 ; i < 7 ; i++) {
            totalResult.mergeResult(tasks.get(i).join());
        }

        octreeResult.mergeResult(totalResult);
        return octreeResult;



    }
}
