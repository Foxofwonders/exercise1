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
 * Compare this track with an other
 * @return -1 if this track is smaller, 0 if equal and 1 if this track is larger
 */
  public int compareTo(Track other)
  {
    // for now, tracks are simple ordered by artist name
	  if (artist.compareTo(other.artist)==0)
	  {
		  if (cd.compareTo(other.cd)==0)
		  {
			  //Compare years
		  }
		  return cd.compareTo(other.cd);
	  }
	  return artist.compareTo(other.artist);
  }
}
