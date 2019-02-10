import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    //Shutting yard algorithm
    //Right Answer:  2 * ( 1 * 5 + 2 * ( 1 + 5 ) ) xc
    //Right Answer: ( 5 * ( 2 / 5  * 1 + 5 ) ) xc
    //5 * ( ( 12 + 222 ) - 5  / 5 ) * 12 xc

    public static int precedence(char c){
        if(c == '+' || c == '-')
            return 1;
        else
            return 2;

    }

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        PrintStream output = new PrintStream(System.out);
        // write your solution here
        Stack<Character> st = new Stack<>();
        Queue<String> qu = new LinkedList<>();
        Stack<Integer> stint = new Stack<>();

        boolean flag = false;

//        System.out.println(s);
        while (input.hasNext())
        {

//            if(input.hasNext() == false)
//                break;
            String s = new String(input.next());
            if(s.equals("xc"))
                break;
//            System.out.printf("asd: %s\n",s);
            flag = false;
            char c = s.charAt(0);
//            System.out.printf("$%d: %c$\n",i,c);



            if(c >= '0' && c <= '9') {
//                System.out.printf("%s ", s);
                qu.add(s);
                flag = true;
            }


            else
            {
                if(c == '(')
                    st.push('(');
                else if(c == ')')
                {
                    while(st.size()> 0)
                    {

                        char tmp = st.pop();
                        if(tmp == '(')
                            break;

//                        System.out.printf("%c ", tmp);
                        qu.add(String.valueOf(tmp));
                    }
                }
                else
                {

                    if(st.size() == 0 || st.peek() == '(' ) {
                        st.push(c);
                        continue;
                    }
                    else{
                        if(precedence(st.peek()) < precedence(c))
                        {
                            st.push(c);
                        }
                        else
                        {


                            while(st.size()> 0)
                            {
                                char tmp = st.pop();
                                if(precedence(tmp) < precedence(c))
                                {
                                    st.push(tmp);
                                    break;
                                }
                                if(tmp == '(') {
//                                    st.push(tmp);
                                    continue;
                                }
//                                System.out.printf("%c ", tmp);
                                qu.add(String.valueOf(tmp));

                            }
                            st.push(c);

                        }
                    }
                }
            }

        }
        while(st.size()> 1)
        {
            char tmp = st.pop();
            if(tmp == '(' || tmp == ' ')
                continue;
//            System.out.printf("%c",tmp);
            qu.add(String.valueOf(tmp));
        }
        if(st.size()!= 0 && st.peek() != '(') {
//            if(!flag)
//                System.out.print(' ');
            char tmp = st.pop();
//            System.out.print(tmp);
            qu.add(String.valueOf(tmp));
//            System.out.print(' ');
        }
//        System.out.println("\nEvaluation:");
//        System.out.println(qu.size());
        int size = qu.size();
        for(int i = 0; i < size; i++)
        {

            String tmp = qu.element();
            qu.remove();
//            System.out.print(tmp);
            if(tmp.charAt(0) >= '0' && tmp.charAt(0) <= '9')
            {
                stint.push(Integer.parseInt(tmp));
            }
            else
            {
                if(tmp.equals("-"))
                {
                    stint.push(-1*(stint.pop()-stint.pop()));
                }
                else if(tmp.equals("+"))
                {
                    stint.push(stint.pop()+stint.pop());
                }
                else if(tmp.equals("*"))
                {
                    stint.push(stint.pop()*stint.pop());
                }
                else if(tmp.equals("/")) {
                    int tmp1 = stint.pop();
                    int tmp2 = stint.pop();
                    stint.push(tmp2/tmp1);
                }

            }
        }
//        System.out.println("");
        System.out.println(stint.pop());


        input.close();
        output.close();
    }
}
