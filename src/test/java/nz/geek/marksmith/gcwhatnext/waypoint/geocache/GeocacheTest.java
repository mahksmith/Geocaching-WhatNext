/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.geek.marksmith.gcwhatnext.waypoint.geocache;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author mark
 */
public class GeocacheTest extends TestCase {
    
    private static Geocache geocacheOK;
    
    public GeocacheTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(GeocacheTest.class);
        return suite;
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        geocacheOK = new Geocache.Builder("GC12345", -37, 175)
                .withType("Geocache|Traditional Cache")
                .withTerrain("2")
                .build();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    
    public void testNewGeocacheAllOK() {
        // Checking each required parameter
        assertNotNull(geocacheOK.getName());
        assertNotNull(geocacheOK.getNorthing());
        assertNotNull(geocacheOK.getEasting());
        
        // Checking each optional parameter
        assertNotNull(geocacheOK.getType());
        assertNotNull(geocacheOK.getTerrain());
    }
}
