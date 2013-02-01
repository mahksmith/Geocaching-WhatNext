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
        igpxf = new ImportGPXFile();
        geocaches = new ArrayList<Geocache>();
    }
    
    @Test
    public void fileDoesntExist() {
        Assert.assertFalse(igpxf.setFile(new File("")));
    }
    
    private List<Geocache> geocaches = new ArrayList<>();
    private ImportGPXFile igpxf;
    
    @Test
    public void fileVerificationNonGPX() {
        igpxf.setFile(new File("test\\geocaching\\testZIP.zip"));
        Assert.assertFalse(igpxf.verifyGPXFile(geocaches));
    }
    
    @Test
    public void fileVerificationGPX() {
        igpxf.setFile(new File("test\\geocaching\\testGPX.gpx"));
        // TODO
        Assert.assertTrue(igpxf.verifyGPXFile(geocaches));
    }
    
    @Test
    public void GPXFromStringOK() {
        /* I'm not sure of the best way to do this, but the easiest would be
         * to take the output from unzipping..
         */
        ImportZipFile izf = new ImportZipFile();
        izf.setZipFile(new File("test\\geocaching\\testZIP.zip"));
        izf.importFile();
        System.out.println(izf.getUnzippedFiles().size());
        for (ByteArrayOutputStream baos : izf.getUnzippedFiles()) {
            igpxf.verifyGPXString(baos.toString(), geocaches);
        }
        
        Assert.assertEquals(1000, geocaches.size());
    }
    
    @Test
    public void GPXFromStringNotOK() {
        Assert.assertFalse(igpxf.verifyGPXString("this is a bad string", geocaches));
    }
}
