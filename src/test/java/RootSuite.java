/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nz.NzSuite;

/**
 *
 * @author mark
 */
public class RootSuite extends TestCase {
    
    public RootSuite(String testName) {
        super(testName);
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("RootSuite");
        suite.addTest(NzSuite.suite());
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
