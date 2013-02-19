package geocaching.io;

import geocaching.waypoint.geocache.Geocache;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 
 * @author Mark Smith, mark@marksmith.geek.nz
 */
public class ImportGPXFile {
    

    public static List<Geocache> verifyGPXFile(File f) {
        if (f == null) {
            throw new IllegalArgumentException();
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(f);
            
            return verifyGPX(doc);
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(ImportGPXFile.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return null;
    }
    
    public static List<Geocache> verifyGPXString(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(s));
            Document doc = dBuilder.parse(is);
            
            return verifyGPX(doc);
        } catch (SAXException | IOException | ParserConfigurationException ex ) {
            Logger.getLogger(ImportGPXFile.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return null;
        
    }
    
    private static List<Geocache> verifyGPX(Document doc) {
        List<Geocache> geocaches = new ArrayList<>();
        doc.getDocumentElement().normalize();
        
        NodeList nList = doc.getElementsByTagName("wpt");
        
        // Iterate through every single waypoint in list.
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            
            Element eElement = (Element) nNode;
            
            /* We are only interested in Geocaches, not all waypoints, so we 
             * pass over all other waypoints.
             */
            String sym = eElement.getElementsByTagName("sym").item(0).getTextContent();
            if (sym.contains("Geocache") != true) {
                continue;
            }
            
            // Waypoint attributes
            float lat = Float.parseFloat(eElement.getAttribute("lat"));
            float lon = Float.parseFloat(eElement.getAttribute("lon"));
            
            String type = eElement.getElementsByTagName("type").item(0).getTextContent();
                        
            // Waypoint parameters
            String name = eElement.getElementsByTagName("name").item(0).getTextContent();
            
            Geocache newGeocache = new Geocache.Builder(name, lat, lon)
                    .withType(type)
                    .build();         
            
            geocaches.add(newGeocache);
        }
        return geocaches;
    }
    
    
    
}
