package com.globalsoftwaresupport;

import java.util.Arrays;

public class ParallelSum {
    private ParallelWorker[] workers;
    private int numOfThreads;

    public ParallelSum(int numOfThreads) {
        this.numOfThreads = numOfThreads;
        this.workers = new ParallelWorker[numOfThreads];
    }

    public int sum(int[] nums) {
        int size = (int) Math.ceil(nums.length * 1.0 / numOfThreads);
        for (int i = 0; i < this.numOfThreads; ++i) {
            workers[i] = new ParallelWorker(nums, i * size, (i + 1) * size);
            workers[i].start();
        }
        try {
            for (ParallelWorker worker : this.workers)
                worker.join();
        } catch (InterruptedException err) {
            err.printStackTrace();
        }
        int[] partialSums = new int[this.numOfThreads];
        int count = 0;
        for (ParallelWorker worker : this.workers) {
            if (count < this.numOfThreads) {
                partialSums[count] = worker.getPartialSum();
                count += 1;
            }
        }
        System.out.println(Arrays.toString(partialSums));
        if (this.numOfThreads >= 2) {
            this.numOfThreads = this.numOfThreads / 2;
            return this.sum(partialSums);
        }
        return partialSums[0];
    }
}
