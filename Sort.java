import java.io.* ;
import java.lang.Math;
import java.util.Scanner;

/**
 *This static class handles all elements of the program.
 *File input, sorting, and searching.
 *
 *@author Stanislav Lyakhov
 *
 *@version 1.1.0
 */
public class Sort {

    public static long ctime;

    public static void main(String[] args) {
        Scanner fileNum = new Scanner(System.in);
        System.out.println("Enter file number: ");
        int input = fileNum.nextInt();
        String[] raw;
        try {
            Scanner scan = new Scanner(new File("input" + input + ".txt"));
            System.out.println("Enter desired sorting  algorithm: (selection, insertion, merge) or search algorithm (linear or binary)");
            interpretInput(parse(scan.nextLine().split(",")));
            fileNum.close();
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     *Turns input file frim array of Strings, to array of integers.
     *
     *@param raw array of Strings read from file
     *@return array of integers
     */
    public static int[] parse(String[] raw) {
        int arr[] = new int[raw.length];
        for (int i = 0; i < arr.length; i++){
            arr[i] = Integer.parseInt(raw[i]);
        }
        return arr;
    }

    /**
     *Takes user input and interprets it.
     *Runs specified algorithm
     *
     *@param arr parsed input file
     */
    public static void interpretInput(int[] arr) throws FileNotFoundException{ 
        Scanner sc = new Scanner(System.in); //Algorithm selection
        String algo = sc.nextLine(); //Reads algorithm input

        ctime = System.currentTimeMillis(); //Set time before algorithm execution

        if (algo.equals("insertion")) {
            printArray(insertionSort(arr));
        } else if (algo.equals("selection")) { 
            printArray(selectionSort(arr));
        } else if (algo.equals("merge")) { 
            printArray(mergeSort(arr));
        } else if (algo.equals("binary")) { 
            printSearch(binarySearch(mergeSort(arr)));
        } else if (algo.equals("linear")) { 
            printSearch(linearSearch(arr));
        } else {
            System.out.println("Algorithm not found");
        }
        sc.close();
    }
    /**
     *Implementation of the selection sort
     *Sorts input file
     *
     *@param arr array of integers to be sorted
     *@return sorted array
     */
    public static int[] selectionSort(int[] arr) {
        int j = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (j = i+1; j < arr.length; j++) {
                if(arr[min] > arr[j]) {
                    min = j;
                }
            }
            int temp = arr[i]; //
            arr[i] = arr[min]; //Element swap
            arr[min] = temp;   //
        }
        return arr;
    }
    /**
     *Implementation of the insertion sort
     *Sorts input file
     *
     *@param arr array of integers to be sorted
     *@return sorted array
     */
    public static int[] insertionSort(int[] arr){
        for (int i = 1; i < arr.length; i++ ) {
            int pos = i;
            while (pos != 0 && arr[pos-1] > arr[pos]) {
                int temp = arr[pos-1];  //
                arr [pos-1] = arr[pos]; //Element swap
                arr [pos] = temp;       //
                pos--;
            }
        }
        return arr;
    }

    /**
     *Implementation of recursive merge sort.
     *Sorts input file, uses  merge helper method
     *
     *@param arr array of integers to be sorted
     *@return sorted array
     *@see merge
     */
    public static int[] mergeSort(int[] arr) {
        if (arr.length == 1) {
            return arr;
        }
        int[] result;
        int[] arr1 = new int[arr.length/2];
        int[] arr2 = new int[arr.length - arr.length/2];
        System.arraycopy(arr, 0, arr1, 0, arr1.length);
        System.arraycopy(arr, arr1.length, arr2, 0, arr2.length);

        if (arr.length == 2) {
            result = merge(arr1, arr2) ;
        } else {
            result = merge(mergeSort(arr1), mergeSort(arr2));
        }
        return result;
    }
    
    /**
     *Merges two arrays in ascending order.
     *Helper method for mergeSort.
     *
     *@param arr1 array of integers to merge
     *@param arr2 array of integers to merge
     *@return merged array of integers
     *@see mergeSort
     */
    public static int[] merge(int[] arr1, int[] arr2) {
        int[] result;
        int counter1 = 0;
        int counter2 = 0;
        int i = 0;

        int[] arr = new int[arr1.length + arr2.length];
        while (counter1 != arr1.length && counter2 != arr2.length) {
            if (arr1[counter1] < arr2[counter2]) {
                arr[i] = arr1[counter1];
                counter1++;
            } else {
                arr[i] = arr2[counter2];
                counter2++;
            }
            i++;
        }

        if (counter1 == arr1.length) {
            for (; counter2 != arr2.length; counter2 ++) {
                arr[i] = arr2[counter2];
                i++;
            }
            result = arr;
        } else {
            for (; counter1 != arr1.length; counter1 ++) {
                arr[i] = arr1[counter1];
                i++;
            }
            result =  arr;
        }
        return result;
    }

    /**
     *Uses linear search to find a specified value.
     *
     *@param arr array of integers to search through
     *@return whether the element was found
     */
    public static boolean linearSearch(int[] arr) {
        boolean found = false;
        int target = selectSearchTarget();
        for (int x : arr ) {
            if (x == target) {
                found = true;
                break;
            }
        }
       return found;
    }

    /**
     *Uses binary search to find a specified value.
     *
     *@param arr sorted array of integers to search through
     *@return whether the element was found
     */
    public static boolean binarySearch(int[] arr) {
        boolean found = false;
        int target = selectSearchTarget();
        float  mid = arr.length/2;
        float half = mid;
        while (true)
            if(mid <= arr.length-1 && target == arr[Math.round(mid)] ) {
                found = true;
                return found;
            } else if (half < 1 || mid < 1 || mid > arr.length - 1) {
                return found;
            } else if (target < arr[Math.round(mid)]) {
                half = Math.round(half / 2);
                mid -= half;
            } else { 
                half = Math.round(half / 2);;
                mid += half;
            }
    }
    
    /**
     *Helper method for search algorithms.
     *Get's target value from user, gets time stamp
     *
     *@return value to search for
     */
    private static int selectSearchTarget() {
        System.out.println("Select integer to search for: ");
        Scanner sea = new Scanner(System.in);
        int target = sea.nextInt();
        sea.close();
        ctime = System.currentTimeMillis();
        return target;
    }

    /**
     *Prints out first ten and last ten elements of array
     *Prints out time taken to run sort.
     *
     *@param sorted sorted array of integers
     *@param ctime initial timestamp taken before the sort
     */
    public static void printArray(int[] sorted){ 
        System.out.print("First 10 values: ");
        for (int i = 0; i < 8; i++) {
            System.out.print(sorted[i] + " ");
        }
        System.out.println(" ");
        System.out.print("Last 10 values: ");
        for (int i = sorted.length - 10; i < sorted.length; i++) {
            System.out.print(sorted[i] + " ");
        }
        System.out.println(" ");
        System.out.printf("The process took %d milliseconds \n", System.currentTimeMillis() - ctime);
    }
    public static void printTable(int[] file1, int[] file2, int[] file3) {

        int[] copy1 = new int[file1.length];
        int[] copy2 = new int[file2.length];
        int[] copy3 = new int[file3.length];
        System.arraycopy( file1, 0, copy1, 0, copy1.length );
        System.arraycopy( file2, 0, copy2, 0, copy2.length );
        System.arraycopy( file3, 0, copy3, 0, copy3.length );

        System.out.print("                        input1   input2    input3 \n");

        System.out.print("Selection sort  ");
        ctime = System.currentTimeMillis();
        int[] a = selectionSort(copy1);
        System.out.print("        ");
        System.out.print(System.currentTimeMillis() - ctime);
        ctime = System.currentTimeMillis();
        int[] b = selectionSort(copy2);
        System.out.print("        ");
        System.out.print(System.currentTimeMillis() - ctime);
        ctime = System.currentTimeMillis();
        int[] c = selectionSort(copy3);
        System.out.print("        ");
        System.out.print(System.currentTimeMillis() - ctime);
        System.out.print("\n");

        System.arraycopy( file1, 0, copy1, 0, copy1.length );
        System.arraycopy( file2, 0, copy2, 0, copy2.length );
        System.arraycopy( file3, 0, copy3, 0, copy3.length );

        System.out.print("Insertion sort  ");
        ctime = System.currentTimeMillis();
        insertionSort(copy1);
        System.out.print("        ");
        System.out.print(System.currentTimeMillis() - ctime);
        ctime = System.currentTimeMillis();
        insertionSort(copy2);
        System.out.print("        ");
        System.out.print(System.currentTimeMillis() - ctime);
        ctime = System.currentTimeMillis();
        insertionSort(copy3);
        System.out.print("        ");
        System.out.print(System.currentTimeMillis() - ctime);
        System.out.print("\n");

        System.arraycopy( file1, 0, copy1, 0, copy1.length );
        System.arraycopy( file2, 0, copy2, 0, copy2.length );
        System.arraycopy( file3, 0, copy3, 0, copy3.length );
        System.out.print("Merge sort      ");

        ctime = System.currentTimeMillis();
        mergeSort(file1);
        System.out.print("        ");
        System.out.print(System.currentTimeMillis() - ctime);
        ctime = System.currentTimeMillis();
        mergeSort(file2);
        System.out.print("        ");
        System.out.print(System.currentTimeMillis() - ctime);
        ctime = System.currentTimeMillis();
        mergeSort(file3);
        System.out.print("         ");
        System.out.print(System.currentTimeMillis() - ctime);
    }
    /**
     *Formats search algorithm output.
     *
     *@param found whether the inputted integer was found in the array.
     */
    public static void printSearch(boolean found) {
        System.out.printf("Target found:  %s \n", found );
        System.out.printf("The process took %d milliseconds \n", System.currentTimeMillis() - ctime);
    }
}
