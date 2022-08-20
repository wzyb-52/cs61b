import static org.junit.Assert.*;
import org.junit.Test;

public class RadixSortTester {
    private static String[] toSort = {"Carol", "Anne", "David", "Bob"};

    private static String[] expected = {"Anne", "Bob", "Carol", "David"};

    @Test
    public void testNaiveWithNonNegative() {
        String[] sorted = RadixSort.sort(toSort);
        assertArrayEquals(expected, sorted);
    }
}
