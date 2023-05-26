import com.charsmart.pelican.lake.redis.Application;
import com.charsmart.pelican.lake.redis.PLock;
import com.charsmart.pelican.lake.redis.PLockRegister;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Wonder
 * @Date: Created on 2023/5/17 20:56
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class PLockTest {

    @Autowired
    private PLockRegister lockRegister;

    @Test
    public void testBlockLock() {
        PLock lock = lockRegister.getLock("lk", 200000);
        try {
            lock.lock();
            Thread.sleep(300);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Test
    public void multipleThread() {
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(50);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(50,
                2000,
                60, TimeUnit.SECONDS,
                queue
        );
        int threadNumber = 100;
        AtomicInteger count = new AtomicInteger(threadNumber);
        for (int i = 0; i < threadNumber; i++) {
            executor.submit(() -> {
                testBlockLock();
                count.decrementAndGet();
            });
        }
        while (count.get() > 0) {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        executor.shutdownNow();
    }

    public void testSingleLock() {
        PLock lock = lockRegister.getLock("lk", 20);
        try {
            boolean hold = lock.tryLock();
            if (hold) {
                System.out.println("[" + Thread.currentThread().getName() + "]succeed to get lock");
                Thread.sleep(500);
            } else {
                System.out.println("[" + Thread.currentThread().getName() + "]fail to get lock");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
//            System.out.println("succeed to release lock");
        }
    }

    @Test
    public void test() {
        PLock lock = lockRegister.getLock("lk", 20);
        try {
            boolean hold = lock.tryLock();
            if (hold) {
                System.out.println("succeed to get lock");
                /*测试重入*/
                try {
                    boolean b = lock.tryLock();
                    if (b) {
                        System.out.println("succeed to reentry");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                    System.out.println("succeed to release inner lock");
                }
            } else {
                System.out.println("fail to get lock");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
            System.out.println("succeed to release lock");
        }
    }
}
