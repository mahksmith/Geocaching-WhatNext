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
        okFile = new File("test\\geocaching\\testZIP.zip");
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
        Assert.assertTrue(izf.getZippedFile().isFile());
    }
    
    @Test
    public void checkLoadedFileIsZip() {
        Assert.assertTrue(izf.importFile());
    }
    
    @Test
    public void checkLoadedFileIsSaved() {
        izf.importFile();
        Assert.assertTrue(izf.getUnzippedFiles().size() > 0);
    }
}
