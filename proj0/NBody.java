/**
 *  My NBody class is a static class, with no constructors. 
 *  So it enable us to call tbe metbods without create an
 *  object.
 */
public class NBody {
    public static double readRadius(String path) {
        In in = new In(path);
        int num = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String path) {
        In in = new In(path);
        int num = in.readInt();
        Planet[] group = new Planet[num];
        double radius = in.readDouble();

        for (int i = 0; i < num; ++i) {
            double xP = in.readDouble(), yP = in.readDouble(),
                xV = in.readDouble(), yV = in.readDouble(), m = in.readDouble();
            String name = in.readString();
            group[i] = new Planet(xP, yP, xV, yV, m, name);
        }
        return group;
    }

    public static void main(String args[]) {
        if (args.length != 3) {
            System.out.println("Wrong command line input!\nThe input format should be:");
            System.out.println("\tT dt filename\n");
            return ;
        }
        // args = new String[]{"15.0", "2.0", "data/planets.txt"};
        double T = Double.parseDouble(args[0]), dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] bodys = readPlanets(filename);

        // /**
        //  *  Now test our Input process
        //  */
        // System.out.println("Total time: " + T);
        // System.out.println("The dt: " + dt);
        // System.out.println("The filename: " + filename);
        // System.out.println("The radius: " + radius);
        // System.out.print("The planets are:");
        // for (Planet p : bodys) {
        //     System.out.print(" " + p.imgFileName);
        // }
        // System.out.println();

        /* Begin to draw the universe */
        StdDraw.enableDoubleBuffering();

        /**
         *  Try to draw a picture as background 
         */
        String background_picture = "images/starfield.jpg";
        StdDraw.setScale(-radius, radius);

        /* Begin simulation */
        double[] Fx = new double[bodys.length];
        double[] Fy = new double[bodys.length];
        for (double t = 0.0; t < T; t += dt) {
            StdDraw.clear();
            StdDraw.picture(0, 0, background_picture, 2 * radius, 2 * radius);

            /* Force analysis in one moment */
            int i = 0;
            for (Planet p : bodys) {
                p.draw();
                Fx[i] = p.calcNetForceExertedByX(bodys);
                Fy[i] = p.calcNetForceExertedByY(bodys);
                i += 1;
            }

            StdDraw.show();
            StdDraw.pause(10);

            /* Update every planet */
            i = 0;
            for (Planet p : bodys) {
                p.update(dt, Fx[i], Fy[i]);
                i += 1;
            }
        }

        /* Stay 2 seconds */
		StdDraw.pause(2000);	

        /*  */
        StdOut.printf("%d\n", bodys.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodys.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        bodys[i].xxPos, bodys[i].yyPos, bodys[i].xxVel,
                        bodys[i].yyVel, bodys[i].mass, bodys[i].imgFileName);   
        }
    }
}
