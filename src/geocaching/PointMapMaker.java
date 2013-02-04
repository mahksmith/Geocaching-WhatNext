package geocaching;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class PointMapMaker {
    static int pointSize = 10;
    
    public static BufferedImage createPointMap(List<Geocache> geocaches, int imageWidth, int imageHeight) {
        // Calculate bounds TODO get this information from GPX file
        float boundNorthLat = -36.723667f;
        float boundSouthLat = -41.436117f;
        float boundWestLon = 173.752383f;
        float boundEastLon = 176.43375f;
        
        
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setBackground(Color.BLACK);
        graphics.clearRect(0, 0, imageWidth, imageHeight);
        try {
            for (Geocache g : geocaches) {
                graphics.setColor(getColor(g.getType()));
                Point p;
                p = findPositionOnMap(imageWidth, imageHeight,
                        boundNorthLat, boundSouthLat, boundWestLon, boundEastLon,
                        g.getLat(), g.getLon());
                graphics.fillRect(p.x, p.y, 4, 4);
                //TODO make sure to draw the rectangles OVER the point, not starting atthe point

            }
            //graphics.dispose();
            
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
        float boundNorthingDiff = Math.abs(northBound - southBound);
        float boundEastingDiff = Math.abs(westBound - eastBound);
        
        // Calculate the bound Ratio
        float[] boundRatio; // in the form [x,y]
        if (boundEastingDiff > boundNorthingDiff) {
            boundRatio = new float[]{1, boundNorthingDiff / boundEastingDiff};
        } else {
            boundRatio = new float[]{boundEastingDiff / boundNorthingDiff, 1};
        }
        
        // Multiply the gaps by ratio to find scaled image size
        int[] scaledImageSize = new int[2];
        scaledImageSize[0] = (int)(boundRatio[0] * imageWidth);
        scaledImageSize[1] = (int)(boundRatio[1] * imageHeight);
        
        // calculate pixel placement
        float y = (Math.abs(cacheLat - northBound) / (boundNorthingDiff / imageHeight));
        float x = (Math.abs(cacheLon - westBound)  / (boundEastingDiff  / imageWidth));
        
        // apply ratio
        y *=  boundRatio[1];
        x *= boundRatio[0];
        
        // TODO Center the points on the map
        
        
        return new Point((int)x,(int)y);
    }
    
    private static Color getColor(String geocacheType) {
        Color c;
        switch(geocacheType) {
            case "Geocache|Traditional Cache"   : c = Color.GREEN; break;
            // Project A.P.E
            // LetterBox
            case "Geocache|Multi-cache"         : c = Color.YELLOW; break;
            // Event
            // MEGA
            // CITO
            // GPS Adventures
            case "Geocache|Virtual Cache"       : c = Color.WHITE; break;
            case "Geocache|Webcam Cache"        : c = Color.WHITE; break;
            case "Geocache|Earthcache"          : c = Color.WHITE; break;
            // Mystery
            case "Geocache|Wherigo Cache"       : c = Color.BLUE; break;
            default: c = new Color(0, 0, 0, 0); break; // Set opaque
        }       
        
        return c;
    }
}
