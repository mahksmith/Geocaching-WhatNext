package geocaching.waypoint.geocache;

import geocaching.waypoint.Waypoint;

public class Geocache extends Waypoint {
        
    private final String type;

    public Geocache(Builder builder) {
        super(builder);
        
        this.type = builder.type;
    }
    
    public String getType() {
        return this.type;
    }
    
    /**
     * Using Fluid Builder pattern
     */
    public static class Builder extends  Waypoint.Builder {
        
        // required parameters
        /*private final float northing;
        private final float easting;
        private final String name;*/
        
        // optional parameters;
        private String type;
        
        public Builder(String name, float northing, float easting) {
            super(name, northing, easting);
        }
        
        
        public Builder withType(String val) {
            this.type = val; 
            return this;
        }
        
        @Override
        public Geocache build() {
            
            return new Geocache(this);
        }
    }
}
