package com.rex.demo.practicetest;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolPractice extends RecursiveTask<Integer> {
    private static final long serialVersionUID = 1L;
    private static final int THRESHOLD = 49;
    private int start;
    private int end;

    public ForkJoinPoolPractice(int start, int end) {
        this.start = start;
        this.end = end;
    }


    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            int result = 0;
            for (int i = start; i <= end; i++) {
                result += i;
            }
            return result;
        } else {
            int middle = (start + end) / 2;
            ForkJoinPoolPractice firstTask = new ForkJoinPoolPractice(start, middle);
            ForkJoinPoolPractice secondTask = new ForkJoinPoolPractice(middle + 1, end);
            invokeAll(firstTask,secondTask);
            return firstTask.join() + secondTask.join();
        }
    }

    public static void main(String[] args) throws Exception{
        testHasResultTask();
    }

    public static void testHasResultTask() throws Exception {
        int result1 = 0;
        for (int i = 1; i <= 1000000; i++) {
            result1 += i;
        }
        System.out.println("循环计算 1-1000000 累加值：" + result1);

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> task = pool.submit(new ForkJoinPoolPractice(1, 1000000));
        int result2 = task.get();
        System.out.println("并行计算 1-1000000 累加值：" + result2);
        pool.awaitTermination(2, TimeUnit.SECONDS);
        pool.shutdown();
    }
}
