import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Data Structure & Algorithms Course (DSA)
 * Innopolis University, Sprinng 2019
 * Assignment 3
 * @author Hany Hamed
 */

/**
 * References:
 * https://www.wikiwand.com/en/Mergeable_heap
 * https://brilliant.org/wiki/binomial-heap/
 * https://www.cs.usfca.edu/~galles/visualization/BinomialQueue.html
 * https://www.tutorialspoint.com/java/java_arraylist_class.htm
 *
 */

public class Main {

    /**
     * This is just a class to provide wrapping to objects in a Pair format
     * @param <K> This is the generic type for the key
     * @param <V> This is the genric type for the value
     */
    public static class Pair<K,V>{
        private K key;      //The key variable
        private V value;    //The value variable

        //Constructor to initialize the object
        public Pair(K k, V v){
            this.key = k;
            this.value = v;
        }

        //Setters and getters
        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    /**
     * This is a class that describe and implement the node in the binomial heap
     * @param <K> This is the generic type for the key
     * @param <V> This is the generic type for the value
     */
    public static class Node<K extends Comparable<K>, V>{
        private K key;
        private V value;
        private ArrayList<Node<K,V>> children = new ArrayList<>();      //Array to store the children of the node

        public Node(K k, V v) {
            this.key = k;
            this.value = v;
        }

        public K getKey(){
            return key;
        }
        public V getValue(){
            return value;
        }

        //Getter for the children's Array
        public ArrayList<Node<K, V>> getChildren() {
            return children;
        }

        public void setChildren(ArrayList<Node<K, V>> children) {
            this.children = children;
        }

        public void setKey(K k){
            this.key = k;
        }
        public void setValue(V v){
            this.value = v;
        }


    }
    /**
     * This is just the interface for Mergable Heap
     * @param <K> This is the generic type of the keys of the nodes of the Heap
     * @param <V> This is the generic type of the values of the node of the Heap
     *
     */
    public interface MergeableHeap<K extends Comparable<K>,V>{
        /**
         * This method is made to insert a new node in the Mergable heap
         */
        void insert(K k, V v);
        /**
         * This method is made to get the maximuum element in the Mergable heap
         * @return pair of key and value
         */
        Pair<K,V> max();
        /**
         * This method is made to remove the max element in the Mergable heap
         */
        void removeMax();

        /**
         * This method is made to merge two Mergable heaps together
         * @param h is the second Mergable heap to be merged with
         */
        void merge(MergeableHeap h); //It will be mistake

    }

    /**
     * This is a class that implement the interface of the Mergable heap to the binomial heap
     * @param <K> This is the generic type for the key
     * @param <V> This is the generic type for the value
     */

    public static class BinomialHeap<K extends Comparable<K>, V> implements MergeableHeap<K,V>{
        private Stack<Node<K,V>> list = new Stack<>();      //Stack to store the roots of the binomial trees

        //getter for the Stack with private access
        private Stack<Node<K, V>> getList() {
            return list;
        }

        /**
         * This method is to insert an element in the binomial heap with private access
         * another public method will use it as a wrapper to use it publicly insertion with Key and Value parameters
         * @param no  Node
         * lgn*lgn = (lgn)^2 ??!!!!
         * traverse*merge
         * O((lgn)^2)
         */
        private void insert(Node<K,V> no){
            boolean inserted = true;    //Flag to check if the node is inserted or not

            //Check the size of the Stack if it is zero, push the first the element and first tree in the heap
            if(list.size() == 0){
                list.push(no);
                return;
            }

            Node<K,V> tmp_node = list.pop();    //If there at least one element, pop it
            // the size of the inserted tree is equal to the exist tree, merge them
            if(tmp_node.getChildren().size() == no.getChildren().size()){
                list.push(tmp_node);    //push the original
                list.push(no);          //push the new inserted one
                this.merge_tree();      //merge the trees
            }
            //if the size of the inserted tree is greater, go down and inserted in its right position
            else if(tmp_node.getChildren().size() < no.getChildren().size()){
                list.push(tmp_node);            //push the original one

                int heap_size = list.size();    //get the size of the stack
                Stack<Node<K,V>> tmp_stack = new Stack<>(); //create a tmp stack in order to traverse the heap, and store the poped element, then push them back again
                //Traverse on the trees in the heap
                for(int i = 0; i < heap_size; i++){

                    //If it is the end of the heap, break
                    if(list.size() == 0)
                        break;
                    Node<K,V> tmp_node2 = list.pop();   //if not the end, continue traversing

                    int n_list_size = no.getChildren().size();  //get the degree of the inserted tree
                    int tmp_node_list_size = tmp_node2.getChildren().size();    //get the degree of the current traversing tree
                    //if the inserted tree is greater than the current, continue traversing
                    if(tmp_node_list_size < n_list_size){
                        tmp_stack.push(tmp_node2);  //Push the current node in the stack to return it back later in order to save the state of the heap in the end after traversing
                        inserted = false;   //set the flag to false
                    }
                    //if the inserted tree is less than the current, then it is in its correct position, put it in its place
                    else if(tmp_node_list_size > n_list_size){
                        list.push(tmp_node2);   //push the greatest first
                        list.push(no);
                        inserted = true;
                        break;
                    }
                    //if the inserted tree is equal to the current, then it is in its correct position, put it in its place, then merge it
                    else if(tmp_node_list_size == n_list_size){
                        list.push(tmp_node2);
                        list.push(no);
                        inserted = true;
                        merge_tree();
                    }

                    //Here optimize with merge tree, remove it and put its code
                }
                //if not inserted yet, thats mean that the inserted tree is the greater than any tree in the heap, push it in the end
                if(!inserted)
                    list.push(no);

                //Push in the list again in the correct order, return the list in its original order after traversing
                int tmp_queue_size = tmp_stack.size();
                for(int i = 0; i < tmp_queue_size; i++){
                    list.push(tmp_stack.pop()); //pop from the stakc, then pop it in the list
                }
            }
            //if the size of the inserted tree is less, just push the current then push the inserted tree
            else{
                list.push(tmp_node);
                list.push(no);
            }

        }
        /**
         * Just a public wrapper method for the private method of insert with the required interface
         * @param k key
         * @param v value
         */
        @Override
        public void insert(K k, V v){
            insert(new Node<>(k,v));
        }

        /**
         * This method is a private method in order to get the maximum element in the heap in a Node object
         * another public method will use it as a wrapper to use it publicly to get the max but in Pair object
         * It is mainly traverse the roots of the trees in the binomial heap and get the smallest
         * @return Node
         * lgn -> just traversing on the heads of the tree
         */
        private Node<K,V> _max(){
            int heap_size = list.size();
            if(heap_size == 0){
                throw new NoSuchElementException();    //Raise Error that there is no element in the heap, it is empty
            }
            Node<K,V> mx = list.peek();                     //see the first root tree in the heap
            Stack<Node<K,V>> tmp_stack = new Stack<>();     //Create tmp stack to traverse the heap
            //Traverse the heap
            for(int i =0; i < heap_size; i++){
                //Pop from the original list, pop it in the tmp stack
                Node<K,V> tmp_node = list.pop();
                tmp_stack.push(tmp_node);
                //If we found greater element, get it
                if(mx.getKey().compareTo(tmp_node.getKey()) < 0){
                    mx = tmp_node;
                }
            }
            //Push again in the original stack the elements that we have pop form it
            int tmp_stack_size = tmp_stack.size();
            for(int i = 0; i < tmp_stack_size; i++){
                list.push(tmp_stack.pop());
            }

            return mx;  // return the maximum
        }

        /**
         * Just a public wrapper method for the private method of max with the required interface
         * @return Pair
         */
        @Override
        public Pair<K,V> max(){
            Node<K,V> mx = _max();
            return new Pair<>(mx.getKey(),mx.getValue());
        }

        /**
         * This method to remove the maximum element from the heap
         * lgn(get max) + lgn(traverse) + lgn*(lgn)^2 (extract the tree* insert) + lgn*(lgn)^2 (restore the original*insert)
         * O((lgn)^3) ??!!!!
         */
        @Override
        public void removeMax(){

            int heap_size = list.size();        //get the size of the heap
            if(heap_size == 0){
                throw new NoSuchElementException();    //Raise Error that there is no element in the heap, it is empty
            }
            Stack<Node<K,V>> tmp_stack = new Stack<>(); //crete a tmp stack in order to traverse the heap
            Node<K,V> mx = _max();  //get the maximum element in the heap which will be one of the roots of the trees
            //Traverse the heap
            for(int i =0; i < heap_size; i++){
                Node<K,V> tmp_node = list.pop();    //pop the element
                //Check if the tree's root is the maximum or not, if it is, delete it, insert the children, merge the heaps
                if(mx.getKey().compareTo(tmp_node.getKey()) == 0){
                    int children_size = mx.getChildren().size();
                    //extract all the trees, then inserted,then merge them inside the insertion
                    for(int x = 0; x < children_size; x++){
                        Node<K,V> tmp_tree = mx.getChildren().get(x);
                        insert(tmp_tree);
                    }
                    break;
                }
                tmp_stack.push(tmp_node);   //push it in the tmp list in order to restore it back, if it wasn't the maximum
            }

            //Restore the original state of the heap without the maximum element in its correct position,
            int tmp_stack_size = tmp_stack.size();
            for(int i = 0; i < tmp_stack_size; i++){
                insert(tmp_stack.pop());    //using insertion instead of push in order to merge too
            }
        }
        /**
         * This method is to merge the trees of the binomial heap, and place them in its correct position
         * ??? remove that its is ordering correct no need
         */
        private void merge_tree(){
            //Traverse the heap, if there are two equal trees , merge them
            int heap_size = this.list.size();
            Stack<Node<K,V>> tmp_queue = new Stack<>();     //create tmp stack for the traversing
            for(int i = 0; i < heap_size; i++){
                if(this.list.size() == 1)
                    break;
                Node<K,V> n = list.pop();
                Node<K,V> tmp_node = list.pop();

                int n_list_size = n.getChildren().size();       //get the degree of the first node
                int tmp_node_list_size = tmp_node.getChildren().size();     //get the degree of the second node

                //Just correct the order of the tree, if they are not equal
                if(tmp_node_list_size < n_list_size){
                    list.push(n);
                    list.push(tmp_node);
                    break;
                }
                else if(tmp_node_list_size > n_list_size){

                    list.push(tmp_node);
                    tmp_queue.push(n);
                }
                //If they are equal, merge them
                else{
                    //If the second is smaller, put the first one is first, then put the second as child for the first
                    if(tmp_node.getKey().compareTo(n.getKey()) < 0){
                        ArrayList<Node<K,V>> tmp_list = n.getChildren();
                        tmp_list.add(tmp_node);
                        list.push(n);
                    }
                    //If the first is smaller or equal, put the second one is first, then put the first as child for the second
                    else{
                        ArrayList<Node<K,V>> tmp_list = tmp_node.getChildren();
                        tmp_list.add(n);
                        list.push(tmp_node);
                    }

                }
            }
            //restore the original state of the heap
            int tmp_queue_size = tmp_queue.size();
            for(int i = 0; i < tmp_queue_size; i++){
                list.push(tmp_queue.pop());
            }

        }
        /**
         * This method is to merge the two heaps
         * extract the trees from the second heap then, insert them and merge
         * lgn*insert
         */
        @Override
        public void merge(MergeableHeap h){
            //Traverse on the second heap heap
            int heap_size = ((BinomialHeap)h).getList().size();
            for(int i = 0; i < heap_size; i++){
                //extract all the trees, then, merge
                Node<K,V> tmp_tree = (Node<K, V>) ((BinomialHeap)h).getList().pop();
                insert(tmp_tree);
            }
            merge_tree();   // merge the tree in the heap
        }

        /**
         * This method is to print the tree.
         * Traverse the tree's root nodes
         */
        private void print_tree(Node<K,V> n){
            System.out.print("<");
            System.out.print(n.getKey());
            System.out.print(",");
            System.out.print(n.getValue());
            System.out.print(">");
            //IF it is a leaf, base case
            if(n.getChildren().size() == 0) {
                System.out.print("/");
                return;
            }
            for(int i = 0; i < n.getChildren().size(); i++){
                print_tree(n.getChildren().get(i));     //recursive call to print the children
            }
        }

        /**
         * This method is to print the heap
         * traverse the heap, and get the root nodes
         */
        public void print_heap(){
            Stack<Node<K,V>> tmp_list = new Stack<>();
            int heap_size = this.getList().size();
            System.out.println("-------------------------");
            System.out.printf("SIZE: %d \n",heap_size);

            for(int i = 0; i < heap_size; i++){
                Node<K,V> n = this.list.pop();
                tmp_list.push(n);
                System.out.printf(" ->%d. #%d(",i+1,n.getChildren().size());
                print_tree(n);
                System.out.print(")");
            }

            System.out.println("\n-------------------------");
            //restore the original state of the heap
            int tmp_size = tmp_list.size();
            for(int i = 0; i < tmp_size; i++){
                this.list.push(tmp_list.pop());
            }
        }
    }

    /**
     * This is a class to describe and implement the Task in the todolist
     */
    public static class Task{
        private int priority_value;     //store the priority value
        private String description;     //store the description of the task
        private Date time;              //store the time of the task


        //Constructor to intialize while creating the objec
        public Task(int priority_value, String description, Date time){
            this.priority_value = priority_value;
            this.description = description;
            this.time = time;
        }


        //Setters and getters
        public int getPriority_value() {
            return priority_value;
        }

        public String getDescription() {
            return description;
        }

        public Date getTime() {
            return time;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setPriority_value(int priority_value) {
            this.priority_value = priority_value;
        }

        public void setTime(Date time) {
            this.time = time;
        }
    }

    public static void main(String[] args) throws ParseException {

        Scanner input = new Scanner(System.in); //Scanner for the input
        SimpleDateFormat date_format = new SimpleDateFormat("dd.MM.yy");    //Set the format of the time as it is in the assignment descripition

        BinomialHeap<Integer,Task> todo_list = new BinomialHeap();  //Create Binomial heap for the todolist in order to get always the most important task, and the pritioty level will be the key
        ArrayList<Date> time_list = new ArrayList<>();              //To store the different times in the list
//        BinomialHeap<Date, Integer> time_list_bh = new BinomialHeap<>();

        int n = input.nextInt();        //Get the number of tasks
        int diff_time = 0;
        int current_tasks = 0;          //counter to store the number of the tasks
        //Get the tasks
        for(int i = 0; i < n; i++){
            String time = input.next(); //Get the first part
            System.out.println(time);
            //If the first character is not a number then it is deletion order
            if(Character.isDigit(time.charAt(0)) == false) {
                String delete_time = input.next();          //get the time of delete
                System.out.println(delete_time);
                Stack<Task> tmp_stack = new Stack<>();      //Create stack to traverse the heap in order to get the most important task in the todolist with this specific date
                //Traverse the heap
                for (int x = 0; x < current_tasks; x++) {
                    Node<Integer, Task> tmp_n = new Node<>(todo_list.max().getKey(),todo_list.max().getValue());
                    todo_list.removeMax();
                    //If you found your element, break without pushing in ht tmp stack
                    if (tmp_n.getValue().getTime().equals(date_format.parse(delete_time))) {
                        break;
                    }
                    tmp_stack.push(tmp_n.getValue());
                }
                //Restore the state of the heap
                int stack_size = tmp_stack.size();
                for(int x = 0 ; x < stack_size; x++){
                    Task tmp_task = tmp_stack.pop();
                    todo_list.insert(tmp_task.getPriority_value(),tmp_task);
                }
                current_tasks--;    //decrement the counter of the tasks
                continue;           //Skip the rest
            }
            String description = input.next(); //Get the description of the task
            int priority_value = input.nextInt();   //Get the pritioty level of the task
            todo_list.insert(priority_value, new Task(priority_value,description,date_format.parse(time))); //insert ot in the heap
            current_tasks++;    //increment the counter

            //Check if the date is already exist or not, if it exist don't store it , if not store it
            boolean exist = false;
            for(int x = 0; x < time_list.size(); x++){
                if(time.equals(date_format.format(time_list.get(x)))){
                    exist = true;
                    break;
                }
            }
            if(!exist){
                diff_time++;    //increment the counter
                time_list.add(date_format.parse(time));
//                time_list_bh.insert(date_format.parse(time),i);
            }
        }

        Collections.sort(time_list);    //Sort the list

//        System.out.println(diff_time);
//        for(int i = 0; i < diff_time; i++){
//            Date tmp = (Date) time_list_bh.max().getKey();
//            time_list_bh.removeMax();
//            time_list.add(tmp);
//        }

/*
        //Traverse on the time list to output the times in the correct order
        for(int i = 0; i < time_list.size(); i++){
            System.out.printf("TODOList %s\n",date_format.format(time_list.get(i)));
            //Traverse the heap and get the specific tasks for the specific date
            Stack<Task> tmp_stack = new Stack<>();
            for(int x = 0; x < current_tasks; x++){
                //Push and pop from the heap to the tmp stack
                Node<Integer,Task> tmp_n = new Node<>(todo_list.max().getKey(),todo_list.max().getValue());
                todo_list.removeMax();
                tmp_stack.push(tmp_n.getValue());

                //If they for the specific date, print it
                if(tmp_n.getValue().getTime().equals(time_list.get(i)))
                    System.out.printf("\t%s\n", tmp_n.getValue().getDescription());
            }


            //Restore the original state of the heap
            int stack_size = tmp_stack.size();
            for(int x = 0 ; x < stack_size; x++){
                //Pop then insert
                Task tmp_task = tmp_stack.pop();
                todo_list.insert(tmp_task.getPriority_value(),tmp_task);
            }
        }

        //Print the whole todolist
        System.out.println("TODOList");
        for(int i = 0; i < current_tasks; i++){
            Node<Integer,Task> tmp_n = new Node<>(todo_list.max().getKey(),todo_list.max().getValue());
            System.out.printf("\t%s\n", tmp_n.getValue().getDescription());
            todo_list.removeMax();
        }

*/




        //Test Cases
//        BinomialHeap<Integer,Character> bh = new BinomialHeap<>();
//        bh.insert(1,'a');
////        System.out.println("Final -----");
//        bh.print_heap();
//        bh.insert(-1,'b');
////        System.out.println("Final -----");
//        bh.print_heap();
//        bh.insert(3,'c');
////        System.out.println("Final -----");
//        bh.print_heap();
//        bh.insert(0,'d');
////        System.out.println("Final -----");
//        bh.print_heap();
//        bh.insert(10,'d');
////        System.out.println("Final -----");
//        bh.print_heap();
//        bh.insert(0,'d');
////        System.out.println("Final -----");
//        bh.print_heap();
//        bh.insert(3,'d');
////        System.out.println("Final -----");
//        bh.print_heap();
////        SIZE: 3
////        ->1. #0(<3,d>/) ->2. #1(<10,d><0,d>/) ->3. #2(<3,c><0,d>/<1,a><-1,b>/)
//
//
//        BinomialHeap<Integer,Character> bh2 = new BinomialHeap<>();
//        bh2.insert(5,'a');
////        System.out.println("Final -----");
//        bh2.print_heap();
//        bh2.insert(-4,'b');
////        System.out.println("Final -----");
//        bh2.print_heap();
//        bh2.insert(9,'c');
////        System.out.println("Final -----");
//        bh2.print_heap();
//        bh2.insert(7,'d');
////        System.out.println("Final -----");
//        bh2.print_heap();
//        bh2.insert(3,'c');
//        bh2.print_heap();
//
////        SIZE: 2
////        ->1. #0(<3,c>/) ->2. #2(<9,c><7,d>/<5,a><-4,b>/)
//
//        System.out.println("*****************");
//        bh.merge(bh2);
//        bh.print_heap();
//        System.out.println("*****************");
////        SIZE: 2
////         ->1. #2(<9,c><7,d>/<5,a><-4,b>/) ->2. #3(<10,d><0,d>/<3,d><3,c>/<3,c><0,d>/<1,a><-1,b>/)
//
//
//
//        bh.removeMax();
//        bh.print_heap();
//
//
////        SIZE: 3
////        ->1. #0(<0,d>/) ->2. #1(<3,d><3,c>/) ->3. #3(<9,c><7,d>/<5,a><-4,b>/<3,c><0,d>/<1,a><-1,b>/)
    }

}
