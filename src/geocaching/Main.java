package geocaching;

import java.io.File;

public class Main {

    public static void main(final String[] args) {
	// write your code here
        File f = new File("test\\geocaching\\");
        System.out.println(f.getAbsolutePath());
    }
}
