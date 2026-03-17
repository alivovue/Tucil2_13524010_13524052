package backend;

import backend.data.*;
import backend.filesystem.*;
import backend.logic.*;
import backend.filesystem.ObjParser;

import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class CLIMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Masukkan file path : ");
            String path = scanner.nextLine();

            System.out.println("Masukkan max depth : ");
            int maxDepth = Integer.parseInt(scanner.nextLine());

            System.out.println("Masukkan nama file output (.obj) : ");
            String outputPath = scanner.nextLine();

            long startTime = System.nanoTime();

            Model model = ObjParser.parse(path);

            if (model.getVertices().isEmpty() || model.getFaces().isEmpty()) {
                System.out.println("Model kosong atau tidak valid");
                return;
            }

            Cube rootCube = CalculatorUtil.buildRootCube(model);

            OctreeTask rootDataTask = new OctreeTask(rootCube, 0, maxDepth, model);
            RecurseOctree recurseOctree = new RecurseOctree(rootDataTask, model);

            ForkJoinPool pool = new ForkJoinPool();
            OctreeResult octreeResult = pool.invoke(recurseOctree);
            pool.shutdown();

            ObjWriter.write(octreeResult.getVoxels(), outputPath);

            long endTime = System.nanoTime();
            double totalTime = (endTime - startTime) / 1_000_000.0;

            int countVoxel = octreeResult.getVoxels().size();
            int countVertex = countVoxel * 8;
            int countFace = countVoxel * 12;

            System.out.println();
            System.out.println("Banyak voxel yang terbentuk: " + countVoxel);
            System.out.println("Banyak vertex yang terbentuk: " + countVertex);
            System.out.println("Banyak faces yang terbentuk: " + countFace);

            System.out.println("Statistik node octree yang terbentuk:");
            for (int i = 0; i <= maxDepth; i++) {
                System.out.println(i + " : " + octreeResult.getNodeCreatedPerDepth()[i]);
            }

            System.out.println("Statistik node yang tidak perlu ditelusuri:");
            for (int i = 0; i <= maxDepth; i++) {
                System.out.println(i + " : " + octreeResult.getSkippedNodesPerDepth()[i]);
            }

            System.out.println("Kedalaman octree: " + maxDepth);
            System.out.println("Lama waktu program berjalan: " + totalTime + " ms");

            System.out.println("Path file output: " + outputPath);

        } 
        catch (NumberFormatException e) {
            System.out.println("Max depth harus berupa integer.");
        } 
        catch (Exception e) {
            System.out.println("Terjadi error: " + e.getMessage());
            e.printStackTrace();
        } 
        finally {
            scanner.close();
        }
    }
}