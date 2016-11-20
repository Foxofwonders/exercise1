package nl.ru.ai.exercise1;

public class Track implements Comparable<Track>
{
  public String artist;  // name of artist
  public String cd;      // cd title
  public int year;       // year production
  public int track;      // track number
  public String title;   // track title
  public String tags;    // track tags
  public Length time;    // track length
  public String country; // artist country
/**
 * Compare this track with an other based on artist name
 * @return -1 if this track is smaller, 0 if equal and 1 if this track is larger
 */
//  public int compareTo(Track other)
//  {
//	  Exercise1.noOfComparisons ++;
//  if (artist.compareTo(other.artist)!=0)
//	  return artist.compareTo(other.artist);
//  if (cd.compareTo(other.cd)!=0)
//	  return cd.compareTo(other.cd);
//  if (year!=other.year)
//  {
//	  return ((Integer)year).compareTo(other.year);
//  }
//  return ((Integer)track).compareTo(other.track);  
//  }
//}
  
  
  /**
   * Compare this track with an other based on track length
   * @return -1 if this track is smaller, 0 if equal and 1 if this track is larger
   */
  public int compareTo(Track other)
  {
	  Exercise1.noOfComparisons ++;
	  return time.compareTo(other.time);
  }
}
