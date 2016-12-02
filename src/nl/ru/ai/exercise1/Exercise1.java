package nl.ru.ai.exercise1;
/**
 * @author Denise van Baalen (s4708237)
 * @author Anna Gansen (s4753755)
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Exercise1
{
  private static final String DATABASE_FILENAME="songs.txt";
  public static int noOfComparisons;
  /*
   * Comparisons BubbleSort: 	11378455 duration, 	161653 artist
   * Comparisons InsertionSort: 5585402 duration,	11376166 artist
   * Comparisons SelectionSort: 11383606 duration,	11383606 artist
   * 
   * InsertionSort makes significantly less comparisons than BubbleSort and SelectionSort when comparing based on duration.
   * However, when sorted by artist, the number of comparisons per sorting method is roughly the same.
   * 
   * Selection Sort makes the most comparisons, and the number of comparisons does not depend on the type of ordering.
   * This is because selection sort always needs to compare all elements in the unsorted array in order to find the smallest value.
   * 
   * InsertionSort compares the first unsorted value to all previous sorted values and inserts it where it fits.
   * This works much better when all values to be compared are different (durations), than when the to be compared values are often the 
   * same (artists), because it would compare the same artist to itself (in the sorted list) all the way before adding a new to-be-inserted
   * track. Note that this effect disappears when the order of the tracks in the original file is descending (because the new track can
   * then be added to the beginning of the list of that artist). 
   * However, the tracks in the original file are (at least mostly) in ascending order.
   * 
   * BubbleSort is much, more more efficient when sorting by artist than when sorting by duration. This is because the distribution of
   * track lengths is more or less random, whether in the original file, most tracks are already in order of artist, which means bubble-
   * sort has very little tracks it needs to swap.
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
      noOfComparisons =0;
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
          //  To sort in the order of Track length, change compareTo-function in Track.java
        case HEAP_SORT:
            heapSort(database);
            break;
      }
      System.out.println("Sorted!");
      /*
       * Show result
       */
      dumpDatabase(database);
      System.out.println("Number of comparisons:"+noOfComparisons);
    }
    catch(FileNotFoundException exception)
    {
      System.out.printf("Error opening database file '%s': file not found\n",DATABASE_FILENAME);
    }
  }

	
  /**
   * Sorts an array using heap sort.
   * @param database
   */
  static <T extends Comparable<T>> void heapSort(ArrayList<Track> database)
  {
    assert database!=null : "ArrayList should be initialized";
    buildHeap(database);
    pickHeap(database);
  }
  /**
   * Turns an ArrayList into a heap.
   * @param database
   */
  private static <T extends Comparable<T>>void buildHeap(ArrayList<T> database)
  {
    assert database!=null : "Source array should be initialized";
    for(int i=1;i<database.size();i++)
    {
      pushUp(i,database);
    }
  }
  /**
   * Swaps an element with its parent as long as its bigger than its parent.
   * @param i
   * @param database
   */
  private static <T extends Comparable<T>> void pushUp(int i, ArrayList<T> database)
  {
    assert database!=null : "Source array should be initialized";
    while(database.get(i).compareTo(database.get((i-1)/2))>0 && i>0)
    {
      T save=database.get((i-1)/2);
      database.set((i-1)/2,database.get(i));
      database.set(i,save);
      i=(i-1)/2;
    }
  }
  /**
   * Sorts the arrayList from heap to min-to-max sorting.
   * @param database
   */
  private static <T extends Comparable<T>>void pickHeap(ArrayList<T> database)
  {
    assert database!=null : "Source array should be initialized";
    for(int i=database.size()-1;i>0;i--)
    {
      swap(database, 0,i);
      pushDown(database,i);
    }
  }
  /**
   * Swaps an element with its biggest child as long as it's smaller than one of its children.
   * To be applied after moving the biggest element to the last spot.
   * @param sorted
   * @param source
   */
  private static <T extends Comparable<T>>void pushDown(ArrayList<T> source, int sorted)
  {
    assert source!=null : "Source array should be initialized";
    int parent=0;
    while(((2*parent+1<sorted&&source.get(parent).compareTo(source.get(2*parent+1))<0)||(2*parent+2<sorted&&source.get(parent).compareTo(source.get(2*parent+2))<0)))
    {
      if(source.get(2*parent+1).compareTo(source.get(2*parent+2))>0||2*parent+2>=sorted)
      {
        T save=source.get(parent);
        source.set(parent,source.get(2*parent+1));
        source.set(2*parent+1,save);
        parent=((2*parent)+1);
      } else
      {
        T save=source.get(parent);
        source.set(parent,source.get(2*parent+2));
        source.set(2*parent+2,save);
        parent=((2*parent)+2);
      }
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
    assert arraylist!=null : "ArrayList should be initialized";
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
    assert arraylist!=null : "ArrayList should be initialized";
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
    assert database!=null : "ArrayList should be initialized";
    for(int i=0;i<database.size();i++)
      insert(database,i,database.get(i));
  }
}
