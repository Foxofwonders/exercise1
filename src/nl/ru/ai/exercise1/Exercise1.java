package nl.ru.ai.exercise1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Exercise1
{
  private static final String DATABASE_FILENAME="songs.txt";
  /*
   * Here we go
   */
  public static void main(String[] args)
  {
    try
    {
      ArrayList <Track> database = new ArrayList <Track>();
      /*
       * Read database
       */
     readDatabase(database);
      System.out.printf("%d songs read from datatabase '%s'\n",database.size(),DATABASE_FILENAME);
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
          bubbleSort(database);
          break;
        case INSERTION_SORT:
          insertionSort(database);
          break;
        case SELECTION_SORT:
          selectionSort(database);
          break;
      }
      System.out.println("Sorted!");
      /*
       * Show result
       */
      dumpDatabase(database);
    }
    catch(FileNotFoundException exception)
    {
      System.out.printf("Error opening database file '%s': file not found\n",DATABASE_FILENAME);
    }
  }
  private static void dumpDatabase(ArrayList <Track> database)
  {
    for(int i=0;i<database.size();i++)
    {
      System.out.printf("%-26s %-32s %4d %s\n",database.get(i).artist,database.get(i).cd,database.get(i).track,database.get(i).time);
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
   * Reads the cd database from the file 'songs.txt' into the specified track arraylist
   * @param database this is the database that will be filled with the input.
   * @throws FileNotFoundException 
   */
  static void readDatabase(ArrayList <Track> database) throws FileNotFoundException
  {
    FileInputStream inputStream=new FileInputStream(DATABASE_FILENAME);
    Scanner scanner=new Scanner(inputStream);
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
      database.add(track);
    }
    scanner.close();
  }
 
  /**
   * Checks if the slice of the specified ArrayList is sorted
   * @param ArrayList
   * @param slice
   * @return true if the slice of the ArrayList is in ascending order, false otherwise
   */
  static <T extends Comparable<T>> boolean isSorted(ArrayList <T> arraylist, Slice slice)
  {
    assert arraylist!=null : "Array should be initialized";
    assert slice.isValid() : "Slice should be valid";
    for(int i=slice.from;i<slice.upto-1;i++)
      if(arraylist.get(i).compareTo(arraylist.get(i+1))>0)
        return false;
    return true;
  }
  
  /**
   * Find position in ArrayList slice where to insert new element
   * @param ArrayList
   * @param slice
   * @param y element for which the position should be returned
   * @return position where to insert
   */
  static <T extends Comparable<T>> int findInsertPosition(ArrayList <T> arraylist, Slice slice, T y)
  {
    assert arraylist!=null : "Array should be initialized";
    assert slice.isValid() : "Slice should be valid";
    assert isSorted(arraylist,slice);
    for(int i=slice.from;i<slice.upto;i++)
      if(arraylist.get(i).compareTo(y)>=0)
        return i;
    return slice.upto;
  }
 
  /**
   * Insert an element to a sorted ArrayList and keep it sorted
   * @param ArrayList
   * @param length of slice
   * @param y element to be added
   */
  static <T extends Comparable<T>> void insert(ArrayList <T> arraylist, int length, T y)
  {
    assert arraylist!=null : "ArrayList should be initialized";
    assert arraylist.size()>=0 : "Length cannot be negative";
    assert isSorted(arraylist,new Slice(0,length)) : "ArrayList should be sorted";
    int position=findInsertPosition(arraylist,new Slice(0,length),y);
    arraylist.add(position,y);
    arraylist.remove(length+1);
  }
  /**
   * Swap two elements in an ArrayList
   * @param ArrayList
   * @param i
   * @param j
   */
  private static <T extends Comparable<T>> void swap(ArrayList <T> arraylist, int i, int j)
  {
    assert arraylist!=null : "ArrayList should be initialized";
    assert i>=0&&i<arraylist.size() : "First index is invalid";
    assert j>=0&&j<arraylist.size() : "Second index is invalid";
    T help=arraylist.get(i);
    arraylist.set(i, arraylist.get(j));
    arraylist.set(j, help);
  }

  /**
   * Sorts an arraylist in situ in ascending order using selection sort
   * @param arraylist
   * 
   */
  static <T extends Comparable<T>> void selectionSort(ArrayList <T> arraylist)
  {
    assert arraylist!=null : "arraylist should be initialized";
    for(int i=0;i<arraylist.size();i++)
    {
      int j=indexOfSmallestValue(arraylist,new Slice(i,arraylist.size()));
      swap(arraylist,i,j);
    }
  }
  /**
   * Finds index of smallest value in array slice
   * @param arraylist
   * @param slice
   * @return index of smallest value
   */
  static <T extends Comparable<T>> int indexOfSmallestValue(ArrayList <T> arraylist, Slice slice)
  {
    assert arraylist!=null : "Array should be initialized";
    assert slice.isValid()&&slice.upto<=arraylist.size() : "Slice should be valid";
    assert slice.upto-slice.from>0 : "Slice should be non-empty";
    int index=slice.from;
    for(int i=slice.from+1;i<slice.upto;i++)
      if(arraylist.get(i).compareTo(arraylist.get(index))<0)
        index=i;
    return index;
  }
  /**
   * Sorts an arraylist in situ in ascending order using bubble sort
   * @param arraylist
   * @param length
   */
  static <T extends Comparable<T>> void bubbleSort(ArrayList <Track> database)
  {
    assert database!=null : "ArrayList should be initialized";
    int length = database.size();
    while(!bubble(database,new Slice(0,length)))
        length--;
  }
  /**
   * Swap all adjacent pairs in the arraylist slice that are not in the right order
   * @param arraylist
   * @param slice
   * @return arraylist slice is sorted
   */
  static <T extends Comparable<T>> boolean bubble(ArrayList <Track> database, Slice slice)
  {
    assert database!=null : "ArrayList should be initialized";
    assert slice.isValid()&&slice.upto<=database.size() : "Slice should be valid";
    boolean isSorted=true;
    for(int i=slice.from;i<slice.upto-1;i++)
      if(database.get(i).compareTo(database.get(i+1))>0)
      {
        swap(database,i,i+1);
        isSorted=false;
      }
    return isSorted;
  }
  /**
   * Sorts an arraylist in situ in ascending order using insertion sort
   * @param arraylist
   * @param length
   */
  static <T extends Comparable<T>> void insertionSort(ArrayList <Track> database)
  {
    assert database!=null : " should be initialized";
    for(int i=0;i<database.size();i++)
      insert(database,i,database.get(i));
  }
}
