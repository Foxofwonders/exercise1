package nl.ru.ai.exercise1;

public class Length
{
  public int minutes; // #minutes (0..)
  public int seconds; // #seconds (00.59)
  /**
   * Construct a Length object from a string
   * @param string
   */
  public Length(String string)
  {
    int position=string.indexOf(':');
    if(position<0)
      throw new RuntimeException(String.format("Invalid length format '%s'",string));
    minutes=Integer.parseInt(string.substring(0,position));
    seconds=Integer.parseInt(string.substring(position+1,string.length()));
  }
  /**
   * Convert Length object to string
   */
  public String toString()
  {
    return String.format("%d:%02d",minutes,seconds);
  }
  
public int compareTo(Length other) 
	{
		if (minutes==other.minutes)
		{
			if( seconds<other.seconds)
			{
			return -1;
			}
			else if( seconds>other.seconds)
			{
			return 1;
			}
			else 
			{
			return 0;
			}
		}
		if(minutes<other.minutes)
		{
		return -1;
		}
		else
		{
		return 1;
		}
	}
}
