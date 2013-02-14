package geocaching;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UnzipperTest {
    
    @Test
    public void testZipFileOK() {
        File f = new File("test/testZIP.zip");
        List<ByteArrayOutputStream> files = Unzipper.unZip(f);
        Assert.assertTrue(files.size() > 0);
    }
    
    @Test
    public void testZipFileNotOK() {
        File f = new File("test/testGPX.gpx");
        List<ByteArrayOutputStream> files = Unzipper.unZip(f);
        Assert.assertTrue(files.size() == 0);
    }
    
    @Test
    public void testFileNotExist() {
        File f = new File("");
        List<ByteArrayOutputStream> files = Unzipper.unZip(f);
        Assert.assertNull(files);
    }
}
