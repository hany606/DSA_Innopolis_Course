import java.io.PrintStream;
import java.util.Scanner;
import java.io.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        Scanner input = new Scanner(new FileInputStream("input.txt"));
        PrintStream output = new PrintStream(new FileOutputStream("output.txt"));
        // write your solution here
        //Task1: print the divisors of a number
        int val = input.nextInt();
        for(int i = 1; i <= val; i++){
            if(val%i == 0) {
                output.print(i);
                output.print(' ');
            }
        }
        output.print("\n");
        //Task2: add 2 numbers
        output.println(input.nextInt()+input.nextInt());

        //Task3: add 3 numbers
        long s = input.nextLong()+input.nextLong();
        if(input.hasNextLong() == false)
            output.print("NOT ENOUGH NUMBERS");
        else {
            s += input.nextLong();
            output.print(s);
        }
        output.print("\n");
        input.close();
        output.close();
    }
}

