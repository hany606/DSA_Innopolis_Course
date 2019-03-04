import javafx.util.Pair;

import javax.swing.*;
import java.util.*;

/**
 * References:
 * https://www.wikiwand.com/en/String_metric
 * https://www.wikiwand.com/en/String_metric
 * https://www.wikiwand.com/en/String_metric
 *
 */


public class Main {

    public static int lev(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        if(n == 0)
            return m;
        if(m == 0)
            return n;

        int[][] table = new int[m+1][n+1];

        for(int i = 0; i < n+1; i++)
            table[0][i] = i;
        for(int i = 1; i < m+1; i++)
            table[i][0] = i;

        for(int j = 1; j < m+1; j++) {
            for(int i = 1; i < n+1; i++) {
                int cost = ((word1.charAt(i-1) == word2.charAt(j-1)) ? 0 : 1);
                table[j][i] = Math.min(table[j][i-1]+1, Math.min(table[j-1][i]+1,table[j-1][i-1]+cost));
            }
        }

        return table[m][n];
    }

    public static int estimate(String word1, String word2) {

        int counter = 0;

        int beforeCounter = lev(word1,word2);
            ArrayList<Integer> costList = new ArrayList<>();

            for(int i = 0; i < word1.length()-1; i++) {
                if(i == word2.length()-1) break;
                costList.add((word1.charAt(i) == word2.charAt(i + 1)) ? 1 : 0);
            }
            for(int i = 0; i < costList.size(); i++) {
                if(costList.get(i) == 1) {
                    counter++;
                    word1 = word1.substring(0,i)+ word1.charAt(i+1) + word1.charAt(i)+word1.substring(i+2);;//+word1.charAt(i);   // maybe give an error if the swap in the end (Test it)
                }
            }

        return Math.min(beforeCounter,counter + lev(word1,word2));
    }

    public static void task21(Scanner input) {
        int n = input.nextInt();
        for(int  i = 0; i < n; i++) {
            String source = input.next();
            String target = input.next();
            System.out.println(estimate(source,target));
        }
    }


    public static void task22(Scanner input) {
        int n = input.nextInt();
        ArrayList<Pair<String,Integer>> dictionary = new ArrayList<>();
        ArrayList<String> correction = new ArrayList<>();
        for(int i = 0; i < n; i++)
            dictionary.add(new Pair<>(input.next(),0));

        String target = input.next();

        int mn = estimate(target,dictionary.get(0).getKey());

        for(int i = 0; i < n; i++) {
            int estimatedDifference = estimate(target,dictionary.get(i).getKey());
            mn = Math.min(mn,estimatedDifference);
            dictionary.set(i,new Pair<>(dictionary.get(i).getKey(),estimatedDifference));
        }

        for(int i = 0; i < n; i++)
            if(mn == dictionary.get(i).getValue())
                correction.add(dictionary.get(i).getKey());

        Collections.sort(correction);
        boolean spaceFlag = false;
        for(int i = 0; i < correction.size(); i++) {
            if(spaceFlag)
                System.out.print(" ");
            System.out.print(correction.get(i));
            spaceFlag = true;

        }
        System.out.println();
    }

    public static ArrayList<String> parseString(String s) {
        ArrayList<String> list = new ArrayList<>();
        int pointer = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == ' ') {
                list.add(s.substring(pointer,i));
                pointer = i+1;
            }
        }
        return list;
    }

    public static void task23(Scanner input) {
        String source = input.next();
        String target = input.next();

        //We should use hashmap to get the frequency
        HashMap<String,Pair<Integer,Integer>> dictionary = new HashMap<>();
        ArrayList<String> correction = new ArrayList<>();

        ArrayList<String> tmp = parseString(source);
        String tmps = "";
        for(int i = 0; i < tmp.size(); i++) {
            tmps = tmp.get(i);
            //update the frequency and set the table
            dictionary.put(tmps,new Pair<>(0,(dictionary.containsKey(tmps) ? 1: 0)*dictionary.get(tmps).getValue()+1));
        }

        tmp = parseString(target);
        //Loop for each token in the string
        for(int i = 0; i < tmp.size(); i++) {

            String starget = tmp.get(i);
            
            // I used tmps to initialize the minimum variable
            int mn = estimate(starget, tmps);
            for (Map.Entry<String, Pair<Integer, Integer>> entry : dictionary.entrySet()) {
                //Todo: I think here we will have some function to filter the starget to remove ... and other characters
                int estimatedDifference = estimate(starget, entry.getKey());
                mn = Math.min(mn, estimatedDifference);
                //update the cost
                dictionary.put(entry.getKey(), new Pair<>(estimatedDifference, dictionary.get(entry.getKey()).getValue()));
            }

            //Todo: Continue
            //Choose the mose suitable correction from the most frequency and less cost
            //Print it
            //See the printing ... issue

        }
    }

    public static void main(String[] args) {
	// write your code here

        Scanner input = new Scanner(System.in);


        //Task 2.1
//        task21(input);

        //Task 2.2
//        task22(input);

        //Task 2.3
        task23(input);


    }
}
