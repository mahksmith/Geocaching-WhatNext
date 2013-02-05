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
    static int pointSize = 4;
    
    public static BufferedImage createPointMap(List<Geocache> geocaches, int imageWidth, int imageHeight) {
        // Calculate bounds TODO get this information from GPX file
        try {
            float boundNorthLat = -200;
            float boundSouthLat = 200;              
            float boundWestLon = 200; 
            float boundEastLon = -200;
            for (Geocache g : geocaches) {
                if (g.getLat() > boundNorthLat)
                    boundNorthLat = g.getLat();
                if (g.getLat() < boundSouthLat)
                    boundSouthLat = g.getLat();
                if (g.getLon() < boundWestLon)
                    boundWestLon = g.getLon();
                if (g.getLon() > boundEastLon)
                    boundEastLon = g.getLon();
            }       

            BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setBackground(Color.BLACK);
            graphics.clearRect(0, 0, imageWidth, imageHeight);
        
            for (Geocache g : geocaches) {
                graphics.setColor(getColor(g.getType()));
                Point p;
                p = findPositionOnMap(imageWidth, imageHeight,
                        boundNorthLat, boundSouthLat, boundWestLon, boundEastLon,
                        g.getLat(), g.getLon());
                
                // need the point centered on the cache
                graphics.fillRect(p.x - (int)(0.5 * pointSize), 
                        p.y - (int)(0.5 * pointSize), pointSize, pointSize);

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
        
        int offsetX = 0;
        int offsetY = 0;
        
        // Calculate the bound Ratio, and offsets for centering
        float[] boundRatio; // in the form [x,y]
        if (boundEastingDiff > boundNorthingDiff) {
            boundRatio = new float[]{1, boundNorthingDiff / boundEastingDiff};
            
        } else {
            boundRatio = new float[]{boundEastingDiff / boundNorthingDiff, 1};
        }        // Multiply the gaps by ratio to find scaled image size
        
        // Multiply the gaps by ratio to find scaled image size
        int[] scaledImageSize = new int[2];
        scaledImageSize[0] = (int)(boundRatio[0] * imageWidth);
        scaledImageSize[1] = (int)(boundRatio[1] * imageHeight);
        
        if (scaledImageSize[0] < imageWidth) {
            offsetX = (int)(0.25 * scaledImageSize[0]);
        } else if (scaledImageSize[1] < imageHeight) {
            offsetY = (int)(0.25 * scaledImageSize[1]);
        }
        
        // calculate pixel placement
        float y = (Math.abs(cacheLat - northBound) / (boundNorthingDiff / imageHeight));
        float x = (Math.abs(cacheLon - westBound)  / (boundEastingDiff  / imageWidth));
        
        // apply ratio
        y *=  boundRatio[1];
        x *= boundRatio[0];
        
        // TODO Center the points on the map
        
        return new Point((int)x + offsetX,(int)y + offsetY);
    }
    
    private static Color getColor(String geocacheType) {
        Color c;
        switch(geocacheType) {
            case "Geocache|Traditional Cache"   : c = Color.GREEN; break;
            // Project A.P.E
            // LetterBox
            case "Geocache|Multi-cache"         : c = Color.YELLOW; break;
            case "Geocache|Event Cache"         : c = Color.RED; break;
            // MEGA
            // CITO
            // GPS Adventures
            case "Geocache|Virtual Cache"       : c = Color.WHITE; break;
            case "Geocache|Webcam Cache"        : c = Color.WHITE; break;
            case "Geocache|Earthcache"          : c = Color.WHITE; break;
            case "Geocache|Unknown Cache"       : c = Color.BLUE; break;
            case "Geocache|Wherigo Cache"       : c = Color.BLUE; break;
            default: c = new Color(0, 0, 0, 0); break; // Set opaque
        }       
        
        return c;
    }
}
