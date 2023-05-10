package jerryai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/25 15:58
 */
public class RangeListTest {
    @Test
    void cases() {
        RangeList rl = new RangeList();
        System.out.println(rl);
        assertEquals("", rl.toString());

        rl.addRange(new int[]{1, 5});
        assertEquals("[1,5)", rl.toString());

        rl.addRange(new int[]{10, 20});
        assertEquals("[1,5)[10,20)", rl.toString());

        rl.addRange(new int[]{20, 20});
        assertEquals("[1,5)[10,20)", rl.toString());

        rl.addRange(new int[]{20, 21});
        assertEquals("[1,5)[10,21)", rl.toString());

        rl.addRange(new int[]{2, 4});
        assertEquals("[1,5)[10,21)", rl.toString());

        rl.addRange(new int[]{3, 8});
        assertEquals("[1,8)[10,21)", rl.toString());

        rl.remove(new int[]{10, 10});
        assertEquals("[1,8)[10,21)", rl.toString());

        rl.remove(new int[]{10, 11});
        assertEquals("[1,8)[11,21)", rl.toString());

        rl.remove(new int[]{15, 17});
        assertEquals("[1,8)[11,15)[17,21)", rl.toString());

        rl.remove(new int[]{3, 19});
        assertEquals("[1,3)[19,21)", rl.toString());
    }
}
