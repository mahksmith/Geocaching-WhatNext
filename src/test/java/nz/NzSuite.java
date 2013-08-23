/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nz.geek.GeekSuite;

/**
 *
 * @author mark
 */
public class NzSuite extends TestCase {
    
    public NzSuite(String testName) {
        super(testName);
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("NzSuite");
        suite.addTest(GeekSuite.suite());
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
