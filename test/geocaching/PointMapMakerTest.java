package geocaching;

import java.awt.Point;
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
    public void testImageGeneration() {
        // TODO
    }
}
