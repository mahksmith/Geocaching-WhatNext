package geocaching;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GeocacheTest {
    
    @BeforeClass
    public static void setUp() {
        geocacheOK = new Geocache.Builder("GC12345", -37, 175).build();
    }
    
    private static Geocache geocacheOK;
    
    @Test
    public void testNewGeocacheAllOK() {
        // Checking each required parameter
        Assert.assertNotNull(geocacheOK.getName());
        Assert.assertNotNull(geocacheOK.getLat());
        Assert.assertNotNull(geocacheOK.getLon());
    }
}
