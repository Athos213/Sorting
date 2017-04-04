import java.io.* ;
import java.util.Scanner;

/**
 *This static class prints out a table of sorting algorithms
 *
 *@author Stanislav Lyakhov
 *
 *@version 1.1.0
 */
public class Table {

    public static long ctime;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan1 = new Scanner(new File("input1.txt"));
        int[] file1 =  Sort.parse(scan1.nextLine().split(","));
        scan1.close();
        Scanner scan2 = new Scanner(new File("input2.txt"));
        int[] file2 =  Sort.parse(scan2.nextLine().split(","));
        scan2.close();
        Scanner scan3 = new Scanner(new File("input3.txt"));
        int[] file3 =  Sort.parse(scan3.nextLine().split(","));
        scan3.close();
        Sort.printTable(file1, file2, file3);
    }
}
