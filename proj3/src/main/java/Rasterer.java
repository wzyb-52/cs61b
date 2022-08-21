import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    /** Add some private static variables to make life easier. */
    private static double ROOT_ULLAT, ROOT_ULLON, ROOT_LRLAT, ROOT_LRLON;
    private static int TILE_SIZE;
    private static double[] LonDPPSofAllLevels, LatDPPSofAllLevels;

    public Rasterer() {
        // YOUR CODE HERE
        /** Just copy these variables from MapServer. */
        ROOT_ULLAT = MapServer.ROOT_ULLAT;
        ROOT_ULLON = MapServer.ROOT_ULLON;
        ROOT_LRLAT = MapServer.ROOT_LRLAT;
        ROOT_LRLON = MapServer.ROOT_LRLON;
        TILE_SIZE  = MapServer.TILE_SIZE;
        getLonAndLatDPPs();
    }

    /** Caculates LonDPPs and LatDPPs of all 8 levels of graph sets. */
    private void getLonAndLatDPPs() {
        LonDPPSofAllLevels = new double[8];
        LatDPPSofAllLevels = new double[8];
        LonDPPSofAllLevels[0] = (ROOT_LRLON - ROOT_ULLON) / TILE_SIZE;
        LatDPPSofAllLevels[0] = (ROOT_ULLAT - ROOT_LRLAT) / TILE_SIZE;
        for (int i = 1; i < 8; ++i) {
            LonDPPSofAllLevels[i] = LonDPPSofAllLevels[i - 1] / 2;
            LatDPPSofAllLevels[i] = LatDPPSofAllLevels[i - 1] / 2;
        }
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // System.out.println(params);

        /** Finds the depth according to the appropriate LonDPP,
         *  then calculates the LonDPP and LatDPP of raster. */
        double lrlon = params.get("lrlon"), ullon = params.get("ullon"),
            ullat = params.get("ullat"), lrlat = params.get("lrlat");
        double queryLonDPP = (lrlon - ullon) / params.get("w");
        int depth;
        for (depth = 0; depth < 8; ++depth) {
            if (LonDPPSofAllLevels[depth] <= queryLonDPP) {
                break;
            }
        }
        if (depth == 8) {
            depth = 7;  // depth is at most 7.
        }
        double rasterLonDPP = LonDPPSofAllLevels[depth], rasterLatDPP = LatDPPSofAllLevels[depth];

        /** Finds the indexes of four tiles in corners */
        int xul, xlr, yul, ylr, MAXINDEX = (int) Math.pow(2, depth) - 1;
        xul = ullon <= ROOT_ULLON ? 0
            : (ullon > ROOT_LRLON ? MAXINDEX : (int) ((ullon - ROOT_ULLON) / rasterLonDPP / TILE_SIZE));
        xlr = lrlon <= ROOT_ULLON ? 0
            : (lrlon > ROOT_LRLON ? MAXINDEX : (int) ((lrlon - ROOT_ULLON) / rasterLonDPP / TILE_SIZE));
        yul = ullat >= ROOT_ULLAT ? 0
            : (ullat < ROOT_LRLAT ? MAXINDEX : (int) ((ROOT_ULLAT - ullat) / rasterLatDPP / TILE_SIZE));
        ylr = lrlat >= ROOT_ULLAT ? 0
            : (lrlat < ROOT_LRLAT ? MAXINDEX : (int) ((ROOT_ULLAT - lrlat) / rasterLatDPP / TILE_SIZE));

        /** Calculates the coordinates of four corners of raster */
        double raster_ul_lon, raster_lr_lon, raster_ul_lat, raster_lr_lat;
        raster_ul_lon = ROOT_ULLON + xul * TILE_SIZE * rasterLonDPP;
        raster_lr_lon = ROOT_ULLON + (xlr + 1) * TILE_SIZE * rasterLonDPP;
        raster_ul_lat = ROOT_ULLAT - yul * TILE_SIZE * rasterLatDPP;
        raster_lr_lat = ROOT_ULLAT - (ylr + 1) * TILE_SIZE * rasterLatDPP;

        /** Concatenates name strings of all tiles */
        String[][] render_grid = new String[ylr - yul + 1][xlr - xul + 1];
        for (int y = 0; y < ylr - yul + 1; ++y) {
            for (int x = 0; x < xlr - xul + 1; ++x) {
                render_grid[y][x] = String.format("d%d_x%d_y%d.png", depth, x + xul, y + yul);
            }
        }

        /** I'm not sure for the criteria of the query success */
        boolean query_success = true;
        if (ullon >= ROOT_LRLON || lrlon <= ROOT_ULLON || ullat <= ROOT_LRLAT || lrlat >= ROOT_ULLAT) {
            query_success = false;
        }

        /** Finally constructs the return map with all information of our raster */
        Map<String, Object> results = new HashMap<>();
//        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
//                           + "your browser.");
        results.put("depth", depth);
        results.put("query_success", query_success);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("render_grid", render_grid);
        return results;
    }

}
