An app for Geocaching that reads in a PocketQuery and tells you where the best place for you to go Geocaching based on your preferences and historical data.

Notes for myself for future implementation:
 * Distribution statistics -> highest / lowest density areas for both Pocket Queries and MyFinds
   - I'd like to make the program produce a .png map of this.
     - Heat map of distribution
     - Adjust PointMapMaker so that land and sea are differentiated.
  - Heat maps need to be of variable resolution size (bin size)
 * Regular statistics of finds
 * Recommendation of Geocaches to find based on various measurements (Step 1, analysis stats)
   - Cache saturation
   - Terrain and difficulty levels
   - Favourite points (can't get this information from PocketQuery yet)
   - Distance from specified / home coordinates (DONE)

Completed:
 * Map maker that creates a map (including basemap from naturalearthdata.com) of caches from .GPX file supplied.