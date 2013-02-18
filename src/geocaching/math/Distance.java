package geocaching.math;

import geocaching.waypoint.Waypoint;
import java.awt.Point;

public class Distance {
    
    /**
     * Calculates the distance between two coordinates. Coordinates are converted
     * to UTM coordinates as UTM uses meters from reference points.
     * @param a First waypoint
     * @param b Second waypoint
     * @return Distance between waypoint a and waypoint b in kilometers
     */
    public static double calculatePythagorean(Waypoint a, Waypoint b) {
        
        
        Point aUTM = geocaching.math.ConvertCoordinates.FromLatLonToUTM(a);
        Point bUTM = geocaching.math.ConvertCoordinates.FromLatLonToUTM(b);
        
        double easting = a.getEasting() - b.getEasting();
        double northing = a.getNorthing() - b.getNorthing();
        
        double eastingsqr = Math.pow(easting, 2);
        double northingsqr = Math.pow(northing, 2);
        
        return (Math.sqrt(northingsqr + eastingsqr))/1000;        
    }
}
