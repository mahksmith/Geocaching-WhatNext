package geocaching;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;

class PointMapMaker {
    static int pointSize = 3;
    
    public static BufferedImage createPointMap(List<Geocache> geocaches, int imageWidth, int imageHeight,
            File shpFile) {
        // Calculate bounds TODO get this information from GPX file
        try { // TODO remove this big huge try block
            float boundNorth = -200;
            float boundSouth = 200;              
            float boundWest = 200; 
            float boundEast = -200;
            for (Geocache g : geocaches) {
                if (g.getLat() > boundNorth)
                    boundNorth = g.getLat();
                if (g.getLat() < boundSouth)
                    boundSouth = g.getLat();
                if (g.getLon() < boundWest)
                    boundWest = g.getLon();
                if (g.getLon() > boundEast)
                    boundEast = g.getLon();
            }
            
            BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setBackground(Color.BLACK);
            graphics.clearRect(0, 0, imageWidth, imageHeight);
            
            if (shpFile != null) {
                StreamingRenderer renderer = getNWBaseMap(shpFile);
                ReferencedEnvelope envelope = new ReferencedEnvelope();
                
                // Recompute bounds to make them the same ratio as the image, so the map isn't skewed
                float diffX = Math.abs(boundNorth - boundSouth);
                float diffY = Math.abs(boundEast - boundWest);
                
                
                float ratioImageX = 1;
                float ratioImageY = 1;
                
                if (imageWidth < imageHeight) {
                    ratioImageY = (float)imageWidth / imageHeight;
                    diffY = diffY / ratioImageY;
                    float yMean = (boundNorth + boundSouth) / 2;
                    boundNorth = yMean + (0.5f * diffY);
                    boundSouth = yMean - (0.5f * diffY);
                    
                } else { // Width >= Height
                    ratioImageX = (float)imageHeight / imageWidth;
                    diffX = diffX / ratioImageX;
                    float xMean = (boundEast + boundWest) / 2;
                    boundWest = xMean - (0.5f * diffX);
                    boundEast = xMean + (0.5f * diffX);
                }
                
                envelope.include(boundWest, boundNorth);
                envelope.include(boundEast, boundSouth);
                renderer.paint(graphics, new Rectangle(imageWidth, imageHeight), envelope);
            }
        
            for (Geocache g : geocaches) {
                graphics.setColor(getColor(g.getType()));
                Point p;
                p = findPositionOnMap(imageWidth, imageHeight,
                        boundNorth, boundSouth, boundWest, boundEast,
                        g.getLat(), g.getLon());
                
                // need the point centered on the cache
                graphics.fillRect(p.x - (int)(0.5 * pointSize), 
                        p.y - (int)(0.5 * pointSize), pointSize, pointSize);

            }
            
            return image.getSubimage(0, 0, imageWidth, imageHeight);      
        } catch (NullPointerException ex) {
            Logger.getLogger(PointMapMaker.class.getName()).log(Level.WARNING, "No caches included.", ex);
            return null;
        }
    }
    
    public static Point findPositionOnMap(int imageWidth, int imageHeight,
            float northBound, float southBound, 
            float westBound,  float eastBound, 
            float cacheLat, float cacheLon) {
    
        // Find size of gap between each bounds
        float boundXDiff = eastBound - westBound;
        float boundYDiff = northBound - southBound;
        
        // divide through the pixels to find the size representation of each pxl
        float pixelSizeX = boundXDiff / imageWidth;
        float pixelSizeY = boundYDiff / imageHeight;
        
        // find difference between the top left and the wpt
        float cacheDiffX = Math.abs(cacheLon - westBound);
        float cacheDiffY = Math.abs(cacheLat - northBound);
        
        // Divide through by pixel resolution to get pixel placement
        int x = (int)(cacheDiffX / pixelSizeX);
        int y = (int)(cacheDiffY / pixelSizeY);
        
        return new Point(x, y);
    }
    
    private static Color getColor(String geocacheType) {
        Color c;
        switch(geocacheType) {
            case "Geocache|Traditional Cache"   : c = Color.GREEN; break;
            case "Geocache|Project APE Cache"   : c = Color.GREEN; break;
            case "Geocache|Letterbox Hybrid"    : c = Color.GREEN; break;
            case "Geocache|Multi-cache"         : c = Color.YELLOW; break;
            case "Geocache|Event Cache"         : c = Color.RED; break;
            case "Geocache|Mega-Event Cache"    : c = Color.RED; break;
            case "Geocache|Cache In Trash Out Event" : c = Color.RED; break;
            case "Geocache|GPS Adventures Exhibit" : c = Color.RED; break;
            case "Geocache|Virtual Cache"       : c = Color.WHITE; break;
            case "Geocache|Webcam Cache"        : c = Color.WHITE; break;
            case "Geocache|Earthcache"          : c = Color.WHITE; break;
            case "Geocache|Unknown Cache"       : c = Color.BLUE; break;
            case "Geocache|Wherigo Cache"       : c = Color.BLUE; break;
            default: c = new Color(0, 0, 0, 0); break; // Set opaque
        }       
        
        return c;
    }
    
    private static StreamingRenderer getNWBaseMap(File shpFile) {
        // calculate how many base maps to load (0.5 degree by 0.5 degree limit)
        
        StreamingRenderer renderer = new StreamingRenderer();
        
        FileDataStore dataStore;
        SimpleFeatureSource shapeFileSource;
        try {
            dataStore = FileDataStoreFinder.getDataStore(shpFile);
            shapeFileSource = dataStore.getFeatureSource();
        } catch (IOException ex) {
            return renderer;
        }
        Style shpStyle = SLD.createPolygonStyle(Color.WHITE, Color.GREEN, 0.0f);
        
        final MapContent map = new MapContent();
        
        Layer shpLayer = new FeatureLayer(shapeFileSource, shpStyle);
        map.addLayer(shpLayer);
        //StreamingRenderer renderer = new StreamingRenderer();
        renderer.setMapContent(map);
        
        
        return renderer;
    }
    
    public BufferedImage makeLegend(int keyPlacement) {
        try {
            // TODO: Find a more efficient way to do this!
            BufferedImage traditional = ImageIO.read(new File("src/resources/images/traditional.gif"));
            BufferedImage multi = ImageIO.read(new File("src/resources/images/multi.gif"));
            BufferedImage mystery = ImageIO.read(new File("src//resources/images/mystery.gif"));
        } catch (IOException ex) {
            Logger.getLogger(ImportGPXFile.class.getName()).log(Level.SEVERE, "Image loading failed", ex);
            return null;
        }
        
        //Images loaded correctly, so we need to make the key
        //TODO
        
        return new BufferedImage(10, 10, 10);
    }
}
