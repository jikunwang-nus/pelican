import com.charsmart.data.asm.ClassRW;
import org.junit.Test;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/15 12:17 PM
 */
public class AsmRWTest {
    @Test
    public void createClass() {
        new ClassRW().createClass();
    }

    @Test
    public void modifyClass(){
        new ClassRW().modifyClass();
    }
}
