package geocaching;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class PointMapMakerTest {
    
    @Test
    public void testFindPositionOnMap() {
        
        Assert.assertEquals(
                new Point(222, 60), 
                PointMapMaker.findPositionOnMap(
                    400, 400, -37.2f, -39.6f, 175.2f, 176.6f, -37.56f, 175.98f));
        
    }
    
    @Test
    public void testImageGenerationOK() {
        File f = new File("test\\4208888.gpx");
        List<Geocache> list = ImportGPXFile.verifyGPXFile(f);
        File shpFile = new File("test\\ne_10m_land.shp");
        BufferedImage i = PointMapMaker.createPointMap(list, 750, 600, shpFile);
        
        File output = new File("pointmap.png");
        try {
            javax.imageio.ImageIO.write(i, "png", output);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        
        Assert.assertNotNull(i);
    }
    
    @Test
    public void testImageGenerationZeroList() {
        ArrayList<Geocache> list = new ArrayList<>();
        Image i = PointMapMaker.createPointMap(list, 400, 400, null);
        Assert.assertNotNull(i);
    }
    
    @Test
    public void testImageGenerationNullList() {
        BufferedImage i = PointMapMaker.createPointMap(null, 400, 400, null);
        Assert.assertNull(i);
    }
    
    @Test
    public void testLegendImageLoadingOK() {
        Assert.assertNotNull(new PointMapMaker().makeLegend(0));
    }
}
