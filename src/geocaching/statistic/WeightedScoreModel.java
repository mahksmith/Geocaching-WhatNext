package geocaching.statistic;

import geocaching.waypoint.Waypoint;
import geocaching.waypoint.geocache.Geocache;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightedScoreModel {
    
    private List<Geocache> geocaches;
    private Map<Geocache, Double> scores;
    
    public WeightedScoreModel(List<Geocache> geocaches) {
        this.geocaches = geocaches;
        
        // Initialise Map
        scores = new HashMap<>();
        for (Geocache geocache : geocaches) {
            scores.put(geocache, new Double(1));
        }
    }
    
    public void calculateDistance(double kilometers, Waypoint home) {
        
        for (Geocache geocache : geocaches) {
            double distance = geocaching.math.Distance.calculatePythagorean(home, geocache);
            double distanceFromPreferred = Math.abs(distance - kilometers);
            
            // Score based on earth circumference of 40,075km
            // TODO instead of earth circumference, assign based on farthest cache in GPX file / Geocache list
            double score = 1 - (distanceFromPreferred / 
                    (geocaching.math.Distance.EARTH_CIRCUMFERENCE / 2));
            score = score * scores.get(geocache);
            scores.put(geocache, score);
        }
    }
}
