/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geocaching;

import geocaching.statistic.WeightedScoreModel;
import geocaching.waypoint.Waypoint;
import geocaching.waypoint.geocache.Geocache;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Mark
 */
public class WeightedScoreModelTest {
    
    private WeightedScoreModel slm;
    
    @Test
    public void testScoreDistance() {
        Waypoint home = new Waypoint.Builder("home", -37.883333f, 175.466667f).build();
        Geocache g = new Geocache.Builder("g", -38, 176).build();
        List<Geocache> l = new ArrayList<>();
        l.add(g);
        slm = new WeightedScoreModel(l);
        slm.calculateDistance(50, home);
   }
}
