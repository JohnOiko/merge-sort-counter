import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        String[] numbers = {};
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String input;
            while ((input = reader.readLine()) != null) {
                ArrayList<String> temp = new ArrayList<>(Arrays.asList(numbers));
                temp.addAll(Arrays.asList(input.split(" ")));
                numbers = temp.toArray(new String[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Total cost: " + MergeSortCost(numbers));
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
