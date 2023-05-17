import com.charsmart.pelican.lake.redis.Application;
import com.charsmart.pelican.lake.redis.PLock;
import com.charsmart.pelican.lake.redis.PLockRegister;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
