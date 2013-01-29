
package geocaching;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.zip.ZipFile;

/**
 *
 * @author Mark Smith
 */
final class ImportZipFile {
    
    private File f = null;
    
    List<ByteArrayOutputStream> files = null;
    
    public ImportZipFile() {
    
    }

    void setZipFile(File f) {
        if (f.exists()) {
           this.f = f;
        }
    }

    public File getFile() {
        return f;
    }
    
    public boolean importFile() {
        files = Unzipper.unZip(f);
        if (files.size() > 0)
            return true;
        return false;
        
    }
}
