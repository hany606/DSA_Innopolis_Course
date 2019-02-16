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
        HashMap<String, Integer> hmap = new HashMap<String, Integer>();
        int n = input.nextInt();
        for(int i = 0; i < n; i++) {
            String s = input.next();
            //here maybe we need to check if there is a already key or not (Maybe not lick c++)
            if(hmap.get(s) == null) {
                hmap.put(s, 1);
                continue;
            }
            hmap.put(s,hmap.get(s)+1);

        }

        List<Pair<String, Integer>> list = new ArrayList<>();
        Iterator it = hmap.entrySet().iterator();
        int si = hmap.size();
        for(int i = 0; i < si; i++)
        {
            int mx = 0;
            String mxK = new String();
            it = hmap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                if(mx < (int) pair.getValue()) {
                    mxK = (String)pair.getKey();
                    mx = (int) pair.getValue();
                }
                else if(mx == (int) pair.getValue()) {
                    if(mxK.compareTo((String) pair.getKey()) > 0) {
                        mxK = (String) pair.getKey();
                        mx = (int) pair.getValue();
                    }
                }
            }
            list.add(new Pair<String, Integer>(mxK,mx));
            hmap.remove(mxK);
        }
        for(int i = 0 ; i < list.size(); i++) {
            System.out.printf("%s %d\n",list.get(i).getKey(),list.get(i).getValue());
        }
    }
}
