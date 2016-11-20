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
 
  /*public int compareTo(Track other)
  {
	  if (artist.compareTo(other.artist)==0)
	  {
		  if (cd.compareTo(other.cd)==0)
		  {
			  if(year==other.year)
			  {
				  if(track<other.track)
				  {
					  return -1;
				  } 
				  else 
				  {
					  return 1;
				  }
				  
			  }
		  }
		  return cd.compareTo(other.cd);
	  }
	  return artist.compareTo(other.artist);
  }
  */
  
  /**
   * Compare this track with an other based on track length
   * @return -1 if this track is smaller, 0 if equal and 1 if this track is larger
   */
  public int compareTo(Track other)
  {
	  if (time.compareTo(other.time)==0)
	  {
		  if (artist.compareTo(other.artist)==0)
		  {
			  if (cd.compareTo(other.cd)==0)
			  {
					  if(track<other.track)
					  {
						  return -1;
					  } 
					  else 
					  {
						  return 1;
					  }
			  }
			  return cd.compareTo(other.cd);
		  }
		  return artist.compareTo(other.artist);
	  }
  	return time.compareTo(other.time);
  }
}
