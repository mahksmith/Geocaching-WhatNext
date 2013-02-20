package geocaching;

import geocaching.waypoint.geocache.Geocache;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Mark Smith, mark@marksmith.geek.nz
 */
public class GeocacheTest {
    
    @BeforeClass
    public static void setUp() {
        geocacheOK = new Geocache.Builder("GC12345", -37, 175)
                .withType("Geocache|Traditional Cache")
                .withDifficulty("2")
                .build();
    }
    
    private static Geocache geocacheOK;
    
    @Test
    public void testNewGeocacheAllOK() {
        // Checking each required parameter
        Assert.assertNotNull(geocacheOK.getName());
        Assert.assertNotNull(geocacheOK.getNorthing());
        Assert.assertNotNull(geocacheOK.getEasting());
        
        // Checking each optional parameter
        Assert.assertNotNull(geocacheOK.getType());
        Assert.assertNotNull(geocacheOK.getTerrain());
    }
}
