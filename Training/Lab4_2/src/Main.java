

import javafx.util.Pair;

import java.io.PrintStream;
import java.util.*;


public class Main {


    static void swap(int[] list, int i, int j)
    {
        int ind = list[i];
        list[i] = list[j];
        list[j] = ind;
    }

    public static void main(String[] args) {
        // write your code here
        Scanner input = new Scanner(System.in);
        PrintStream output = new PrintStream(System.out);
        HashMap<String, Boolean> hmap = new HashMap<>();
        HashMap<String, Boolean> hmap2 = new HashMap<>();
        int n = input.nextInt();

        for(int i = 0; i < n; i++) {
            hmap.put(input.next(),true);
        }

        n = input.nextInt();

        for(int i = 0; i < n; i++) {
            String s = input.next();
            if(hmap.get(s) == null)
                hmap2.put(s,true);
        }
        System.out.println(hmap2.size());
        Iterator it = hmap2.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey());
        }
    }
}
