package geocaching;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class UnzipperTest {
    @Test
    public void testZipFileOK() {
        File f = new File("test\\geocaching\\8498280_zzzHome.zip");
        List<ByteArrayOutputStream> files = Unzipper.unZip(f);
        Assert.assertTrue(files.size() > 0);
    }
    
    @Test
    public void testZipFileNotOK() {
        File f = new File("test\\geocaching\\UnzipperTest.java");
        List<ByteArrayOutputStream> files = Unzipper.unZip(f);
        Assert.assertTrue(files.size() == 0);
    }
}
