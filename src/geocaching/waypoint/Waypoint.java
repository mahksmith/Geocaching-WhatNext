/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geocaching.waypoint;

import java.io.Serializable;

/**
 *
 * @author Mark
 */
public class Waypoint implements Serializable {
    
    private final float northing;
    private final float easting;
    private final String name;
    //private final String type;
    
    public Waypoint(Builder builder) {
        this.northing = builder.northing;
        this.easting = builder.easting;
        this.name = builder.name;
    }
    
    public float getNorthing() {
        return this.northing;
    }
    
    public float getEasting() {
        return this.easting;
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public static class Builder {
        
        private final float northing;
        private final float easting;
        private final String name;
        
        public Builder(String name, float northing, float easting) {
            this.northing = northing;
            this.easting = easting;
            this.name = name;
        }
        
        public Waypoint build() {
            return new Waypoint(this);
        }
    }
}
