import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    static class Element {
        private int in;
        private int e1;
        private int e2;

        Element(int in,int e1, int e2) {
            this.in = in;
            this.e1 = e1;
            this.e2 = e2;
        }

        public int getIn() {
            return in;
        }

        public int getE1() {
            return e1;
        }

        public int getE2() {
            return e2;
        }

        public void setE1(int e1) {
            this.e1 = e1;
        }

        public void setE2(int e2) {
            this.e2 = e2;
        }

    }

    public static class ElementsComparator implements Comparator<Element> {

        @Override
        public int compare(Element o, Element th) {

            System.out.printf("%d %d - %d %d\n",o.getE1(),o.getE2(), th.getE1(), th.getE2());
            if(o.getE1() < th.getE1())
                return 1;
            else if(o.getE1() > th.getE1())
                return 0;
            else {
                if(o.getE2() >= th.getE2())
                    return 1;
                else if(o.getE2() < th.getE2())
                    return 0;
            }
            return 1;

        }

    }
    public static void main(String[] args) {
	// write your code here
        Scanner input = new Scanner(System.in);
        ArrayList<Element> arr  = new ArrayList<>();

        int n = input.nextInt();

        for(int i = 0; i < n; i++)
            arr.add(new Element(i+1,input.nextInt(), input.nextInt()));

        ElementsComparator elementsComparator = new ElementsComparator();

        arr.sort(elementsComparator);
        for(int i = 0; i < n-1; i++)
            System.out.printf("%d ",arr.get(i).getIn());
            System.out.printf("%d ",arr.get(n-1).getIn());

    }
}
