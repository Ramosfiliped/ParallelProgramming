package MergeSortPackage;

import java.util.*;
import java.util.concurrent.*;

public class ParallelMergeSort extends RecursiveTask<List<Integer>>{

    private List<Integer> elements;

    public ParallelMergeSort(List<Integer> elements){
        this.elements = elements;
    }

    @Override
    protected List<Integer> compute() {
        if(this.elements.size() <= 1){
            return this.elements;
        }
        else{
            final int pivot = this.elements.size() / 2;
            ParallelMergeSort leftTask = new ParallelMergeSort(this.elements.subList(0, pivot));
            ParallelMergeSort rightTask = new ParallelMergeSort(this.elements.subList(pivot, this.elements.size()));

            leftTask.fork();
            rightTask.fork();

            List<Integer> left = leftTask.join();
            List<Integer> right = rightTask.join();

            merge(left, right);
            return this.elements;
        }

    }

    private void merge(List<Integer> left, List<Integer> right) {
        int leftIndex = 0;
        int rightIndex = 0;
        while(leftIndex < left.size() ) {
            if(rightIndex == 0) {
                if( left.get(leftIndex).compareTo(right.get(rightIndex)) > 0 ) {
                    swap(left, leftIndex++, right, rightIndex++);
                } else {
                    leftIndex++;
                }
            } else {
                if(rightIndex >= right.size()) {
                    if(right.get(0).compareTo(left.get(left.size() - 1)) < 0 )
                        merge(left, right);
                    else
                        return;
                }
                else if( right.get(0).compareTo(right.get(rightIndex)) < 0 ) {
                    swap(left, leftIndex++, right, 0);
                } else {
                    swap(left, leftIndex++, right, rightIndex++);
                }
            }
        }

        if(rightIndex < right.size() && rightIndex != 0)
            merge(right.subList(0, rightIndex), right.subList(rightIndex, right.size()));
    }

    private void swap(List<Integer> left, int leftIndex, List<Integer> right, int rightIndex) {
        //N leftElement = left.get(leftIndex);
        left.set(leftIndex, right.set(rightIndex, left.get(leftIndex)));
    }

    public static void main(String[] args) {
        int processors = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(processors);
        List<Integer> result = forkJoinPool.invoke(new ParallelMergeSort(new ArrayList<>(Arrays.asList(5,9,8,7,6,1,2,3,4, 10, 15, 12, 11, 13, 14))));
        System.out.println("result: " + result);
    }
}
