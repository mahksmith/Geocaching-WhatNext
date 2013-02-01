package geocaching;

class Geocache {
    
    private final float lat;
    private final float lon;
    private final String name;

    private Geocache(Builder builder) {
        this.lat = builder.lat;
        this.lon = builder.lon;
        this.name = builder.name;
    }    
    
    public float getLat() {
        return this.lat;
    }
    
    public float getLon() {
        return this.lon;
    }
    
    public String getName() {
        return this.name;
    }
    
    /**
     * Using Fluid Builder pattern
     */
    public static class Builder {
        
        // required parameters
        private final float lat;
        private final float lon;
        private final String name;
        
        // optional parameters;
        
        public Builder(String name, float lat, float lon) {
            this.lat = lat;
            this.lon = lon;
            this.name = name;
        }
        
        /*
        public Builder withX(int val) {
            x = val; return this;
        }
        */
        
        public Geocache build() {
            // perhaps need to check that all required parameters are used.
            return new Geocache(this);
        }
    }
}
