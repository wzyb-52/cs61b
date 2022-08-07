import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }   // Uncomment this class once you've created your Palindrome class.

    @Test
    public void testIsPalindrome() {
        assertTrue("\"\" is a Palindrome",
             palindrome.isPalindrome(""));
        assertTrue("\"a\" is a Palindrome",
             palindrome.isPalindrome("a"));
        assertTrue("\"123454321\" is a Palindrome",
             palindrome.isPalindrome("123454321"));
        assertFalse("\"1234564321\" is not a Palindrome",
             palindrome.isPalindrome("1234564321"));
        assertFalse("\"akjsbvjashgvjkavbja\" is not a Palindrome",
             palindrome.isPalindrome("akjsbvjashgvjkavbja"));
    }

    @Test
    public void testOffByOnePalindrome() {
        OffByOne obo = new OffByOne();
        assertTrue("\"\" is a Palindrome",
             palindrome.isPalindrome("", obo));
        assertTrue("\"a\" is a Palindrome",
             palindrome.isPalindrome("a", obo));
        assertTrue("\"&123465432%\" is a Palindrome",
             palindrome.isPalindrome("&123465432%", obo));
        assertFalse("\"akjsbvjashgvjkavbja\" is not a Palindrome",
             palindrome.isPalindrome("akjsbvjashgvjkavbja", obo));
    }

    public static void main(String... args) {        
        jh61b.junit.TestRunner.runTests("all", TestPalindrome.class);
    }
}
