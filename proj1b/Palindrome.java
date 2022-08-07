public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> ad = new ArrayDeque<Character>();
        for (int i = 0; i < word.length(); ++i) {
            ad.addLast(word.charAt(i));
        }
        return ad;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> ad = wordToDeque(word);
        for (int i = 0; i * 2 + 1 < word.length(); ++i) {
            if (word.charAt(i) != ad.removeLast()) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> ad = wordToDeque(word);
        for (int i = 0; i * 2 + 1 < word.length(); ++i) {
            if (cc.equalChars(word.charAt(i), ad.removeLast()) == false) {
                return false;
            }
        }
        return true;
    }
}
