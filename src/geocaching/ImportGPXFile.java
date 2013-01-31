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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ImportGPXFile {
    
    private File f;

    public boolean setFile(File file) {
        if (file.exists()) {
            f = file;
            return true;
        }
        return false;
    }

    public boolean verifyGPXFile(List<Geocache> geocaches) {
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
    
    public boolean verifyGPXString(String s, List<Geocache> geocaches) {
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
    
    private boolean verifyGPX(final Document doc, List<Geocache> geocaches) {
        doc.getDocumentElement().normalize();
        
        NodeList nList = doc.getElementsByTagName("wpt");
        
        // Iterate through every single waypoint in list.
        for (int temp = 0; temp < nList.getLength(); temp++) {
            // TODO check here if cache is geocache or waypoint
            
            
            Node nNode = nList.item(temp);
            
            Element eElement = (Element) nNode;
            
            float lat = Float.parseFloat(eElement.getAttribute("lat"));
            float lon = Float.parseFloat(eElement.getAttribute("lon"));
                        
            // get geocache parameters
            String name = eElement.getElementsByTagName("name").item(0).getTextContent();
            
            Geocache newGeocache = new Geocache.Builder(name, lat, lon)
                    // Optional parameters here
                    .build();            
            
            geocaches.add(newGeocache);
        }
        return true;
    }
    
    
    
}
