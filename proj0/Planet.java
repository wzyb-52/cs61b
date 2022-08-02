/**
 *  My Planet class.
 */
public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV, 
        double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double dx = xxPos - p.xxPos, dy = yyPos - p.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet p) {
        double G = 6.67E-11, r = calcDistance(p);
        return G * mass * p.mass / (r * r);
    }

    public double calcForceExertedByX(Planet p) {
        double F = calcForceExertedBy(p);
        return F * (p.xxPos - xxPos) / calcDistance(p);
    }

    public double calcForceExertedByY(Planet p) {
        double F = calcForceExertedBy(p);
        return F * (p.yyPos - yyPos) / calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet[] groups) {
        double netX = 0.0;
        for (Planet p : groups) {
            if (equals(p)) {
                continue;
            }
            netX += calcForceExertedByX(p);
        }
        return netX;
    }

    public double calcNetForceExertedByY(Planet[] groups) {
        double netY = 0.0;
        for (Planet p : groups) {
            if (equals(p)) {
                continue;
            }
            netY += calcForceExertedByY(p);
        }
        return netY;
    }

    public void update(double dt, double fx, double fy) {
        double ax = fx / mass, ay = fy / mass;
        xxVel += dt * ax;
        yyVel += dt * ay;
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }

    public void draw() {
        // double ratio = 2E-10;
        String picture = "images/" + imgFileName;
        StdDraw.picture(xxPos, yyPos, picture);
    }
}
