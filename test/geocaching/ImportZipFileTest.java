package geocaching;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
/**
 *
 * @author Mark Smith
 */
public class ImportZipFileTest {
    
    private ImportZipFile i;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        okFile = new File("test\\geocaching\\8498280_zzzHome.zip");
    }
    
    @Before
    public void setUp() throws Exception {
        izf = new ImportZipFile();
        izf.setZipFile(okFile);
    }
    
    private static ImportZipFile izf;
    private static File okFile;
    
    @Test
    public void testOKFileIsActuallyAFile() {
        Assert.assertTrue(okFile.exists());
    } 
    
    @Test
    public void checkLoadFileOK() {
        Assert.assertTrue(izf.getFile().isFile());
    }
    
    @Test
    public void checkLoadedFileIsZip() {
        Assert.assertTrue(izf.importFile());
    }
}
