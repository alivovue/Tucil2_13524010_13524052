package backend;

import backend.data.*;
import backend.filesystem.*;
import backend.logic.*;

import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class CLIMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String path = null;
        int maxDepth = -1;
        String outputPath = null;
        Model model = null;

        try {
            while (model == null) {
                System.out.println("Masukkan file path : ");
                path = scanner.nextLine();

                try {
                    model = ObjParser.parse(path);

                    if (model.getVertices().isEmpty() || model.getFaces().isEmpty()) {
                        System.out.println("Model kosong atau tidak valid. Silakan coba lagi.\n");
                        model = null;
                    }
                } 
                catch (Exception e) {
                    System.out.println("File input tidak valid: " + e.getMessage());
                    System.out.println("Silakan masukkan path lagi.\n");
                }
            }

            while (maxDepth < 0) {
                System.out.println("Masukkan max depth : ");
                String depthInput = scanner.nextLine();

                try {
                    maxDepth = Integer.parseInt(depthInput);

                    if (maxDepth < 0) {
                        System.out.println("Max depth tidak boleh negatif.\n");
                    }
                } 
                catch (NumberFormatException e) {
                    System.out.println("Max depth harus berupa integer.\n");
                }
            }

            while (outputPath == null || outputPath.isBlank()) {
                System.out.println("Masukkan nama file output (.obj) : ");
                outputPath = scanner.nextLine();

                if (outputPath.isBlank()) {
                    System.out.println("Nama file output tidak boleh kosong.\n");
                }
            }

            long startTime = System.nanoTime();

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
        catch (Exception e) {
            System.out.println("Terjadi error: " + e.getMessage());
            e.printStackTrace();
        } 
        finally {
            scanner.close();
        }
    }
}