package geocaching;

import geocaching.waypoint.geocache.Geocache;
import junit.framework.Assert;
import org.junit.Test;

public class DistanceTest {
    
    @Test
    public void calculateDistanceOk() {
        Geocache a = new Geocache.Builder("a", -37, 175).build();
        Geocache b = new Geocache.Builder("b", -38, 176).build();
        double distance = geocaching.math.Distance.calculatePythagorean(a, b);
                
        // Take approximation, unit from website is less than 0.027% inaccurate
        Assert.assertEquals("141.8", Double.toString(distance).substring(0, 5));
    }
}
