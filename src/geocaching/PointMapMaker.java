package geocaching;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

class PointMapMaker {
    static int pointSize = 10;
    
    public static Image createPointMap(List<Geocache> geocaches, int imageWidth, int imageHeight) {
        // Calculate bounds TODO get this information from GPX file
        float boundNorthLat = 0f;
        float boundSouthLat = 1f;
        float boundWestLon = 0f;
        float boundEastLon = 1f;
        
        
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        
        for (Geocache g : geocaches) {
            graphics.setColor(getColor(g.getType()));
            Point p;
            p = findPositionOnMap(imageWidth, imageHeight,
                    boundNorthLat, boundSouthLat, boundWestLon, boundEastLon,
                    g.getLat(), g.getLon());
            graphics.drawRect(p.x, p.y, pointSize, pointSize);
            
            
        }
        return image.getSubimage(0, 0, imageWidth, imageHeight);      
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
        
        return new Point((int)x,(int)y);
    }
    
    private static Color getColor(String geocacheType) {
        Color c;
        switch(geocacheType) {
            case "Geocache|Traditional Cache": c = Color.GREEN; break;
            default: c = new Color(0, 0, 0, 0); break; // Set opaque
        }       
        
        return c;
    }
}
