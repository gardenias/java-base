## java keyword _`synchronized`_  

1. `synchronized method`
2. `synchronized code block`
3. 对象锁：锁资源为对象，争抢粒度较小
    ```java
    public synchronized void deposit(float amt) {
        ...
    }
    ```  
    ```java
    private final Byte instanceLockObject = new Byte((byte)0);
    public void deposit(float amt) {
         synchronized(instanceLockObject){
            ...
         }
    }
    ```
    ```java
    public  void deposit(float amt) {
         synchronized(this){
            ...
         }
    }
    ```  

4. 类锁或者公共对象锁:锁资源为类或者公共对象，
类的定义只有一个，公共对象全局共享，因此会发生更多的争抢
    ```java
    public class A{
        public void method(){
            synchronized(A.class){
                ...
            }
        }
    }
    ```
    ```java
    public class A{
        public static final Byte classLockObject = new Byte((byte)0);
        public void method(){
            synchronized(classLockObject){
                ...
            }
        }
    }
    ```  

    以下摘自`java.lang.Shutdown`的代码片段：
    ```java
    package java.lang

    public class Shutdown{
        private static class Lock { };
        private static Object lock = new Lock();

        /* Lock object for the native halt method */
        private static Object haltLock = new Lock();

        /* Invoked by Runtime.runFinalizersOnExit */
        static void setRunFinalizersOnExit(boolean run) {
            synchronized (lock) {
                runFinalizersOnExit = run;
            }
        }
        /**
         * Add a new shutdown hook.  Checks the shutdown state and the hook itself,
         * but does not do any security checks.
         *
         * The registerShutdownInProgress parameter should be false except
         * registering the DeleteOnExitHook since the first file may
         * be added to the delete on exit list by the application shutdown
         * hooks.
         *
         * @params slot  the slot in the shutdown hook array, whose element
         *               will be invoked in order during shutdown
         * @params registerShutdownInProgress true to allow the hook
         *               to be registered even if the shutdown is in progress.
         * @params hook  the hook to be registered
         *
         * @throw IllegalStateException
         *        if registerShutdownInProgress is false and shutdown is in progress; or
         *        if registerShutdownInProgress is true and the shutdown process
         *           already passes the given slot
         */
        static void add(int slot, boolean registerShutdownInProgress, Runnable hook) {
            synchronized (lock) {
                if (hooks[slot] != null)
                    throw new InternalError("Shutdown hook at slot " + slot + " already registered");

                if (!registerShutdownInProgress) {
                    if (state > RUNNING)
                        throw new IllegalStateException("Shutdown in progress");
                } else {
                    if (state > HOOKS || (state == HOOKS && slot <= currentRunningHook))
                        throw new IllegalStateException("Shutdown in progress");
                }

                hooks[slot] = hook;
            }
        }
    }
    ```
