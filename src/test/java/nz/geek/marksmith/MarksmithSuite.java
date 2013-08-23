/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.geek.marksmith;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nz.geek.marksmith.gcwhatnext.GcwhatnextSuite;

/**
 *
 * @author mark
 */
public class MarksmithSuite extends TestCase {
    
    public MarksmithSuite(String testName) {
        super(testName);
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("MarksmithSuite");
        suite.addTest(GcwhatnextSuite.suite());
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
