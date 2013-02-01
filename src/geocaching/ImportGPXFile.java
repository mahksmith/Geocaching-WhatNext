package geocaching;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
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


public class ImportGPXFile {
    

    public static boolean verifyGPXFile(File f, List<Geocache> geocaches) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(f);
            
            return verifyGPX(doc, geocaches);
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(ImportGPXFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ImportGPXFile.class.getName()).log(Level.WARNING, "Need to set a file using setfile()", ex);
        }
        
        return false;
    }
    
    public static boolean verifyGPXString(String s, List<Geocache> geocaches) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(s));
            Document doc = dBuilder.parse(is);
            
            return verifyGPX(doc, geocaches);
        } catch (SAXException | IOException | ParserConfigurationException ex ) {
            Logger.getLogger(ImportGPXFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(ImportGPXFile.class.getName()).log(Level.WARNING, 
                    "Failed to give String for input", ex);
        }
        
        return false;
    }
    
    private static boolean verifyGPX(final Document doc, List<Geocache> geocaches) {
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
            if (sym.compareTo("Geocache") != 0) {
                continue;
            }
            
            // Waypoint attributes
            float lat = Float.parseFloat(eElement.getAttribute("lat"));
            float lon = Float.parseFloat(eElement.getAttribute("lon"));
                        
            // Waypoint parameters
            String name = eElement.getElementsByTagName("name").item(0).getTextContent();
            
            Geocache newGeocache = new Geocache.Builder(name, lat, lon)
                    // Optional parameters here
                    .build();            
            
            geocaches.add(newGeocache);
        }
        return true;
    }
    
    
    
}
