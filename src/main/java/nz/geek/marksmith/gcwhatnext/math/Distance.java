package nz.geek.marksmith.gcwhatnext.math;

import nz.geek.marksmith.gcwhatnext.waypoint.Waypoint;

/**
 * 
 * @author Mark Smith, mark@marksmith.geek.nz
 */
public class Distance {
    
    public static final int EARTH_CIRCUMFERENCE = 40076; // equatorial, rounded up
    
    /**
     * Calculates the distance between two coordinates in decimal kilometres. Coordinates are converted
     * to UTM coordinates as UTM uses meters from reference points.
     * @param a First Waypoint
     * @param b Second Waypoint
     * @return Distance between Waypoint a and Waypoint b in kilometers
     */
    public static double calculatePythagorean(Waypoint a, Waypoint b) {
        
        
        //Point aUTM = geocaching.math.ConvertCoordinates.FromLatLonToUTM(a);
        //Point bUTM = geocaching.math.ConvertCoordinates.FromLatLonToUTM(b);
        
        CoordinateConversion c = new CoordinateConversion();
        String c1 = c.latLon2UTM(a.getNorthing(), a.getEasting());
        String c2 = c.latLon2UTM(b.getNorthing(), b.getEasting());
        String[] split = c1.split("\\s");
        double c1East = Double.parseDouble(split[2]);
        double c1North = Double.parseDouble(split[3]);
        
        split = c2.split("\\s");
        int c2East = Integer.parseInt(split[2]);
        int c2North = Integer.parseInt(split[3]);
        
        double easting = c1East - c2East;
        double northing = c1North - c2North;
        
        double eastingsqr = Math.pow(easting, 2);
        double northingsqr = Math.pow(northing, 2);
        
        return (Math.sqrt(northingsqr + eastingsqr))/1000;        
    }
}
