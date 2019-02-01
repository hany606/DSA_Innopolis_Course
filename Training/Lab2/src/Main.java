import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;



public class Main {
    static void swap(int[] l, int i, int x)
    {
        int tmp = l[i];
        l[i] = l[x];
        l[x] = tmp;
    }
    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        Scanner input = new Scanner(new FileInputStream("input.txt"));
        PrintStream output = new PrintStream(new FileOutputStream("output.txt"));
        int size = input.nextInt();
        int[] list = new int[size];

        for(int i = 0; i < size; i++)
        {
            list[i] = input.nextInt();
        }


        for(int i = 0; i < size; i++)
        {
            int index = i;
            for(int x = i; x < size; x++)
            {
                //get the smallest element than it
                if(list[x] < list[index]) {
                    index = x;
                }
            }
            swap(list,index,i);
        }
        for(int i = 0 ; i< size; i++)
        {
            output.print(list[i]);
            output.print(' ');
        }

    }

}
