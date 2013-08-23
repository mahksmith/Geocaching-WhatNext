/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.geek.marksmith.gcwhatnext.statistic;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author mark
 */
public class StatisticSuite extends TestCase {
    
    public StatisticSuite(String testName) {
        super(testName);
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("StatisticSuite");
        suite.addTest(WeightedScoreModelTest.suite());
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
