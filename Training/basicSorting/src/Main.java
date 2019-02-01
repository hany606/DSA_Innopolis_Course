public class Main {

    static void print(int[] list, int n){
        for(int i = 0 ; i < n; i++){
            System.out.println(list[i]);
        }
        System.out.println("-------------------");
    }


    static void testManipulator(int[] list, int n){
        for(int i = 0 ; i < n; i++){
            list[i] += 2;
        }
    }

    static void swap(int[] list, int i, int j)
    {
        int ind = list[i];
        list[i] = list[j];
        list[j] = ind;
    }

    static void shift(int[] list, int s, int e)
    {
        for(int i = e; i > s; i--)
        {
            list[i] = list[i-1];
        }
    }


    //It is based on swapping the elements if they were wrongly positioned
    static void bubleSort(int[] list, int n){
        for(int i = 0; i < n; i++)
        {
            for(int j = i+1; j < n; j++)
            {
                if(list[i] > list[j])
                    swap(list,i,j);
            }
        }
    }


    //It based on pick and put and on the belief that any sequence behind it is already sorted
    static void insertionSort(int[] list, int n){
        for(int i = 1; i < n; i++)
        {
            int j;

            //more optimized way
            //In the same time it is shifting and compare
            int k = list[i];
            for(j = i-1; j >= 0 && list[j] > k; j--)
                list[j+1] = list[j];
            list[j+1] = k;


//            for( j = i-1; j >= 0; j--) {
//
//                if (list[i] > list[j]) {
//
//                    j = -2;
//                    break;
//                }
//            }
//
//            if(j != -2)
//            {
//                //save the value
//                int k = list[i];
//                //shift
//                shift(list,j+1,i);
//                //insert it in j
//                list[j+1] = k;
//            }

        }
    }

    static void selectionSort(int[] list, int n)
    {
        for(int i = 0; i < n; i++)
        {
            int index = i;
            for(int x = i; x < n; x++)
            {
                //get the smallest element than it
                if(list[x] < list[index]) {
                    index = x;
                }
            }
            swap(list,index,i);
        }

    }




    public static void main(String[] args) {
	// write your code here
        int[] originalList = {6,5,4,3,2,1};
        int[] list = {0,0,0,0,0,0};
        int size = 6;

        for(int i = 0; i < size; i++){
            list[i] = originalList[i];
        }
        print(originalList,size);

        bubleSort(list,size);
        print(list,size);

        //Resetting the list to the original format
        for(int i = 0; i < size; i++){
            list[i] = originalList[i];
        }
        print(list,size);
        insertionSort(list,size);
        print(list,size);

        //Resetting the list to the original format
        for(int i = 0; i < size; i++){
            list[i] = originalList[i];
        }

        print(list,size);
        selectionSort(list,size);
        print(list,size);


    }
}
