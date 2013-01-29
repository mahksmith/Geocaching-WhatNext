package geocaching;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipper{
    private static final int BUFFER_SIZE = 2048;
    
    public static List<ByteArrayOutputStream> unZip(File f) {
        try {
            FileInputStream fis = new FileInputStream(f);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(baos);
            
            byte[] buffer = new byte[BUFFER_SIZE];
            
            while (bis.read(buffer, 0, BUFFER_SIZE) != -1) {
                bos.write(buffer);
            } 
            bos.flush();
            bos.close();
            bis.close();
            List<ByteArrayOutputStream> listFiles = unzip(baos);
            return listFiles;
        }
        
        catch (Exception e) {
            return null;
        }
        
    }
    
    private static List<ByteArrayOutputStream> unzip(ByteArrayOutputStream zippedFile) {
        try {
            ZipInputStream inputStream = new ZipInputStream(
                    new BufferedInputStream(new ByteArrayInputStream(zippedFile.toByteArray())));

            ZipEntry entry;

            List<ByteArrayOutputStream> result = new ArrayList<ByteArrayOutputStream>();
            while ((entry = inputStream.getNextEntry()) != null) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                int count;
                byte data[] = new byte[BUFFER_SIZE];
                
                if (!entry.getName().endsWith("-wpts.gpx")) {
                    BufferedOutputStream out = new BufferedOutputStream(outputStream, BUFFER_SIZE);
                    while ((count = inputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                        out.write(data, 0, count);
                    }

                    out.flush();
                    out.close();

                    result.add(outputStream);
                }
            }
            return result;
        } catch (IOException ioe) {
            throw new RuntimeException();
        }
    }
}
