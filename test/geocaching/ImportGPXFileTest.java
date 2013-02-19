package geocaching;

import geocaching.waypoint.geocache.Geocache;
import geocaching.io.Unzipper;
import geocaching.io.ImportGPXFile;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;

/**
 * 
 * @author Mark Smith, mark@marksmith.geek.nz
 */
public class ImportGPXFileTest {
    
    private List<Geocache> geocaches;
    
    @Test
    public void fileVerificationNonGPX() {
        geocaches = ImportGPXFile.verifyGPXFile(
                new File("test/testZIP.zip"));
        Assert.assertNull(geocaches);
    }
    
    @Test
    public void fileVerificationGPX() {
        geocaches = ImportGPXFile.verifyGPXFile(
                new File("test/testGPX.gpx"));
        Assert.assertEquals(1000, geocaches.size());
    }
    
    @Test
    public void fileVerificationNull() {
        Assert.assertNull(ImportGPXFile.verifyGPXFile(
                new File("")));
    }
    
    @Test
    public void GPXFromStringOK() {
        /* I'm not sure of the best way to do this, but the easiest would be
         * to take the output from unzipping..
         */
        List<ByteArrayOutputStream> output;
        output = Unzipper.unZip(new File("test/testZIP.zip"));
        geocaches = new ArrayList<>();
        for (ByteArrayOutputStream baos : output) {
            List<Geocache> list = ImportGPXFile.verifyGPXString(baos.toString());
            geocaches.addAll(list);
        }
        // Checking all geocaches are accounted for
        Assert.assertEquals(1000, geocaches.size());
    }
    
    @Test
    public void GPXFromStringNotOK() {
        geocaches = ImportGPXFile.verifyGPXString("this is a bad string");
        Assert.assertNull(geocaches);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testNullStringOK() {
        geocaches = ImportGPXFile.verifyGPXString(null);
    }
}
