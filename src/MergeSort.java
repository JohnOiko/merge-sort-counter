import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String input;
            ArrayList<String> numbers = new ArrayList<>();
            while ((input = reader.readLine()) != null) {
                numbers.addAll(Arrays.asList(input.split(" ")));
            }
            System.out.println("Total cost: " + MergeSortCost(numbers.toArray(new String[0])));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int MergeSortCost(String[] array) {
        int count = 0;
        if (array.length < 2) return 0;
        else {
            String[] A = Arrays.copyOf(array, array.length/2);
            String[] B = Arrays.copyOfRange(array, array.length/2, array.length);
            count += MergeSortCost(A);
            count += MergeSortCost(B);
            int i = 0, j = 0;
            while ((i < A.length) && (j < B.length)) {
                if (Integer.parseInt(A[i]) < Integer.parseInt(B[j])) {
                    array[i + j] = A[i];
                    i++;
                }
                else {
                    if (Math.abs(Integer.parseInt(A[i]) - Integer.parseInt(B[j])) > 1) count += 3;
                    else if (Math.abs(Integer.parseInt(A[i]) - Integer.parseInt(B[j])) == 1) count += 2;
                    array[i + j] = B[j];
                    j++;
                }
            }
            if (i == A.length) {
                while (i + j < array.length) {
                    array[i + j] = B[j];
                    j++;
                }
            }
            else if (j == B.length) {
                array[i + j] = A[i];
                i++;
                while (i < A.length) {
                    for (int k = j - 1 ; k >= 0 ; k--) {
                        if (Math.abs(Integer.parseInt(A[i]) - Integer.parseInt(B[k])) > 1) count += 3;
                        else if (Math.abs(Integer.parseInt(A[i]) - Integer.parseInt(B[k])) == 1) count += 2;
                    }
                    array[i + j] = A[i];
                    i++;
                }
            }
        }
        return count;
    }
}
