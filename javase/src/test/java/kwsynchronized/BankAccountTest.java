package kwsynchronized;

import com.yimin.javase.kwsynchronized.BankAccount;
import org.junit.Assert;
import org.junit.Test;

import com.yimin.javase.kwsynchronized.SynchronizedAccount;

/**
 * Created by yimin on 15/5/21.
 */
public class BankAccountTest {
    private static int THREAD_NUM = 1000;

    @Test
    public void accountInstanceLockTest() {
        Thread[] threads = new Thread[THREAD_NUM];

        final BankAccount acc = new BankAccount("John", 0);

        for (int i = 0; i < THREAD_NUM; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    acc.deposit(100l);
                    acc.withdraw(100l);
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < THREAD_NUM; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }
        if (acc.getBalance() == 0) System.out.println(acc);
        else System.err.println(acc);
    }

    @Test
    public void synchronizedAccountInstanceLockTest() {
        Thread[] threads = new Thread[THREAD_NUM];
        final SynchronizedAccount acc = new SynchronizedAccount("John", 0);

        for (int i = 0; i < THREAD_NUM; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    acc.deposit(100l);
                    acc.withdraw(100l);
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < THREAD_NUM; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }
        if (acc.getBalance() == 0) System.out.println(acc);
        else System.err.println(acc);

        Assert.assertEquals("synchronized method",0,acc.getBalance(),0);
    }
}
