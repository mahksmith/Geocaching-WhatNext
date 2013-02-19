package geocaching.statistic;

import geocaching.waypoint.Waypoint;
import geocaching.waypoint.geocache.Geocache;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 
 * @author Mark Smith, mark@marksmith.geek.nz
 */
public class WeightedScoreModel {
    
    private List<Geocache> geocaches;
    private Map<Geocache, Double> scores;
    int scoringMethodsUsed;
    
    public WeightedScoreModel(List<Geocache> geocaches) {
        this.geocaches = geocaches;
        scoringMethodsUsed = 0;
        
        // Initialise Map
        scores = new HashMap<>();
        for (Geocache geocache : geocaches) {
            scores.put(geocache, new Double(1));
        }
    }
    
    /**
     * Calculates the distance score for each Geocache.
     * The current method pushes far away caches to a very low number.
     * @param distancePreferred
     * @param home Home waypoint
     */
    public void calculateDistanceScore(double distancePreferred, Waypoint home) {
        // initially, iterate through all caches calculating greatest distance
        // probably not efficient.
        Map<Geocache, Double> distances = new HashMap<>();
        for (Geocache geocache : geocaches) {
            double distanceToHome = geocaching.math.Distance.calculatePythagorean(home, geocache);
            double distanceToPreferred = Math.abs(distanceToHome - distancePreferred);
            
            /* Might not be the best method of scoring, but should work well
             * when using the "Geometric mean". */
            double score = 1 / distanceToPreferred;
            score = score * scores.get(geocache);
            scores.put(geocache, score);
        }
        
        // Is there a better way to add this to every method
        ++scoringMethodsUsed;
    }
    
    public void geometricMean() {
        for (Geocache g : geocaches) {
            // All the scores have been pre-multiplied, only need to take kth root
            double score = scores.get(g);
            score = Math.pow(score, 1 / scoringMethodsUsed);
            scores.put(g, score);
        }
    }
    
    public SortedMap<Geocache, Double> returnScores() {
        // Copy all items over to be sorted
        ValueComparator vc = new ValueComparator(scores);
        SortedMap<Geocache,Double> sortedGeocaches = new TreeMap<>(vc);
        sortedGeocaches.putAll(scores);
        
        return sortedGeocaches;
    }
}

class ValueComparator implements Comparator<Geocache> {
    
    Map<Geocache, Double> geocaches;
    
    public ValueComparator(Map<Geocache, Double> geocaches) {
        this.geocaches = geocaches;
    }
    
    @Override
    public int compare(Geocache a, Geocache b) {
        return geocaches.get(a).compareTo(geocaches.get(b));
    }
}