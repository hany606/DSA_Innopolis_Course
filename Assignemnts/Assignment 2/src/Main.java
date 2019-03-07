import javafx.util.Pair;

import java.util.*;


/**
 * Data Structure & Algorithms Course (DSA)
 * Innopolis University, Sprinng 2019
 * Assignment 2
 * @author Hany Hamed
 */

/**
 * References:
 * https://www.wikiwand.com/en/String_metric
 * https://www.youtube.com/watch?v=aEIhvv5p-V8
 * https://www.wikiwand.com/en/Damerau%E2%80%93Levenshtein_distance
 * https://www.youtube.com/watch?v=uYH6fSQJmuw
 */


public class Main {

    /**
     * This method is used to estimate the cost of changing the source word to the target one using only:
     * Deletion, Insertion, Swap.
     * This method is an implementation of OSA (Optimal string alignment distance) Algorithm
     * @param word1 This is the source word
     * @param word2 This is the target word
     * @return int the cost
     */
    public static int estimate(String word1, String word2) {
        int n = word1.length();     //Store the length of the source word (word1 -> source)
        int m = word2.length();     //Store the length of the target word (word2 -> target)
        int cost = 0;               //Store the cost of the changing

        //If the source word or the target word is zero it will return the length of the other word as cost for insertion operations
        if(n == 0)
            return m;
        if(m == 0)
            return n;

        //Create a table to store the previous cost for each sub-problem as the sub-problem is a substrings from the words
        int[][] table = new int[n+1][m+1];

        //Initialize the array by making the first row and the first column indicate the indexes as they are represent the cost of inserting to substrings of source to be a substring of target
        for(int i = 0; i < n+1; i++)
            table[i][0] = i;
        for(int i = 0; i < m+1; i++)
            table[0][i] = i;

        // Using DP approach of memomization to create the rest of the table
        for(int i = 1; i < n+1; i++) {
            for(int j = 1; j < m+1; j++) {
                //If the letters of the corresponding indexes are in the correct place, cost = 0
                //Otherwise the cost = 1 as inserting
                if(word1.charAt(i-1) == word2.charAt(j-1))
                    cost = 0;
                else
                    cost = 1;

                //Store the cost to convert the substring of source word[0,i] to the target word[0,j] as minimum value of
                //performing inserting or deletion and take the use of the memomization table
                table[i][j] = Math.min(table[i][j-1]+1, Math.min(table[i-1][j]+1,table[i-1][j-1]+cost));

                //Check if the two elements in the string should be swapped or not
                if((i > 1 && j > 1) && word1.charAt(i-1) == word2.charAt(j-2) && word1.charAt(i-2) == word2.charAt(j-1))
                    table[i][j] = Math.min(table[i][j],table[i-2][j-2]+cost); //Take the minimum with performing swapping or not

            }
        }

        return table[n][m];
    }

    /**
     * This method is used to take the input and print the output for the first task
     * Used to calculate the distance between two words (source, target) [The cost to change from source to target]
     * @param input This is the Scanner object to take the input
     */
    public static void task21(Scanner input) {
        int n = input.nextInt();
        for(int  i = 0; i < n; i++) {
            String source = input.next();
            String target = input.next();
            System.out.println(estimate(source,target));
        }
    }

    /**
     * This method is used to take the input and print the output for the second task
     * Used to make correction for a word from a given dictionary word according to the closest word to it
     * @param input This is the Scanner object to take the input
     */
    public static void task22(Scanner input) {
        int n = input.nextInt();        //Read the number of words in the dictionary
        ArrayList<Pair<String,Integer>> dictionary = new ArrayList<>(); //Create Array list which each element is pair which consists of the word and the cost to change the source word to it
        ArrayList<String> correction = new ArrayList<>();   //Array list to collect all the words that can be correction words for the source

        //Read the input
        for(int i = 0; i < n; i++)
            dictionary.add(new Pair<>(input.next(),0));

        String target = input.next();

        // Initialize the input with the first cost between the target and first word in the dictionary
        //It will not be different if we interchange the parameters of source and the target words, as they will be the same cost
        int mn = estimate(target,dictionary.get(0).getKey());

        //Calculate the cost for each word in the dictionary with the target and find the minimum cost
        for(int i = 0; i < n; i++) {
            int estimatedDifference = estimate(target,dictionary.get(i).getKey());
            mn = Math.min(mn,estimatedDifference);
            dictionary.set(i,new Pair<>(dictionary.get(i).getKey(),estimatedDifference));
        }

        //Collect the correct correction and put them in a list
        for(int i = 0; i < n; i++)
            if(mn == dictionary.get(i).getValue())
                correction.add(dictionary.get(i).getKey());

        Collections.sort(correction);   //Sort the correct list in Lexicographically order

        boolean spaceFlag = false;      //Initialize a flag to print the correct format without a space in the end -- It can be deleted and just print the first element outside the loop without a space

        for(int i = 0; i < correction.size(); i++) {
            if(spaceFlag)
                System.out.print(" ");
            System.out.print(correction.get(i));
            spaceFlag = true;

        }
        System.out.println();
    }

    /**
     * This method is used to parse the line of words to separate words
     * @param s This is big line of input
     * @return ArrayList<String> List with the words inside the big line of input
     */
    public static ArrayList<String> parseString(String s) {
        ArrayList<String> list = new ArrayList<>();     //Create empty list
        String tmp = "";    //Initialize a temporary variable to store the substring until we reach point to tak as a word
        //Iterate over the line of the string
        for(int i = 0; i < s.length(); i++) {
            //If the character is not an alphabet it means the end of the substring
            if(!(s.charAt(i) >= 'a' && s.charAt(i) <= 'z')) {
                //If the length is not zero, add in the list. In order to avoid the empty strings in the list after the punctuation marks
                if(tmp.length() != 0) {
                    list.add(tmp);
                    tmp = "";   //reset the tmp variable
                }
                continue;   //Continue to the next iteration
            }

            tmp += s.charAt(i); //Push in the tmp variable the current character
        }
        list.add(tmp); //Add what is the rest in the tmp to the list
        return list;
    }

    /**
     * This method is used to take the input and print the output for the Third task
     * This is used to correct sentence of words according to a source line
     * @param input This is the Scanner object to take the input
     */
    public static void task23(Scanner input) {
        //Read the input
        String source = input.nextLine();
        String target = input.nextLine();

        // value: cost,frequency
        HashMap<String,Pair<Integer,Integer>> dictionary = new HashMap<>(); //Create a HashMap where the key is a word in the source text and the value is pair to store the cost for a specific word in the target and the frequency of it

        ArrayList<String> tmp = parseString(source);    //List have all the seperated words in the line of the source
        String tmpss = "";
        for(int i = 0; i < tmp.size(); i++) {
            tmpss = tmp.get(i);
            //update the frequency and set the table
            dictionary.put(tmpss,new Pair<>(0,(dictionary.containsKey(tmpss) ? dictionary.get(tmpss).getValue()+1: 0)));
        }


        String tmps = "";
        //iterate over the target line
        for(int i = 0; i < target.length()+1; i++) {

            //If it is the end of the line or the character is not an alphabet, the substring in tmps is a separate word
            if(i == target.length() || (!(target.charAt(i) >= 'a' && target.charAt(i) <= 'z'))) {
                //If the length is not zero it will calculate the cost between the word and every word in the source line
                if(tmps.length() != 0) {
                    int mn = estimate(tmps, tmpss);

                    //Iterate over the HashMap
                    for (Map.Entry<String, Pair<Integer, Integer>> entry : dictionary.entrySet()) {
                        //Calculate the cost between the words in the source line and the current word in the target line
                        int estimatedDifference = estimate(tmps, entry.getKey());
                        mn = Math.min(mn, estimatedDifference);
                        dictionary.put(entry.getKey(), new Pair<>(estimatedDifference, dictionary.get(entry.getKey()).getValue())); //Update the element with the cost
                    }

                    String correctKey = ""; //It will store one of the correct words from the source line
                    int mxFreq = -1;        //to choose the most frequent word and with less cost

                    //Iterate over the HashMap to choose the most suitable word depending on the frequency and the cost
                    for (Map.Entry<String, Pair<Integer, Integer>> entry : dictionary.entrySet()) {
                        if(mn == dictionary.get(entry.getKey()).getKey()) {
                            if (mxFreq < dictionary.get(entry.getKey()).getValue())
                                correctKey = entry.getKey();
                        }
                    }

                    System.out.print(correctKey);   //Print the correct word in its correct place
                    tmps = "";  //Reset the temporary variable
                }
                //This is made in order to protect the program in accessing empty space
                if(i == target.length())
                    break;

                System.out.print(target.charAt(i));
                continue;
            }

            tmps += target.charAt(i);   //Add to the substring
        }

    }

    /**
     * This is just a Class to store the conditions, rules and the cost of the new rule for operations on the strings
     * to calculate the cost on predefined rule
     * Rules -> allowable actions on the strings (e.g. swapping, ...etc)

     */
    public static abstract class Misspelling {
        private int cost;           //Store the cost
        private int rowIndex;       //Store the row factor index to define the rule
        private int columnIndex;    //Store the Column factor index to define the rule
        public abstract boolean misspellingRule(int i, int j, String word1, String word2);  //This will be the condition

        Misspelling(int cost, int rowIndex, int columnIndex){
            this.cost = cost;
            this.rowIndex = rowIndex;
            this.columnIndex = columnIndex;
        }

        public int getCost() {
            return cost;
        }

        public int getRowIndex() {
            return rowIndex;
        }

        public int getColumnIndex() {
            return columnIndex;
        }



    }

    /**
     * This method is used to estimate the cost of changing the source word to the target one using only:
     * Deletion, Insertion, Swap.
     * This method is an implementation of OSA (Optimal string alignment distance) Algorithm
     * The same implementation of estimate Method but with modification to accept new modified rules
     * Rules -> allowable actions on the strings (e.g. swapping, ...etc)
     * @param word1 This is the source word
     * @param word2 This is the target word
     * @return int the cost
     * @param ms the list of the new rules

     */
    public static int estimateBonus(String word1, String word2, Misspelling[] ms) {
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

                //Iterate over the list of new defined rules and calculate the minimum cost
                for(int x = 0; x < ms.length; x++){
                    if(ms[x].misspellingRule(i,j,word1,word2))
                        table[i][j] = Math.min(table[i][j], table[i+ms[x].getRowIndex()][j+ms[x].getColumnIndex()]+ms[x].getCost());
                }

            }
        }

        return table[n][m];
    }

    /**
     * The modified version of estimate in Task 1
     * @param input This is the Scanner object to take the input
     * @param ms the list of the new rules
     */    public static void bonusTask(Scanner input,Misspelling[] ms) {
        int n = input.nextInt();
        for(int  i = 0; i < n; i++) {
            String source = input.next();
            String target = input.next();
            System.out.println(estimateBonus(source,target,ms));
        }
    }


    public static void main(String[] args) {
	// write your code here

        Scanner input = new Scanner(System.in);
        //Test Cases are provided in the contest in Codeforces and the Examples in the PDF

        //Task 2.1
//        task21(input);

        //Task 2.2
//        task22(input);

        //Task 2.3
        task23(input);


        //Extra task
        /*
        //Creating the rules
        Misspelling[] ms = new Misspelling[2];
        ms[0] = new Misspelling(1,-2,-2) {
            @Override
            public boolean misspellingRule(int i, int j, String word1, String word2) {
                if((i > 1 && j > 1) && word1.charAt(i-1) == word2.charAt(j-2) && word1.charAt(i-2) == word2.charAt(j-1))
                    return true;
                return false;
            }
        };

        //Create a list with allowed phoneticList changes from 1 letter to 2 letters
        ArrayList<Pair<Character,String>> phoneticList12C = new ArrayList<>();
        phoneticList12C.add(new Pair<>('f',"ph"));
        phoneticList12C.add(new Pair<>('l',"el"));


        // Adding phonetic Misspelling rule
        ms[1] = new Misspelling(1,0,0) {
            @Override
            public boolean misspellingRule(int i, int j, String word1, String word2) {
                if(i < word1.length() && j < word2.length()){
                    for(int x = 0; x < phoneticList12C.size(); x++)
                        if(word1.charAt(x) == phoneticList12C.get(x).getKey() && word2.substring(x,2) == phoneticList12C.get(x).getValue())
                            return true;
                }
                return false;
            }
        };

        bonusTask(input,ms);

    */
    }

}
