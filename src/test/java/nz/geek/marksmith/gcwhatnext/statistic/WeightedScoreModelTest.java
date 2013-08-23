/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.geek.marksmith.gcwhatnext.statistic;

import java.util.ArrayList;
import java.util.List;
import nz.geek.marksmith.gcwhatnext.waypoint.Waypoint;
import java.util.SortedMap;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nz.geek.marksmith.gcwhatnext.waypoint.geocache.Geocache;

/**
 *
 * @author mark
 */
public class WeightedScoreModelTest extends TestCase {
    
    private WeightedScoreModel wsm;

    public WeightedScoreModelTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(WeightedScoreModelTest.class);
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

    public void testScoreDistanceOK() {
        Waypoint home = new Waypoint.Builder("home", -37, 175).build();
        Geocache g = new Geocache.Builder("g", -36, 174)
                .withTerrain("2.0")
                .withDifficulty("1.0")
                .build();
        Geocache h = new Geocache.Builder("h", -38, 176)
                .withTerrain("2.5")
                .withDifficulty("2.5")
                .build();

        // simulate list of geocaches
        List<Geocache> list = new ArrayList<>();
        list.add(g);
        list.add(h);

        wsm = new WeightedScoreModel(list);
        wsm.calculateDistanceScore(141, home);
        wsm.calculateTerrainScore(2.5);
        wsm.calculateDifficultyScore(2.5);
        wsm.geometricMean();
        SortedMap<Geocache, Double> sorted = wsm.returnScores();
        System.out.println(sorted);
        assertTrue(sorted.get(h) > sorted.get(g));
    }
}
