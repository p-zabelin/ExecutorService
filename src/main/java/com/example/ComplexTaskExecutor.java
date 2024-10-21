package com.example;

import java.util.concurrent.*;

public class ComplexTaskExecutor {
    private final int numberOfTasks;
    private final int[] results;

    public ComplexTaskExecutor(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
        this.results = new int[numberOfTasks];
    }

    public void executeTasks(int numberOfTasks) {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfTasks);
        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks, () -> {
            System.out.println("Вычислена последняя задача в " + Thread.currentThread().getName());
            int combinedResult = 0;
            for (int result : results) {
                combinedResult += result;
            }
            System.out.println("Общий результат " + combinedResult + " в " + Thread.currentThread().getName());
        });

        for (int i = 0; i < numberOfTasks; i++) {
            final int taskIndex = i;
            executorService.submit(() -> {
                ComplexTask task = new ComplexTask(taskIndex);
                try {
                    results[taskIndex] = task.execute();
                    barrier.await(); // Ждем, пока все потоки не завершат выполнение
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executorService.shutdown(); // Завершаем работу ExecutorService
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
