import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

public class Recommender{

    /********************************
     * Do not change below code
     ********************************/

    int swaps, compares;
    int[] inversionCounts;
    String[] products;

    public Recommender(){
        swaps = 0;
        compares = 0;
    }

    public int getComapares() {
        return compares;
    }

    public int getSwaps() {
        return swaps;
    }
    /**************
     * This function is for the quick sort.
     **************/
    private boolean compare(int a ,int b){
        compares++;
        return a <= b;

    }
    /***************
     * This functions is for the quick sort.
     * By using this function, swap the similarity and the products at the same time.
     *****************/
    private void swap(int[] arr, int index1, int index2){
        swaps++;
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;

        String tempS = products[index1];
        products[index1] = products[index2];
        products[index2] = tempS;

    }

    /********************************
     * Do not change above code
     ********************************/

    /**
     * This function is for the calculate inversion counts of each option's.
     * @param dataset is file name of all data for hash table
     * @param options is the list of product name which we want to getting the inversion counts
     * @return it is integer array of each option's inversion counts. The order of return should be matched with options.
     */
    public int[] inversionCounts(String dataset, String[] options) {
        // TODO
        String[] Countproducts = options.clone();
        HashTable hash = new HashTable();
        int[] counts = new int[Countproducts.length];;
        try{
            hash.load(dataset);
            Product[] prods = new Product[Countproducts.length];

            //gets the Product objects from the wanted options
            for (int i = 0; i < Countproducts.length; i++) {
                prods[i] = hash.get(Countproducts[i]).value;
            }

            for (int i = 0; i < Countproducts.length; i++) {
                counts[i] = mergeSort(prods[i].depRating, 0, prods[i].depRating.length - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counts;
    }

    public int mergeSort(int[] array, int start, int end) {
        int switches = 0;
        if (start == end) {
            return 0;
        }
        int middle = (end - start) / 2;

        switches += mergeSort(array, start, end - middle - 1) + mergeSort(array, end - middle, end);

        while(true) {
            if (array[start] > array[end - middle]) {
                //System.out.println("switching this bitch "  + array[start] + " and " + array[end - middle]);
                int intermediate = array[end - middle];
                for (int i = end - middle - 1; i >= start; i--) {
                    switches++;
                    array[i + 1] = array[i];
                }
                array[start] = intermediate;
                middle--;
            }
            start++;
            if (middle < 0) {
                middle = 0;
            }
            if (start == end - middle) {
                break;
            }
        }

        return switches;
    }

    /**
     * Get the sequence of recommendation from the dataset by sorting the inverse count.
     * Compare the similarity of depRating between RecentPurchase's and each option's.
     * Use inverse count to get the similarity of two array.
     * */
    public String[] recommend(String dataset, String recentPurchase, String[] options) {
        products = options.clone();
        // TODO
        int[] counts = new int[options.length];
        counts = inversionCounts(dataset, options);
        String[] purchase = new String[1];
        purchase[0] = recentPurchase;
        int[] purchaseCount = inversionCounts(dataset, purchase);
        for (int i = 0; i < counts.length; i++) {
            counts[i] = Math.abs(purchaseCount[0] - counts[i]);
        }
        //System.out.println("test length is " + products.length);
        for (int i = 0; i < counts.length; i++) {
            System.out.print(counts[i] + " ");
        }
        System.out.println();
        products = quickSort(products, counts, 0, products.length - 1);
        for (int i = 0; i < counts.length; i++) {
            System.out.print(counts[i] + " ");
        }
        System.out.println();
        //System.out.println("fo;iwan");
        return products;
    }

    public String[] quickSort(String[] stringArray, int[] intArray, int start, int end) {
        //System.out.println("length is " + stringArray.length);
        if (start < end) {
            int pivot = intArray[end];
            int i = start - 1;
            for (int j = start; j < end; j++) {
                if (compare(intArray[j],pivot)) {
                    i++;
                    swap(intArray, i, j);
                }
            }
            swap(intArray, end, i+1);
            stringArray = quickSort(stringArray, intArray, start, i);
            stringArray = quickSort(stringArray, intArray, i + 2, end);
        }
        return stringArray;
    }
}
