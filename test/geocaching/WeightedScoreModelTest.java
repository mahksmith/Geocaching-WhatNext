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
import java.util.SortedMap;
import java.util.TreeMap;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Mark Smith, mark@marksmith.geek.nz
 */
public class WeightedScoreModelTest {
    
    private WeightedScoreModel slm;
    
    @Test
    public void testScoreDistanceOK() {
        Waypoint home = new Waypoint.Builder("home", -37, 175).build();
        Geocache g = new Geocache.Builder("g", -36, 174).build();
        Geocache h = new Geocache.Builder("h", -38, 176).build();
        
        // simulate list of geocaches
        List<Geocache> list = new ArrayList<>();        
        list.add(g);
        list.add(h);
        
        slm = new WeightedScoreModel(list);
        slm.calculateDistanceScore(141, home);
        slm.geometricMean();
        SortedMap<Geocache,Double> sorted = slm.returnScores();
        Assert.assertTrue(sorted.get(h) > sorted.get(g));
        
   }
}
