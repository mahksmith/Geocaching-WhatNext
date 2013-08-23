/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.geek.marksmith.gcwhatnext.map;

import java.awt.Image;
import nz.geek.marksmith.gcwhatnext.waypoint.geocache.Geocache;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nz.geek.marksmith.gcwhatnext.io.ImportGPXFile;

/**
 *
 * @author mark
 */
public class PointMapMakerTest extends TestCase {

    public PointMapMakerTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(PointMapMakerTest.class);
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

    public void testCreatePointMapNull() {
        List<Geocache> geocaches = null;
        int imageWidth = 0;
        int imageHeight = 0;
        File shpFile = null;
        BufferedImage expResult = null;
        BufferedImage result = PointMapMaker.createPointMap(geocaches, imageWidth, imageHeight, shpFile);
        assertEquals(expResult, result);
    }

    public void testFindPositionOnMapNull() {
        int imageWidth = 0;
        int imageHeight = 0;
        float northBound = 0.0F;
        float southBound = 0.0F;
        float westBound = 0.0F;
        float eastBound = 0.0F;
        float cacheLat = 0.0F;
        float cacheLon = 0.0F;
        Point expResult = new Point(0, 0);
        Point result = PointMapMaker.findPositionOnMap(imageWidth, imageHeight, northBound, southBound, westBound, eastBound, cacheLat, cacheLon);
        assertEquals(expResult, result);
    }

    public void testFindPositionOnMap() {

        assertEquals(
                new Point(222, 60),
                PointMapMaker.findPositionOnMap(
                400, 400, -37.2f, -39.6f, 175.2f, 176.6f, -37.56f, 175.98f));

    }

    public void testImageGenerationOK() {
        File f = new File("src/main/resources/testfiles/4208888.gpx");
        List<Geocache> list = ImportGPXFile.verifyGPXFile(f);
        File shpFile = new File("src/main/resources/testfiles/ne_10m_land.shp");
        BufferedImage i = PointMapMaker.createPointMap(list, 750, 600, shpFile);

        File output = new File("src/main/resources/testfiles/pointmap.png");
        try {
            javax.imageio.ImageIO.write(i, "png", output);
        } catch (java.io.IOException ex) {
            System.err.println(ex);
        }

        assertNotNull(i);
    }

    public void testImageGenerationZeroList() {
        ArrayList<Geocache> list = new ArrayList<>();
        Image i = PointMapMaker.createPointMap(list, 400, 400, null);
        assertNotNull(i);
    }

    public void testImageGenerationNullList() {
        BufferedImage i = PointMapMaker.createPointMap(null, 400, 400, null);
        assertNull(i);
    }
}
