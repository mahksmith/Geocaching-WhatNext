/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.geek.marksmith.gcwhatnext.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nz.geek.marksmith.gcwhatnext.waypoint.geocache.Geocache;

/**
 *
 * @author mark
 */
public class ImportGPXFileTest extends TestCase {

    private List<Geocache> geocaches;

    public ImportGPXFileTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ImportGPXFileTest.class);
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
     * Test of verifyGPXFile method, of class ImportGPXFile.
     */
    public void testVerifyGPXFileNull() {
        System.out.println("verifyGPXFile");
        File f = null;
        List expResult = null;
        List result = ImportGPXFile.verifyGPXFile(f);
        assertEquals(expResult, result);
    }

    /**
     * Test of verifyGPXString method, of class ImportGPXFile.
     */
    public void testVerifyGPXString() {
        System.out.println("verifyGPXString");
        String s = "";
        List expResult = null;
        List result = ImportGPXFile.verifyGPXString(s);
        assertEquals(expResult, result);
    }

    public void testFileVerificationNonGPX() {
        geocaches = ImportGPXFile.verifyGPXFile(
                new File("src/main/resources/testfiles/testZIP.zip"));
        assertNull(geocaches);
    }

    public void testFileVerificationGPX() {
        geocaches = ImportGPXFile.verifyGPXFile(
                new File("src/main/resources/testfiles/testGPX.gpx"));
        assertEquals(1000, geocaches.size());
    }

    public void testFileVerificationNull() {
        assertNull(ImportGPXFile.verifyGPXFile(
                new File("")));
    }

    public void testGPXFromStringOK() {
        /* I'm not sure of the best way to do this, but the easiest would be
         * to take the output from unzipping..
         */
        List<ByteArrayOutputStream> output;
        output = Unzipper.unZip(new File("src/main/resources/testfiles/testZIP.zip"));
        geocaches = new ArrayList<>();
        for (ByteArrayOutputStream baos : output) {
            List<Geocache> list = ImportGPXFile.verifyGPXString(baos.toString());
            geocaches.addAll(list);
        }
        // Checking all geocaches are accounted for
        assertEquals(1000, geocaches.size());
    }

    public void testGPXFromStringNotOK() {
        geocaches = ImportGPXFile.verifyGPXString("this is a bad string");
        assertNull(geocaches);
    }

//    @Test(expected=IllegalArgumentException.class)
    public void testNullStringOK() {
        geocaches = ImportGPXFile.verifyGPXString(null);
    }
}
