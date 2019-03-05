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

    public static int estimate(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int cost = 0;

        if(n == 0)
            return m;
        if(m == 0)
            return n;

        int[][] table = new int[n+1][m+1];

        for(int i = 0; i < n+1; i++)
            table[i][0] = i;
        for(int i = 0; i < m+1; i++)
            table[0][i] = i;

        for(int i = 1; i < n+1; i++) {
            for(int j = 1; j < m+1; j++) {

                if(word1.charAt(i-1) == word2.charAt(j-1))
                    cost = 0;
                else
                    cost = 1;

                table[i][j] = Math.min(table[i][j-1]+1, Math.min(table[i-1][j]+1,table[i-1][j-1]+cost));

                if((i > 1 && j > 1) && word1.charAt(i-1) == word2.charAt(j-2) && word1.charAt(i-2) == word2.charAt(j-1))
                    table[i][j] = Math.min(table[i][j],table[i-2][j-2]+cost);

            }
        }

        return table[n][m];
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
        String tmp = "";
        for(int i = 0; i < s.length(); i++) {
            if(!(s.charAt(i) >= 'a' && s.charAt(i) <= 'z')) {
//                System.out.print(s.charAt(i));
                if(tmp.length() != 0) {
                    list.add(tmp);
                    tmp = "";
                }
                continue;
            }

            tmp += s.charAt(i);
        }
        list.add(tmp);
        return list;
    }

    public static void task23(Scanner input) {

        String source = input.nextLine();
        String target = input.nextLine();

        //We should use hashmap to get the frequency
        // value: cost,frequency
        HashMap<String,Pair<Integer,Integer>> dictionary = new HashMap<>();
        ArrayList<String> correction = new ArrayList<>();

        ArrayList<String> tmp = parseString(source);
        String tmpss = "";
        for(int i = 0; i < tmp.size(); i++) {
            tmpss = tmp.get(i);
            //update the frequency and set the table
            dictionary.put(tmpss,new Pair<>(0,(dictionary.containsKey(tmpss) ? dictionary.get(tmpss).getValue()+1: 0)));
//            System.out.println(tmpss);
        }



        String tmps = "";
        for(int i = 0; i < target.length()+1; i++) {
//            System.out.printf("/%c/",target.charAt(i));
            if(i == target.length() || (!(target.charAt(i) >= 'a' && target.charAt(i) <= 'z'))) {
                if(tmps.length() != 0) {
                    int mn = estimate(tmps, tmpss);
                    for (Map.Entry<String, Pair<Integer, Integer>> entry : dictionary.entrySet()) {
                        //Todo: I think here we will have some function to filter the starget to remove ... and other characters
                        int estimatedDifference = estimate(tmps, entry.getKey());
                        mn = Math.min(mn, estimatedDifference);
                        //update the cost
                        dictionary.put(entry.getKey(), new Pair<>(estimatedDifference, dictionary.get(entry.getKey()).getValue()));
                    }

//                    System.out.println("-----------------------");
//                    System.out.println(mn);
//                    System.out.println(tmps);
//                    for (Map.Entry<String, Pair<Integer, Integer>> entry : dictionary.entrySet()) {
//                        System.out.println(entry);
//                    }
//                    System.out.println("-----------------------");


                    String correctKey = "";
                    int mxFreq = -1;
                    for (Map.Entry<String, Pair<Integer, Integer>> entry : dictionary.entrySet()) {
                        if(mn == dictionary.get(entry.getKey()).getKey()) {
                            if (mxFreq < dictionary.get(entry.getKey()).getValue())
                                correctKey = entry.getKey();
                        }
                    }

                    System.out.print(correctKey);
//                    System.out.println("********************");

                    tmps = "";
                }
                if(i == target.length())
                    break;
                System.out.print(target.charAt(i));

                continue;
            }

            tmps += target.charAt(i);
        }

//        tmp = parseString(target);
//        //Loop for each token in the string
//        for(int i = 0; i < tmp.size(); i++) {
//
//            String starget = tmp.get(i);
//
//            // I used tmps to initialize the minimum variable
//            int mn = estimate(starget, tmps);
//            for (Map.Entry<String, Pair<Integer, Integer>> entry : dictionary.entrySet()) {
//                //Todo: I think here we will have some function to filter the starget to remove ... and other characters
//                int estimatedDifference = estimate(starget, entry.getKey());
//                mn = Math.min(mn, estimatedDifference);
//                //update the cost
//                dictionary.put(entry.getKey(), new Pair<>(estimatedDifference, dictionary.get(entry.getKey()).getValue()));
//            }
//
//            //Todo: Continue
//            //Choose the mose suitable correction from the most frequency and less cost
//            //Print it
//            //See the printing ... issue

//        }
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
