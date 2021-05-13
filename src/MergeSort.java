import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Main class that reads the numbers from the file and prints the cost of ordering them.
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

        // Initialize cost to 0 and add to it the costs of the left (first) and right (second) halves of the Arraylist numbers
        // using recursion.
        long cost = 0;
        cost += MergeSortCost(numbers, start, mid);
        cost += MergeSortCost(numbers, mid, end);

        // Create a new Arraylist to hold the merged numbers so as not to change the original Arraylist until the end.
        ArrayList<Integer> merged = new ArrayList<>();
        // i is the index of the first half and j the index of the second half of the Arraylist numbers.
        int i = start, j = mid;
        // While both indexes haven't reached the end of their half, adjust the cost if needed and add the corresponding numbers
        // to the Arraylist merged.
        while (i < mid && j < end) {
            int dif = numbers.get(i) - numbers.get(j);
            // if numbers[i] - numbers[j] = 1, adjust the cost and add numbers[j] to merged since it's less than numbers[i].
            if (dif == 1) {
                // Since numbers[i] > numbers[j] and the first half is already in ascending order, the numbers from i + 1 to mid
                // are all more than numbers[i] and thus more than numbers[j]. Also, because numbers[i] - numbers[j] = 1, a while
                // loop adds 2 to the cost while numbers[k] = numbers[i] for k = i, ..., i + 1, ..., mid. Once numbers[k] != numbers[i],
                // 3 * (the amount of the rest of the elements of the first half which are at k <= index <= mid) is added to the cost
                // and the flag becomes false to break the loop.
                boolean flag = true;
                int k = i;
                while (k < mid && flag) {
                    // If numbers[k] = numbers[i], add 2 to the cost.
                    if (numbers.get(k) - numbers.get(j) == 1) {
                        cost += 2;
                        k++;
                    }
                    // Else if numbers[k] != numbers[i], add 3 * (the amount of the rest of the elements of the first half which are at
                    // k <= index <= mid) to the cost and make the flag false to break the loop.
                    else {
                        cost += 3L * (mid - k);
                        flag = false;
                    }
                }
                // Add numbers[j] to the Arraylist merged, because it's less than numbers[i].
                merged.add(numbers.get(j));
                // Since we added an element from the second half to the Arraylist merged, move on to the next element of the second half
                // by increasing j by 1.
                j++;
            }
            // Else if numbers[i] - numbers[j] > 1, since numbers[i] > numbers[j] and the first half is already in ascending order, the
            // numbers from i + 1 to mid are all larger than numbers[j] by at least 2. Thus, 3 * (the amount of the rest of the elements
            // of the first half which are at i <= index <= mid) is added to the cost and numbers[j] is added to the Arraylist merged,
            // because it's less than numbers[i]. Also, since we added an element from the second half to the Arraylist merged, move
            // on to the next element of the second half by increasing j by 1.
            else if (dif > 1) {
                cost += 3L * (mid - i);
                merged.add(numbers.get(j));
                j++;
            }
            // Else if numbers[i] - numbers[j] < 1 (so numbers[i] - numbers[j] <= 0 since numbers are all Integers), add numbers[i] to
            // the Arraylist merged and since we added an element from the first half to the Arraylist merged, move on to the next
            // element of the first half by increasing i by 1 without adjusting the cost since there is no inversion.
            else {
                merged.add(numbers.get(i));
                i++;
            }
        }
        // After the previous outer loop, there will still be unmerged elements either in the first or the second half of the
        // Arraylist numbers, but not both. If there are still unmerged elements in the first half (i hasn't reached mid), add
        // them all to the Arraylist merged.
        while (i < mid) {
            merged.add(numbers.get(i));
            i++;
        }
        // Similarly to the previous while loop, if there are still unmerged elements in the second half (j hasn't reached end),
        // add them all to the Arraylist merged.
        while (j < end) {
            merged.add(numbers.get(j));
            j++;
        }
        // Now that all of the elements of the Arraylist numbers that had to be ordered have been merged in the Arraylist merged
        // and are in ascending order in that Arraylist, replace the previous elements of the Arraylist numbers with the
        // corresponding elements of the Arraylist merged.
        for (int p = 0 ; p < merged.size() ; p++) {
            numbers.set(start + p, merged.get(p));
        }
        // Return the calculated cost, the Arraylist numbers that was passed as a parameter is now ordered in ascending order
        // when accessed by the code that called this function.
        return cost;
    }
}