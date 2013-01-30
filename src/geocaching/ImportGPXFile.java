package geocaching;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ImportGPXFile {
    
    private File f;

    public boolean setFile(File file) {
        if (file.isFile()) {
            f = file;
            return true;
        }
        return false;
    }

    public boolean verifyGPXFile(List<Geocaches> geocaches) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(f);
            
            return verifyGPX(doc, geocaches);
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(ImportGPXFile.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return false;
    }
    
    public boolean verifyGPXString(String s, List<Geocaches> geocaches) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(s);
            
            return verifyGPX(doc, geocaches);
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(ImportGPXFile.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return false;
    }
    
    private boolean verifyGPX(final Document doc, List<Geocaches> geocaches) {
        doc.getDocumentElement().normalize();
        
        NodeList nList = doc.getElementsByTagName("wpt");
        
        return false;
    }
    
    
    
}
