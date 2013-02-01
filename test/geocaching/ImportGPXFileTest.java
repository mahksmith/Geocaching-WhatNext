package geocaching;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class ImportGPXFileTest {
    
    @Before
    public void setUp() {
        geocaches = new ArrayList<>();
    }
    
    private List<Geocache> geocaches = new ArrayList<>();
    
    @Test
    public void fileVerificationNonGPX() {
        Assert.assertFalse(ImportGPXFile.verifyGPXFile(
                new File("test\\\\geocaching\\\\testZIP.zip"),
                (geocaches)));
    }
    
    @Test
    public void fileVerificationGPX() {
        Assert.assertTrue(ImportGPXFile.verifyGPXFile(
                new File("test\\\\geocaching\\\\testGPX.gpx"),
                geocaches));
        Assert.assertEquals(1000, geocaches.size());
    }
    
    @Test
    public void fileVerificationNull() {
        Assert.assertFalse(ImportGPXFile.verifyGPXFile(
                new File(""), geocaches));
    }
    
    @Test
    public void GPXFromStringOK() {
        /* I'm not sure of the best way to do this, but the easiest would be
         * to take the output from unzipping..
         */
        List<ByteArrayOutputStream> output;
        output = Unzipper.unZip(new File("test\\geocaching\\testZIP.zip"));
        
        for (ByteArrayOutputStream baos : output) {
            ImportGPXFile.verifyGPXString(baos.toString(), geocaches);
        }
        // Checking all geocaches are accounted for
        Assert.assertEquals(1000, geocaches.size());
    }
    
    @Test
    public void GPXFromStringNotOK() {
        Assert.assertFalse(ImportGPXFile.verifyGPXString("this is a bad string", geocaches));
    }
    
    @Test
    public void testNullStringOK() {
        Assert.assertFalse(ImportGPXFile.verifyGPXString(null, geocaches));
    }
}
