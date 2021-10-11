package singleton;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileTest {
    public static void main(String[] args) throws Exception {
        // volatile：可见性，不保证原子性，有序性
        // 定义：
        // -可见性：指当多个线程访问同一个变量时，一个线程修改了这个变量的值，其他线程能够立即看得到修改的值。
        // -原子性：即一个操作或者多个操作 要么全部执行并且执行的过程不会被任何因素打断，要么就都不执行。
        // -有序性：即程序执行的顺序按照代码的先后顺序执行。

        VolatileTest test = new VolatileTest();
        // 证明：可见性
//        test.visibility();
        // 证明：不保证原子性
//        test.nonAtomic();
        // 证明：有序性
        test.ordering();

    }

    // 证明：可见性
    private volatile boolean flag;

    public void visibility() {
        // 开一个线程，让其一直读flag
        new Thread() {
            @Override
            public void run() {
                super.run();
                // 让其一直循环，以保证线程可活
                while (!change) {
                }
            }
        }.start();
        // 睡一段时间，保证上面的线程执行
        safetySleep(1000);
        // 当前线程，修改flag为ture，以停止上面的循环
        change = true;
        System.out.println("end");
        // 结果：
        // -未加volatile：线程没停止，一直在运行
        // -加了volatile：线程停止，不再运行
    }

    // 证明：不保证原子性
//    private int count;
//
//    private void increase() {
//        count++;
//    }
//
//    private synchronized void increaseSyn() {
//        count++;
//    }
//
//    Lock lock = new ReentrantLock();
//
//    private void increaseLock() {
//        lock.lock();
//        count++;
//        lock.unlock();
//    }

    private final AtomicInteger count = new AtomicInteger();

    private void increaseAtomic() {
        count.getAndIncrement();
    }

    private void nonAtomic() {
        // 多个线程，同时修改一个数
        for (int i = 0; i < 100; i++) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    for (int i = 0; i < 100; i++) {
//                        increase();
//                        increaseSyn();
//                        increaseLock();
                        increaseAtomic();
                    }
                }
            }.start();
        }
        // 不能用Sleep，因为时间不定，它可能赋值操作，都操作完成了
//        safetySleep(1000);
        // Idea，Thread.activeCount()为2个，有一个是监控线程；其它Eclipse或Java执行的是一个
        while (Thread.activeCount() > 2) {
            // 超过默认线程数量，即有其它线程在执行，优先让其它线程执行，保证其它线程执行完
            // Thread.yield() 方法，使当前线程由执行状态，变成为就绪状态，让出CPU，在下一个线程执行时候，此线程有可能被执行，也有可能没有被执行。
            Thread.yield();
        }
        System.out.println("count=" + count);
        // 结果：
        // -未加volatile：count数值不对
        // -加了volatile：同上
        // -采用synchronized：数值正确
        // -采用Lock：数值正确
        // -采用AtomicInteger：数值正确
    }

    // 证明：有序性
    private int a, b;
    private boolean change;
//    private volatile boolean change;

    private void ordering() throws InterruptedException {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            // 如果在本线程内观察，所有操作都是有序的；如果在一个线程中观察另一个线程，所有操作都是无序的，因此开两个线程
            Thread t1 = new Thread(() -> {
                // 有可能发生重排，即 先执行 change = true，再执行 a = 1
                a = 1; // 1
                change = true; // 2
            });

            Thread t2 = new Thread(() -> {
                // 正常结果
                // -线程1的run方法先执行，b=2
                // -线程2的run方法先执行，b=0
                // 非正常结果（线程1的run方法内发成重排）
                // -线程1的run方法先执行
                // --1.发生重排
                // --2.又发生和线程2竞争，导致线程1的run方法未执行完成，只执行了(change = true)，就被线程2执行，导致b=1
                // -线程2的run方法先执行，b=0
                if (change) { // 3
                    b = a + 1; // 4
                }
            });
            // 线程1优先于线程2执行，但是不能保证其内部run方法的优先执行
            t1.start();
            t2.start();
            // 等待线程执行结束
            t1.join();
            t2.join();
            // 打印结果值
            if (b != 2)
                System.out.println("第 " + i + "次，b=" + b);
            // 如果b=1，即发生了重排
            if (b == 1) {
                break;
            }
            // 归原，以进行下次循环
            a = b = 0;
            change = false;
        }
        // 结果：
        // -未加volatile：会发生重排，会退出循环；执行几百万次后（最近一次是136Ｗ），有可能退出循环
        // -加了volatile：给change加上volatile后，保证change前面的不会重排，正确
        // 如下原理是保证每个时刻是有一个线程执行同步代码，相当于是让线程顺序执行同步代码，自然就保证了有序性。
        // -采用synchronized：正确
        // -采用Lock：正确
    }

    // 单例模式的双重锁为什么要加volatile
    // 因为，第5行实际操作为（a.分配内存，b.初始化对象，c.赋值），它可能会发生重排序，导致执行顺序为：a c b
    // 如果再来一个线程调用此方法，则先执行第2行，因为已经赋值过（执行过c），所以会直接进行返回，但是此时还没有执行b（初始化操作），所以会返回一个未初始化的对象。
    private volatile static VolatileTest instance;

    public static VolatileTest getInstance() {      // 1
        if (instance == null) {                     // 2
            synchronized (VolatileTest.class) {     // 3
                if (instance == null) {             // 4
                    instance = new VolatileTest();  // 5
                }
            }
        }
        return instance;                            // 6
    }

    private void safetySleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
