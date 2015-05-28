package com.yimin.javase.kwsynchronized;

/**
 * Created by yimin on 15/5/21.
 */
public class SynchronizedAccount {
    private String name;
    private float amount = 0.0f;
    private static final int DEFAULT_SLEEP_TIME = 100;
    private final Byte instanceLockObject = new Byte((byte)0);

    public SynchronizedAccount(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }

    public synchronized void deposit(float amt) {
        float tmp = amount;
        tmp += amt;

        sleep(DEFAULT_SLEEP_TIME);

        amount = tmp;
    }

    public synchronized void withdraw(float amt) {
        float tmp = amount;
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
        return "SynchronizedAccount{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
