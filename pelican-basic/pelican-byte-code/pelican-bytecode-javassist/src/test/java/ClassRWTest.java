import com.charsmart.data.javassist.ClassRW;
import org.junit.Test;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/15 4:45 PM
 */
public class ClassRWTest {
    @Test
    public void testCreate() {
        new ClassRW().createClass();
    }

    @Test
    public void testModify() {
        new ClassRW().modify();
    }
}
