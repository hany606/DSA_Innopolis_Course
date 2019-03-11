import javafx.util.Pair;


//Lab 1: post order + something
//Lab 2: Prefix sum or Tree

import java.util.*;

public class Main {
//    public static Class Node{
//        private  Node prev;
//
//        prev = new Node();
//    }
    public static class Node implements Comparable<Node>{
        private Integer val;
        private Integer index;

        Node(int val, int index){
            this.val = val;
            this.index = index;
        }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getIndex() {
        return index;
    }

    public int getVal() {
        return val;
    }

    @Override
    public int compareTo(Node i) {
        return this.val.compareTo((Integer) i.getVal());
    }
}
    public static void main(String[] args) {
	// write your code here
        List<Node> list = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        for(int i = 0; i < n; i++)
            list.add(list.size(), new Node(input.nextInt(),i));

        Collections.sort(list);
        for(int i = 0; i < n; i++) {
            System.out.printf("%d %d \n",list.get(i).getVal(),list.get(i).getIndex());
        }

        int mid = n/2;
        for(int i = 0; i < mid; i++)

    }
}
