/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.geek;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nz.geek.marksmith.MarksmithSuite;

/**
 *
 * @author mark
 */
public class GeekSuite extends TestCase {
    
    public GeekSuite(String testName) {
        super(testName);
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("GeekSuite");
        suite.addTest(MarksmithSuite.suite());
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
