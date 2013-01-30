
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
    private List<ByteArrayOutputStream> files = null;
    
    public ImportZipFile() {
    
    }

    public void setZipFile(File f) {
        if (f.exists()) {
           this.f = f;
        }
    }

    public File getZippedFile() {
        return f;
    }
    
    public List<ByteArrayOutputStream> getUnzippedFiles() {
        return files;
    }    
    
    public boolean importFile() {
        this.files = Unzipper.unZip(f);
        if (files.size() > 0)
            return true;
        return false;
        
    }
}
