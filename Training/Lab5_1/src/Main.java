import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int W = input.nextInt();

        ArrayList<Pair<Integer,Integer>> list = new ArrayList<>();

        for(int i = 0 ; i < n; i++) {
            int w = input.nextInt();
            list.add(new Pair<w,0>);
        }


    }
}
