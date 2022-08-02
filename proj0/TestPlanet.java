/**
 *  Tests my Planet class.
 *  Prints out the pairwise force between two planets.
 */
public class TestPlanet {
    public static void main(String args[]) {
        System.out.println("Checking calcDistance...");

        Planet p1 = new Planet(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Planet p2 = new Planet(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Planet p3 = new Planet(4.0, 5.0, 3.0, 4.0, 5.0, "jupiter.gif");

        checkPairwiseForce(p1, p2, 0.0, "calcPairwiseForce()", 0.01);
        checkPairwiseForce(p1, p3, 6.67E-11, "calcPairwiseForce()", 0.01);
    }

    public static void checkPairwiseForce(Planet p1, Planet p2, double expected, String label, double eps) {
        if (p1.xxPos == p2.xxPos && p1.yyPos == p2.yyPos) {
            System.out.println("PASS: These two planets are the same!\n");
            return;
        }
        double actual = p1.calcForceExertedBy(p2);
        if (Math.abs(expected - actual) <= eps * Math.max(expected, actual)) {
            System.out.println("PASS: " + label + ": Expected " + expected + " and you gave " + actual);
        } else {
            System.out.println("FAIL: " + label + ": Expected " + expected + " and you gave " + actual);
        }
    }
}
