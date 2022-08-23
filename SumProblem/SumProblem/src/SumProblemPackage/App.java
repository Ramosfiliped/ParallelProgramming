package SumProblemPackage;

import java.util.Random;

public class App {
    public static void main(String[] args) {
        Random random = new Random();
        int[] nums = new int[10000000];

        for (int i = 0; i < 10000000; i++)
            nums[i] = random.nextInt(100);

        long start, end, sum;

        // Sequential Algorithm
        // SequentialSum sequential = new SequentialSum();
        // start = System.currentTimeMillis();
        // sum = sequential.sum(nums);
        // end = System.currentTimeMillis();
        // System.out.println(("Result: " + sum + "\nTime: " + (end - start)));

        // Parallel Algorithm
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        ParallelSum parallel = new ParallelSum(numberOfThreads);
        start = System.currentTimeMillis();
        sum = parallel.sum(nums);
        end = System.currentTimeMillis();
        System.out.println("Result: " + sum + "\nTime: " + (end - start));
    }
}
