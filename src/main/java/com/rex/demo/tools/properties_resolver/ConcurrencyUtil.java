package com.rex.demo.tools.properties_resolver;

import java.util.concurrent.CompletableFuture;


/**
 * java concurrent介紹
 */
public class ConcurrencyUtil {


    public static void main(String[] args) {
        Thread workerThread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());

//            completableFutureTest();
//            completableFutureTest02();
//            completableFutureTest03();
//            completableFutureTest04();
//            completableFutureTest05();
//            completableFutureTest06();
            for (int i = 0; i < 10; i++) {
                completableFutureTest20();
            }
        });
        workerThread.start();

        try {
            // 等待工作线程执行完毕
            workerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Worker thread has finished. Exiting main thread.");
    }

    /**
     * 创建一个简单的异步任务
     */
    private static void completableFutureTest() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // 异步执行的代码
            return "Hello, CompletableFuture!";
        });

        // 当任务完成时，处理结果
        future.thenAccept(result -> {
            System.out.println("Result: " + result);
        });
    }

    /**
     * 组合多个任务
     */
    private static void completableFutureTest02() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int num1 = getNum(100);
            return num1;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int num2 = getNum(200);
            return num2;
        });

        CompletableFuture<Integer> combinedFuture = future1.thenCombine(future2, (result1, result2) -> result1 + result2);

        combinedFuture.thenAccept(result -> {
            System.out.println("Combined Result: " + result);
        });
    }

    private static int getNum(int x) {
        int num2 = 0;
        for (int i = 0; i < x; i++) {
            num2++;
        }
        return num2;
    }

    /**
     * 组合多个任务
     */
    private static void completableFutureTest03() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            // 任务1
            return getNum(10000000);
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            // 任务2
            return getNum(10000000);
        });

        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            // 任务3
            return getNum(10000000);
        });

        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2, future3);

        allOf.thenRun(() -> {
            // 所有任务完成后执行此处的代码
            int result1 = future1.join(); // 获取任务1的结果
            int result2 = future2.join(); // 获取任务2的结果
            int result3 = future3.join(); // 获取任务3的结果
            int total = result1 + result2 + result3;
            System.out.println("Total: " + total);
        });
    }

    private static void completableFutureTest04() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            // 任务1
            return 42;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            // 任务2
            return 100;
        });

        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(future1, future2);

        anyOf.thenAccept(result -> {
            System.out.println("Any task completed with result: " + result);
        });
    }

    public static int staticNum = 0;

    /**
     * 组合多个任务
     */
    private static void completableFutureTest05() {
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            // 任务1
            staticNumCount(10000000);
        });
        future1.join();
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            // 任务2
            staticNumCount(10000000);
        });
        future2.join();
        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
            // 任务3
            staticNumCount(10000000);
        });
        future3.join();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2, future3);

        if (future1.isDone() && future2.isDone() && future3.isDone()) {
            allOf.thenRun(() -> System.out.println("Total: " + staticNum));
        }
    }

    private static void completableFutureTest06() {
        // 记录程序开始时间
        long startTime = System.currentTimeMillis();
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            // 任务1
            staticNumCount(10);
        });
        future1.join();
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            // 任务2
            staticNumCount(10);
        });
        future2.join();
        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
            // 任务3
            staticNumCount(10);
        });
        future3.join();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2, future3);

        if (future1.isDone() && future2.isDone() && future3.isDone()) {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            System.out.println("耗費時間: " + executionTime + "毫秒");
            allOf.thenRun(() -> System.out.println("Total: " + staticNum));
        }
    }

    private static void staticNumCount(int x) {
        for (int i = 0; i < x; i++) {
            try {
                Thread.sleep(1000); // 模拟耗时操作 執行1 秒 一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            staticNum++;
        }
    }


    /**
     * 组合多个任务
     */
    private static void completableFutureTest20() {
        // 记录程序开始时间
        long startTime = System.currentTimeMillis();
        CompletableFuture<Void> completableFuture01 = CompletableFuture.runAsync(() -> {
            UnicodeResolver.convertUnicodeToUTF8("dicts");
        });

        CompletableFuture<Void> completableFuture02 = CompletableFuture.runAsync(() -> {
            UnicodeResolver.convertUnicodeToUTF8("messages");
        });

        CompletableFuture<Void> completableFuture03 = CompletableFuture.runAsync(() -> {
            UnicodeResolver.convertUnicodeToUTF8("views");
        });
        completableFuture01.join();
        completableFuture02.join();
        completableFuture03.join();
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(completableFuture01, completableFuture02, completableFuture03);
        if (anyOf.isDone()) {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            System.out.println("耗費時間: " + executionTime + "毫秒");
        }
    }
}
