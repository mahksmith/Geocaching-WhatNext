package nz.geek.marksmith.gcwhatnext.statistic;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import nz.geek.marksmith.gcwhatnext.math.Distance;
import nz.geek.marksmith.gcwhatnext.waypoint.Waypoint;
import nz.geek.marksmith.gcwhatnext.waypoint.geocache.Geocache;

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
        /* This might need a better scoring method, but it's not percentage 
         * based and users do not need to know the score. */
        for (Geocache geocache : geocaches) {
            double distanceToHome = Distance.calculatePythagorean(home, geocache);
            double distanceToPreferred = Math.abs(distanceToHome - distancePreferred);
            
            /* Might not be the best method of scoring, but should work ok
             * when using the "Geometric mean". */
            double score = 1 / distanceToPreferred;
            updateScore(geocache, score);
        }
    }
    
    public void calculateDifficultyScore(double difficultyPreferred) {
        for (Geocache geocache : geocaches) {
            double distanceToPreferred = geocache.getDifficulty() - difficultyPreferred;
            distanceToPreferred = Math.abs(distanceToPreferred);
            System.out.println(distanceToPreferred);
            
            distanceToPreferred = 1.0 / (1.0 + distanceToPreferred);
            updateScore(geocache, distanceToPreferred);
        }
    }
    
    public void calculateTerrainScore(double terrainPreferred) {
        for (Geocache geocache : geocaches) {
            double distanceToPreferred = geocache.getTerrain() - terrainPreferred;
            distanceToPreferred = Math.abs(distanceToPreferred);
            
            // scoring
            distanceToPreferred = 1.0 / (1.0 + distanceToPreferred);
            updateScore(geocache, distanceToPreferred);
        }
    }
    
    private void updateScore(Geocache geocache, double score) {
        double s = score * scores.get(geocache);
        scores.put(geocache, s);
        ++scoringMethodsUsed;
    }
    
    public void geometricMean() {
        for (Geocache g : geocaches) {
            // All the scores have been pre-multiplied, only need to take kth root
            double score = scores.get(g);
            score = Math.pow(score, 1.0 / scoringMethodsUsed);
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