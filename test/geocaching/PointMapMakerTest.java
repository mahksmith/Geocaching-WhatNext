package geocaching;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PointMapMakerTest {
    
    @Test
    public void testFindPositionOnMap() {
        
        Assert.assertEquals(
                new Point(129, 60), 
                PointMapMaker.findPositionOnMap(
                    400, 400, -37.2f, -39.6f, 175.2f, 176.6f, -37.56f, 175.98f));
        
    }
    
    @Test
    public void testImageGenerationOK() {
        ArrayList<Geocache> list = new ArrayList<>();
        //ImportGPXFile.verifyGPXFile(new File("test\\geocaching\\testGPX.gpx"), list);
        ImportGPXFile.verifyGPXFile(new File("waypoints.gpx"), list);
        BufferedImage i = PointMapMaker.createPointMap(list, 400, 400);
        
        File output = new File("pointmap.png");
        try {
            ImageIO.write(i, "png", output);
        } catch (IOException ex) {
            
        }
        
        Assert.assertNotNull(i);
    }
    
    @Test
    public void testImageGenerationZeroList() {
        ArrayList<Geocache> list = new ArrayList<>();
        Image i = PointMapMaker.createPointMap(list, 400, 400);
        Assert.assertNotNull(i);
    }
    
    @Test
    public void testImageGenerationNullList() {
        BufferedImage i = PointMapMaker.createPointMap(null, 400, 400);
        Assert.assertNull(i);
    }
}
