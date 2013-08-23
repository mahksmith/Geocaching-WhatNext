/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.geek.marksmith.gcwhatnext;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nz.geek.marksmith.gcwhatnext.io.IoSuite;
import nz.geek.marksmith.gcwhatnext.map.MapSuite;
import nz.geek.marksmith.gcwhatnext.math.MathSuite;
import nz.geek.marksmith.gcwhatnext.statistic.StatisticSuite;
import nz.geek.marksmith.gcwhatnext.waypoint.WaypointSuite;

/**
 *
 * @author mark
 */
public class GcwhatnextSuite extends TestCase {
    
    public GcwhatnextSuite(String testName) {
        super(testName);
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("GcwhatnextSuite");
        suite.addTest(MathSuite.suite());
        suite.addTest(MainTest.suite());
        suite.addTest(StatisticSuite.suite());
        suite.addTest(WaypointSuite.suite());
        suite.addTest(IoSuite.suite());
        suite.addTest(MapSuite.suite());
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
}
