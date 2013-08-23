package nz.geek.marksmith.gcwhatnext.math;

import nz.geek.marksmith.gcwhatnext.waypoint.Waypoint;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nz.geek.marksmith.gcwhatnext.waypoint.geocache.Geocache;

/**
 *
 * @author mark
 */
public class DistanceTest extends TestCase {
    
    public DistanceTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(DistanceTest.class);
        return suite;
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
   
    public void testCalculateDistanceOk() {
        Geocache a = new Geocache.Builder("a", -37, 175).build();
        Geocache b = new Geocache.Builder("b", -38, 176).build();
        double distance = Distance.calculatePythagorean(a, b);
                
        // Take approximation, unit from website is less than 0.027% inaccurate
        assertEquals("141.8", Double.toString(distance).substring(0, 5));
    }
}
