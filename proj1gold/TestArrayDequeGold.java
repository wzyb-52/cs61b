import org.junit.Test;
import static org.junit.Assert.*;

/**
 *  My test to Array deque.
 */
public class TestArrayDequeGold {
    @Test
    public void test1() {
        System.out.println("Test1 starts:\n");
        StudentArrayDeque<Integer> ad1 = new StudentArrayDeque<>();
        for (int i = 0; i < 4; ++i) {
            ad1.addFirst(i);
        }
        for (Integer expected = 3; expected >= 0; --expected) {
            Integer actual = ad1.removeFirst();
            assertEquals("Failed in " + (3 - expected) +"th remove(): "
                     + "actual "  + actual + " not equal to "
                     + expected + "!", expected, actual);
        }
    }
    @Test
    public void test2() {
        System.out.println("\nTest2 starts:\n");
        StudentArrayDeque<Integer> ad1 = new StudentArrayDeque<>();
        for (int i = 0; i < 4; ++i) {
            ad1.addLast(i);
        }
        for (Integer expected = 3; expected >= 0; --expected) {
            Integer actual = ad1.removeLast();
            assertEquals("Failed in " + (3 - expected) +"th remove(): "
                     + "actual "  + actual + " not equal to "
                     + expected + "!", expected, actual);
        }
    }
    @Test
    public void test3() {
        System.out.println("\nTest3 starts:\n");
        StudentArrayDeque<Integer> ad1 = new StudentArrayDeque<>();
        for (int i = 0; i < 12; ++i) {
            ad1.addFirst(i);
        }
        for (Integer expected = 0; expected < 8; ++expected) {
            Integer actual = ad1.removeLast();
            assertEquals("Failed in " + expected +"th remove(): "
                     + "actual "  + actual + " not equal to "
                     + expected + "!", expected, actual);
        }
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(TestArrayDequeGold.class);
    }
}
