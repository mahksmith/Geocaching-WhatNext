package nz.geek.marksmith.gcwhatnext;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import nz.geek.marksmith.gcwhatnext.io.ImportGPXFile;
import nz.geek.marksmith.gcwhatnext.io.Unzipper;
import nz.geek.marksmith.gcwhatnext.map.PointMapMaker;
import nz.geek.marksmith.gcwhatnext.waypoint.geocache.Geocache;

public class Main {

    public static void main(final String[] args) {
        System.out.println("Started...");
	// Checking of input
        if (args.length > 0) {
            System.out.println(args[0]);
            if (args[0].equals("FindsMap")) {
                System.out.print("Creating finds Map...");
                String username = args[1];
                String pocketquery = args[2];
                String filedestination = args[3];
                
                //generate list of geocaches from file
                List<ByteArrayOutputStream> input;
                input = Unzipper.unZip(new File(pocketquery));
                List<Geocache> geocaches = new ArrayList<>();
                for (ByteArrayOutputStream baos : input) {
                    List<Geocache> list = ImportGPXFile.verifyGPXString(baos.toString());
                    geocaches.addAll(list);
                }   
                System.out.println("Loaded ok: " + geocaches.size());
                
                File shpFile = new File("src/main/resources/testfiles/ne_10m_land.shp");
                System.out.println(shpFile.getAbsolutePath());
                BufferedImage i = PointMapMaker.createPointMap(geocaches, 750, 600, shpFile, username);

                File output = new File(filedestination);
                try {
                    javax.imageio.ImageIO.write(i, "png", output);
                } catch (java.io.IOException ex) {
                    System.err.println(ex);
                }
            }
        }            
    }
}
