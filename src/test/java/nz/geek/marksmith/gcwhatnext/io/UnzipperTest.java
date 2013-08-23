/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.geek.marksmith.gcwhatnext.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author mark
 */
public class UnzipperTest extends TestCase {

    public UnzipperTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(UnzipperTest.class);
        return suite;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of unZip method, of class Unzipper.
     */
    public void testUnZipNull() {
        File f = null;
        List expResult = null;
        List result = Unzipper.unZip(f);
        assertEquals(expResult, result);
    }

    public void testZipFileOK() {
        File f = new File("src/main/resources/testfiles/testZIP.zip");
        List<ByteArrayOutputStream> files = Unzipper.unZip(f);
        assertTrue(files.size() > 0);
    }

    public void testZipFileNotOK() {
        File f = new File("src/main/resources/testfiles/testGPX.gpx");
        List<ByteArrayOutputStream> files = Unzipper.unZip(f);
        assertTrue(files.size() == 0);
    }

    public void testFileNotExist() {
        File f = new File("");
        List<ByteArrayOutputStream> files = Unzipper.unZip(f);
        assertNull(files);
    }
}
