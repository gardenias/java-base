package com.yimin.javase.kwsynchronized;

/**
 * Created by yimin on 15/5/21.
 */
public class BankAccount {
    private String name;
    private long amount = 0l;
    private static final int DEFAULT_SLEEP_TIME = 100;

    public BankAccount(String name, long amount) {
        this.name = name;
        this.amount = amount;
    }

    public void deposit(long amt) {
        long tmp = amount;
        tmp += amt;
        sleep(DEFAULT_SLEEP_TIME);
        amount = tmp;
    }

    public void withdraw(long amt) {
        long tmp = amount;
        tmp -= amt;

        sleep(DEFAULT_SLEEP_TIME);

        amount = tmp;
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    public float getBalance() {
        return amount;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
