import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MergeSort {
    public static void main(String[] args) {
        // Read numbers from file using a Scanner.
        try (Scanner input = new Scanner(new File(args[0]))) {
            // While there are still integers in the file, keep reading them and adding them to an Arraylist.
            ArrayList<Integer> numbers = new ArrayList<>();
            while (input.hasNextInt()) {
                numbers.add(input.nextInt());
            }
            // Print the cost MergeSortCost returns with the Arraylist of given integers as parameter.
            System.out.println("Total cost: " + MergeSortCost(numbers, 0, numbers.size()));
        } catch (FileNotFoundException e) {
            // If there is an exception when reading the integers from the file, print the stack trace.
            e.printStackTrace();
        }
    }

    // Function that returns the cost of sorting the Arraylist it accepts as parameter using a modified version of MergeSort.
    private static long MergeSortCost(ArrayList<Integer> numbers, int start, int end) {
        // mid is an index to the half way point of the Arraylist.
        int mid = start + (end - start)/2;
        // If the Arraylist numbers has 1 or 0 elements, break the recursion and return 0 as cost.
        if (end - start <= 1) return 0;

        // Initialize cost to 0 and add to it the costs of the left and right parts of the Arraylist numbers using a recursion.
        long cost = 0;
        cost += MergeSortCost(numbers, start, mid);
        cost += MergeSortCost(numbers, mid, end);

        // Create a new Arraylist to hold the merged numbers so as not to change the original Arraylist until the end.
        ArrayList<Integer> merged = new ArrayList<>();
        int i = start, j = mid;
        while (i < mid && j < end) {
            int dif = numbers.get(i) - numbers.get(j);
            if (dif == 1) {
                boolean flag = true;
                int k = i;
                while (k < mid && flag) {
                    if (numbers.get(k) - numbers.get(j) == 1) {
                        cost += 2;
                        k++;
                    }
                    else {
                        cost += 3L * (mid - k);
                        flag = false;
                    }
                }
                merged.add(numbers.get(j));
                j++;
            }
            else if (dif > 1) {
                cost += 3L * (mid - i);
                merged.add(numbers.get(j));
                j++;
            }
            else {
                merged.add(numbers.get(i));
                i++;
            }
        }
        while (i < mid) {
            merged.add(numbers.get(i));
            i++;
        }
        while (j < end) {
            merged.add(numbers.get(j));
            j++;
        }
        for (int p = 0 ; p < merged.size() ; p++) {
            numbers.set(start + p, merged.get(p));
        }
        return cost;
    }
}