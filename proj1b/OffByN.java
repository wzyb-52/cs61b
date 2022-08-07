public class OffByN implements CharacterComparator {
    public int n;
    OffByN(int N) {
        n = N;
    }
    @Override
    public boolean equalChars(char x, char y) {
        if (x - y == n || x - y == -n) {
            return true;
        }
        return false;
    }
}
