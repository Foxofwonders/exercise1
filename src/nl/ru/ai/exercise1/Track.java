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
  public int compareTo(Track other)
  {
    assert other!=null : "Undefined other track.";
    assert this !=null : "This track is undefined.";
    Exercise1.noOfComparisons++;
    {
      if(artist.toLowerCase().compareTo(other.artist.toLowerCase())==0)
        if(year==other.year)
          if(cd.toLowerCase().compareTo(other.cd.toLowerCase())==0)
            if(track<other.track)
              return -1;
            else
              return 1;
          else return cd.toLowerCase().compareTo(other.cd.toLowerCase());
        else if(year<other.year)
          return -1;
        else
          return 1;
      else return artist.toLowerCase().compareTo(other.artist.toLowerCase());
    }
  }


  
  
  /**
   * Compare this track with an other based on track length
   * @return -1 if this track is smaller, 0 if equal and 1 if this track is larger
   */
  public int compareTo1(Track other)
  {
	  Exercise1.noOfComparisons ++;
	  return time.compareTo(other.time);
  }
}
