package geocaching.waypoint.geocache;

import geocaching.waypoint.Waypoint;

/**
 * 
 * @author Mark Smith, mark@marksmith.geek.nz
 */
public class Geocache extends Waypoint {
        
    private final String type;
    private final double terrain;
    private final double difficulty;

    public Geocache(Builder builder) {
        super(builder);
        
        this.type = builder.type;
        this.terrain = builder.terrain;
        this.difficulty = builder.difficulty;
    }
    
    public String getType() {
        return this.type;
    }

    public double getTerrain() {
        return this.terrain;
    }

    public double getDifficulty() {
        return this.difficulty;
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
        private double terrain;
        private double difficulty;
        
        public Builder(String name, float northing, float easting) {
            super(name, northing, easting);
        }        
        
        public Builder withType(String val) {
            this.type = val; 
            return this;
        }
        
        public Builder withTerrain(String val) {
            this.terrain = Double.parseDouble(val);
            return this;
        }
        
        public Builder withDifficulty(String val) {
            this.difficulty = Double.parseDouble(val);
            return this;
        }
        
        @Override
        public Geocache build() {
            
            return new Geocache(this);
        }
    }
}
