/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    private static class compString {
        String str;
        int index;
        char toComp;
        public compString(String s) {
            str = s;
            index = -1;
            toComp = 0;
        }
        public char setIndex(int d) {
            index = d;
            toComp = index >= str.length() ? 0 : str.charAt(index);
            return toComp;
        }

        public char getToComp() {
            return toComp;
        }

        public String getStr() {
            return str;
        }
    }

    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        compString[] ascii = new compString[asciis.length];

        /** Initializes the compStrings arrow and finds the max index. */
        int maxIndex = 0;
        for (int i = 0; i < asciis.length; ++i) {
            String str = asciis[i];
            maxIndex = str.length() > maxIndex ? str.length() : maxIndex;
            ascii[i] = new compString(str);
        }

        /** Radix LSD Sort */
        for (int index = maxIndex - 1; index >= 0; --index) {
            ascii = sortHelperLSD(ascii, index);
        }

        /** Constructs a new String arrow. */
        String[] newAsciis = new String[asciis.length];
        for (int i = 0; i < asciis.length; ++i) {
            newAsciis[i] = ascii[i].getStr();
        }
        return newAsciis;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of compStrings
     * @param index The position to sort the Strings on.
     */
    private static compString[] sortHelperLSD(compString[] asciis, int index) {
        int[] counting = new int[256];
        for (compString item : asciis) {
            counting[item.setIndex(index)]++;
        }
        int[] starts = new int[256];
        for (int i = 0, pos = 0; i < 256; ++i) {
            starts[i] = pos;
            pos += counting[i];
        }
        compString[] temp = new compString[asciis.length];
        for (compString item : asciis) {
            char c = item.getToComp();
            int pos = starts[c]++;
            temp[pos] = item;
        }
        return temp;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
