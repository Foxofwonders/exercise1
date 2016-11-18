package nl.ru.ai.exercise1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Exercise1
{
  private static final String DATABASE_FILENAME="songs.txt";
  private static final int MAX_NR_OF_TRACKS=5000;
  /*
   * Here we go
   */
  public static void main(String[] args)
  {
    try
    {
      Track[] database=new Track[MAX_NR_OF_TRACKS];
      /*
       * Read database
       */
      int length=readDatabase(database);
      System.out.printf("%d songs read from datatabase '%s'\n",length,DATABASE_FILENAME);
      /*
       * Ask for sorting method
       */
      Scanner input=new Scanner(System.in);
      SortingMethod method=askForSortingMethod(input);
      /*
       * Sort 
       */
      System.out.printf("Sorting with %s\n",method);
      switch(method)
      {
        case BUBBLE_SORT:
          bubbleSort(database,length);
          break;
        case INSERTION_SORT:
          insertionSort(database,length);
          break;
        case SELECTION_SORT:
          selectionSort(database,length);
          break;
      }
      System.out.println("Sorted!");
      /*
       * Show result
       */
      dumpDatabase(database,length);
    }
    catch(FileNotFoundException exception)
    {
      System.out.printf("Error opening database file '%s': file not found\n",DATABASE_FILENAME);
    }
  }
  private static void dumpDatabase(Track[] database, int length)
  {
    for(int i=0;i<length;i++)
    {
      System.out.printf("%-26s %-32s %4d %s\n",database[i].artist,database[i].cd,database[i].track,database[i].time);
    }
  }
  /**
   * Ask the user for a sorting method
   * @param input scanner used for asking
   * @return sorting method
   */
  private static SortingMethod askForSortingMethod(Scanner input)
  {
    /*
     * Show possible sorting methods
     */
    for(SortingMethod method : SortingMethod.values())
      System.out.printf("%d : %s\n",method.ordinal(),method);
    /*
     * Loop until valid choice
     */
    SortingMethod choice=null;
    while(choice==null)
    {
      System.out.println("Enter choice: ");
      int selection=input.nextInt();
      if(selection>=0&&selection<SortingMethod.values().length)
        choice=SortingMethod.values()[selection];
      else
        System.out.println("Invalid choice, try again!");
    }
    return choice;
  }
  /**
   * Reads the cd database from the file 'songs.txt' into the specified track array
   * @param database this is the database that will be filled with the input.
   * @return number of tracks read
   * @throws FileNotFoundException 
   */
  static int readDatabase(Track[] database) throws FileNotFoundException
  {
    FileInputStream inputStream=new FileInputStream(DATABASE_FILENAME);
    Scanner scanner=new Scanner(inputStream);
    int numberOfTracks=0;
    while(scanner.hasNext())
    {
      Track track=new Track();
      track.artist=scanner.nextLine();
      track.cd=scanner.nextLine();
      track.year=scanner.nextInt();
      scanner.nextLine();
      track.track=scanner.nextInt();
      scanner.nextLine();
      track.title=scanner.nextLine();
      track.tags=scanner.nextLine();
      track.time=new Length(scanner.nextLine());
      track.country=scanner.nextLine();
      database[numberOfTracks]=track;
      numberOfTracks++;
    }
    scanner.close();
    return numberOfTracks;
  }
  /*************** Auxiliary array routines from lecture ***************/
  /**
   * Checks if the slice of the specified array is sorted
   * @param array
   * @param slice
   * @return true if the slice of the array is in ascending order, false otherwise
   */
  static <T extends Comparable<T>> boolean isSorted(T[] array, Slice slice)
  {
    assert array!=null : "Array should be initialized";
    assert slice.isValid() : "Slice should be valid";
    for(int i=slice.from;i<slice.upto-1;i++)
      if(array[i].compareTo(array[i+1])>0)
        return false;
    return true;
  }
  /**
   * Find position in array slice where to insert new element
   * @param array
   * @param slice
   * @param y element for which the position should be returned
   * @return position where to insert
   */
  static <T extends Comparable<T>> int findInsertPosition(T[] array, Slice slice, T y)
  {
    assert array!=null : "Array should be initialized";
    assert slice.isValid() : "Slice should be valid";
    assert isSorted(array,slice);
    for(int i=slice.from;i<slice.upto;i++)
      if(array[i].compareTo(y)>=0)
        return i;
    return slice.upto;
  }
  /**
   * Shifts all elements in the slice one position to the right
   * @param array
   * @param slice
   */
  static <T extends Comparable<T>> void shiftRight(T[] array, Slice slice)
  {
    assert array!=null : "Array should be initialized";
    assert slice.isValid()&&slice.from<array.length : "Slice should be valid";
    for(int i=slice.upto;i>slice.from;i--)
      array[i]=array[i-1];
  }
  /**
   * Insert an element to a sorted array and keep it sorted
   * @param array
   * @param length length
   * @param y element to be added
   * @return new length
   */
  static <T extends Comparable<T>> int insert(T[] array, int length, T y)
  {
    assert array!=null : "Array should be initialized";
    assert length>=0 : "Length cannot be negative";
    assert isSorted(array,new Slice(0,length)) : "Array should be sorted";
    int position=findInsertPosition(array,new Slice(0,length),y);
    shiftRight(array,new Slice(position,length));
    array[position]=y;
    return length+1;
  }
  /**
   * Swap two elements in an array
   * @param array
   * @param i
   * @param j
   */
  private static <T extends Comparable<T>> void swap(T[] array, int i, int j)
  {
    assert array!=null : "Array should be initialized";
    assert i>=0&&i<array.length : "First index is invalid";
    assert j>=0&&j<array.length : "Second index is invalid";
    T help=array[i];
    array[i]=array[j];
    array[j]=help;
  }
  /*************** Array based Sorting routines from lecture ***************/
  /**
   * Sorts an array in situ in ascending order using selection sort
   * @param array
   * @oaram length
   */
  static <T extends Comparable<T>> void selectionSort(T[] array, int length)
  {
    assert array!=null : "array should be initialized";
    for(int i=0;i<length;i++)
    {
      int j=indexOfSmallestValue(array,new Slice(i,length));
      swap(array,i,j);
    }
  }
  /**
   * Finds index of smallest value in array slice
   * @param array
   * @param slice
   * @return index of smallest value
   */
  static <T extends Comparable<T>> int indexOfSmallestValue(T[] array, Slice slice)
  {
    assert array!=null : "Array should be initialized";
    assert slice.isValid()&&slice.upto<=array.length : "Slice should be valid";
    assert slice.upto-slice.from>0 : "Slice should be non-empty";
    int index=slice.from;
    for(int i=slice.from+1;i<slice.upto;i++)
      if(array[i].compareTo(array[index])<0)
        index=i;
    return index;
  }
  /**
   * Sorts an array in situ in ascending order using bubble sort
   * @param array
   * @param length
   */
  static <T extends Comparable<T>> void bubbleSort(T[] array, int length)
  {
    assert array!=null : "array should be initialized";
    while(!bubble(array,new Slice(0,length)))
        length--;
  }
  /**
   * Swap all adjacent pairs in the array slice that are not in the right order
   * @param array
   * @param slice
   * @return array slice is sorted
   */
  static <T extends Comparable<T>> boolean bubble(T[] array, Slice slice)
  {
    assert array!=null : "Array should be initialized";
    assert slice.isValid()&&slice.upto<=array.length : "Slice should be valid";
    boolean isSorted=true;
    for(int i=slice.from;i<slice.upto-1;i++)
      if(array[i].compareTo(array[i+1])>0)
      {
        swap(array,i,i+1);
        isSorted=false;
      }
    return isSorted;
  }
  /**
   * Sorts an array in situ in ascending order using insertion sort
   * @param array
   * @param length
   */
  static <T extends Comparable<T>> void insertionSort(T[] array, int length)
  {
    assert array!=null : "array should be initialized";
    for(int i=0;i<length;i++)
      insert(array,i,array[i]);
  }
}