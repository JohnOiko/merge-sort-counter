import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MergeSort {

    public static void main(String[] args) {
        //Read numbers from file.
        long exeTime = System.nanoTime();
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            Scanner input = new Scanner(reader);
            ArrayList<Integer> numbers = new ArrayList<>();
            /* While EOF hasn't been reached, split the next line with " " and add it as a
            String Arraylist to the Arraylist Numbers that holds the inputted numbers as Strings. */
            while (input.hasNextInt()) numbers.add(input.nextInt());
            /* Calculate and print the cost by passing the Arraylist Numbers as an array of Strings
            to the function MergeSortCost(String[] array). */
            System.out.println("Total cost: " + MergeSortCost(numbers));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println((System.nanoTime() - exeTime)/1000000.0 + "ms");
    }

    /* Function that calculates and returns the cost of sorting the String array it accepts as parameter
    using a modified version of MergeSort. */
    private static int MergeSortCost(ArrayList<Integer> numbers) {
        int cost = 0;
        //If the given array has 0 or 1 elements, return 0 as the cost.
        if (numbers.size() < 2) return 0;
        //If array has at least 2 elements, calculate the cost of sorting it.
        else {
            /* The first half of the array is stored in firstHalf using Arrays.copyOf and
            the second half is stored in secondHalf using Arrays.copyOfRange. */
            ArrayList<Integer> firstHalf = new ArrayList<>(numbers.subList(0, numbers.size()/2));
            ArrayList<Integer> secondHalf = new ArrayList<>(numbers.subList(numbers.size()/2, numbers.size()));
            /* Recursively calculate the cost of the first and second halves and add it to the
            total cost. */
            cost += MergeSortCost(firstHalf);
            cost += MergeSortCost(secondHalf);
            //i is the index of the first half and j is the index of the seconds half.
            int i = 0, j = 0;
            //Merge the two halves until one of the halves becomes empty.
            while ((i < firstHalf.size()) && (j < secondHalf.size())) {
                /* If the element of the first half is less then the element of the second half,
                store it in the array passed as parameter and increment its index. */
                if (firstHalf.get(i) < secondHalf.get(j)) {
                    numbers.set(i + j, firstHalf.get(i));
                    i++;
                }
                /* Else if the element of the first half is greater than or equal to the element of the second half, adjust
                the cost, store element of the first half in the array passed as parameter and increment its index. */
                else {
                    /* Since the element of the first half is greater or equal than the element of the second,
                    we have Α_Ι > A_J, while i < j. */
                    // If firstHalf[i] - secondHalf[j] > 1, add 3 to the cost.
                    if (firstHalf.get(i) - secondHalf.get(j) > 1) cost += 3;
                    // Else if firstHalf[i] - secondHalf[j] = 1, add 2 to the cost.
                    else if (firstHalf.get(i) - secondHalf.get(j) == 1) cost += 2;
                    numbers.set(i + j, secondHalf.get(j));
                    j++;
                }
            }
            /* If the first half is empty, copy the rest of the elements of the second half to the array passed
            as parameter. */
            if (i == firstHalf.size()) {
                while (i + j < numbers.size()) {
                    numbers.set(i + j, secondHalf.get(j));
                    j++;
                }
            }
            /* Else if the second half is empty, adjust the cost and copy the rest of the elements of the first
            half to the array passed as parameter. */
            else if (j == secondHalf.size()) {
                /* Copy the first of the remaining elements of the first half to the array passed as parameter
                without adjusting the cost since it was already accounted for by comparing it to the last element
                of the second half. */
                numbers.set(i + j, firstHalf.get(i));
                i++;
                /* For the rest of the elements of the first half, compare them to all the elements of the second half,
                adjust the cost and copy the first half element to the array passed as parameter (since we know that all
                of the rest of the elements of the first half are greater than all the elements of the second half). */
                while (i < firstHalf.size()) {
                    // Since we know the above, we have Α_Ι > A_K, while i < k.
                    int k = j - 1;
                    boolean flag = true;
                    while (k >= 0 && flag) {
                        // If firstHalf[i] - secondHalf[k] = 1, add 2 to the cost.
                        if (firstHalf.get(i) - secondHalf.get(k) == 1)  cost += 2;
                        /* Else if firstHalf[i] - secondHalf[k] > 1, add 3 * (the amount of elements left in the second
                        half) to the cost (since the second half is already sorted we know that the remaining elements
                        are less than secondHalf[k]) and exit the loop by changing the flag. */
                        else if (firstHalf.get(i) - secondHalf.get(k) > 1) {
                            cost += 3 * (k + 1);
                            flag = false;
                        }
                        k--;
                    }
                    numbers.set(i + j, firstHalf.get(i));
                    i++;
                }
            }
        }
        return cost;
    }
}
