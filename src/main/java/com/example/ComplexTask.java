package com.example;


public class ComplexTask {

    private final int taskId;

    public ComplexTask(int taskId) {
        this.taskId = taskId;
    }

    public int execute() {
        System.out.println("Вычисление задачи " + taskId + " в " + Thread.currentThread().getName());
        int sum = 0;
        for (int i = 1; i <= 5; i++) {
            sum += i;
        }
        return sum;
    }
}
