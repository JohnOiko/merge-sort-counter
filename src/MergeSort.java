import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        //Read numbers from file.
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String input;
            ArrayList<String> numbers = new ArrayList<>();
            /* While EOF hasn't been reached, split the next line with " " and add it as a
            String Arraylist to the Arraylist Numbers that holds the inputted numbers as Strings. */
            while ((input = reader.readLine()) != null) {
                numbers.addAll(Arrays.asList(input.split(" ")));
            }
            /* Calculate and print the cost by passing the Arraylist Numbers as an array of Strings
            to the function MergeSortCost(String[] array). */
            long exeTime = System.nanoTime();
            System.out.println("Total cost: " + MergeSortCost(numbers.toArray(new String[0])));
            System.out.println((System.nanoTime() - exeTime)/1000000.0 + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Function that calculates and returns the cost of sorting the String array it accepts as parameter
    using a modified version of MergeSort. */
    private static int MergeSortCost(String[] array) {
        int cost = 0;
        //If the given array has 0 or 1 elements, return 0 as the cost.
        if (array.length < 2) return 0;
        //If array has at least 2 elements, calculate the cost of sorting it.
        else {
            /* The first half of the array is stored in firstHalf using Arrays.copyOf and
            the second half is stored in secondHalf using Arrays.copyOfRange. */
            String[] firstHalf = Arrays.copyOf(array, array.length/2);
            String[] secondHalf = Arrays.copyOfRange(array, array.length/2, array.length);
            /* Recursively calculate the cost of the first and second halves and add it to the
            total cost. */
            cost += MergeSortCost(firstHalf);
            cost += MergeSortCost(secondHalf);
            //i is the index of the first half and j is the index of the seconds half.
            int i = 0, j = 0;
            //Merge the two halves until one of the halves becomes empty.
            while ((i < firstHalf.length) && (j < secondHalf.length)) {
                /* If the element of the first half is less then the element of the second half,
                store it in the array passed as parameter and increment its index. */
                if (Integer.parseInt(firstHalf[i]) < Integer.parseInt(secondHalf[j])) {
                    array[i + j] = firstHalf[i];
                    i++;
                }
                /* Else if the element of the first half is greater than or equal to the element of the second half, adjust
                the cost, store element of the first half in the array passed as parameter and increment its index. */
                else {
                    /* Since the element of the first half is greater or equal than the element of the second,
                    we have Α_Ι > A_J, while i < j. */
                    // If firstHalf[i] - secondHalf[j] > 1, add 3 to the cost.
                    if (Integer.parseInt(firstHalf[i]) - Integer.parseInt(secondHalf[j]) > 1) cost += 3;
                    // Else if firstHalf[i] - secondHalf[j] = 1, add 2 to the cost.
                    else if (Integer.parseInt(firstHalf[i]) - Integer.parseInt(secondHalf[j]) == 1) cost += 2;
                    array[i + j] = secondHalf[j];
                    j++;
                }
            }
            /* If the first half is empty, copy the rest of the elements of the second half to the array passed
            as parameter. */
            if (i == firstHalf.length) {
                while (i + j < array.length) {
                    array[i + j] = secondHalf[j];
                    j++;
                }
            }
            /* Else if the second half is empty, adjust the cost and copy the rest of the elements of the first
            half to the array passed as parameter. */
            else if (j == secondHalf.length) {
                /* Copy the first of the remaining elements of the first half to the array passed as parameter
                without adjusting the cost since it was already accounted for by comparing it to the last element
                of the second half. */
                array[i + j] = firstHalf[i];
                i++;
                /* For the rest of the elements of the first half, compare them to all the elements of the second half,
                adjust the cost and copy the first half element to the array passed as parameter (since we know that all
                of the rest of the elements of the first half are greater than all the elements of the second half). */
                while (i < firstHalf.length) {
                    // Since we know the above, we have Α_Ι > A_K, while i < k.
                    int k = j - 1;
                    boolean flag = true;
                    while (k >= 0 && flag) {
                        // If firstHalf[i] - secondHalf[k] = 1, add 2 to the cost.
                        if (Integer.parseInt(firstHalf[i]) - Integer.parseInt(secondHalf[k]) == 1)  cost += 2;
                        /* Else if firstHalf[i] - secondHalf[k] > 1, add 3 * (the amount of elements left in the second
                        half) to the cost (since the second half is already sorted we know that the remaining elements
                        are less than secondHalf[k]) and exit the loop by changing the flag. */
                        else if (Integer.parseInt(firstHalf[i]) - Integer.parseInt(secondHalf[k]) > 1) {
                            cost += 3 * (k + 1);
                            flag = false;
                        }
                        k--;
                    }
                    array[i + j] = firstHalf[i];
                    i++;
                }
            }
        }
        return cost;
    }
}
