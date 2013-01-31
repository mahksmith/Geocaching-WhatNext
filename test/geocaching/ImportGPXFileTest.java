package geocaching;

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
    }
    
    @Test
    public void fileDoesntExist() {
        ImportGPXFile igpxf = new ImportGPXFile();
        Assert.assertFalse(igpxf.setFile(new File("")));
    }
    
    private List<Geocache> geocaches = new ArrayList<Geocache>();
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
        // TODO: Make the files and verify
    }
    
    @Test
    public void GPXFromStringNotOK() {
        // TODO: make the files and verify
    }
}
