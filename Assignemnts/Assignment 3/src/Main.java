import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Data Structure & Algorithms Course (DSA)
 * Innopolis University, Sprinng 2019
 * Assignment 3
 * Submission Number: 52924609
 * Submission Link: https://codeforces.com/group/lk8Ud0ZeBu/contest/242929/submission/52924609
 * @author Hany Hamed
 */

/**
 * References:
 * https://www.wikiwand.com/en/Mergeable_heap
 * https://brilliant.org/wiki/binomial-heap/
 * https://www.cs.usfca.edu/~galles/visualization/BinomialQueue.html
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
        private ArrayList<Node<K,V>> list = new ArrayList<>();      //Stack to store the roots of the binomial trees

        //getter for the Stack with private access
        private ArrayList<Node<K, V>> getList() {
            return list;
        }

        /**
         * This method is to insert an element in the binomial heap with private access
         * another public method will use it as a wrapper to use it publicly insertion with Key and Value parameters
         * @param no  Node
         * $Complexity proof$ This if will be T(n) = O(lgn) (The description for each part is down with the comments)
         *   T(n) = O(1) || O(lgn) || O(1)  -> T(n) = O(lgn) according if we insert trees with 1-order or d-order
         *   as O(lgn) = merge_Tree
         */
        private void insert(Node<K,V> no){

            //Check the size of the Stack if it is zero, push the first the element and first tree in the heap
            if(list.size() == 0){
                list.add(0,no);
                return;
            }
            //if the first one is smaller than the inserted, insert the new one in front and merge the heap, which will organize it
            if(list.get(0).getChildren().size() <= no.getChildren().size()) {
                list.add(0,no);
                merge_tree();
            }
            //if anything else which means that it is  in the correct order, just insert without merge
            else
                list.add(0,no);


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
         * @return Pair which will wrap the index and the Node (binomaialtree's root)
         * T(n) = O(lgn) -> just traversing on the heads of the tree
         * as the number of heads is always = lgn
         */
        private Pair<Integer,Node<K,V>> _max(){
            int heap_size = list.size();
            if(heap_size == 0){
                throw new NoSuchElementException();    //Raise Error that there is no element in the heap, it is empty
            }
            Node<K,V> mx = list.get(0);                     //see the first root tree in the heap
            int mxind = 0;
            //Traverse the heap
            for(int i =0; i < heap_size; i++){
                //get the tree's root from the heap
                Node<K,V> tmp_node = list.get(i);
                //If we found greater element, get it
                if(mx.getKey().compareTo(tmp_node.getKey()) < 0){
                    mx = tmp_node;
                    mxind = i;
                }
            }
            return new Pair<>(mxind,mx);  // return the maximum
        }


        /**
         * Just a public wrapper method for the private method of max with the required interface
         * @return Pair
         */
        @Override
        public Pair<K,V> max(){
            Node<K,V> mx = _max().getValue();
            return new Pair<>(mx.getKey(),mx.getValue());
        }

        /**
         * This method to remove the maximum element from the heap
         * $Complexity proof$ This if will be T(n) = O(lgn [get_max]+ lgn [traverse] + lgn[Extract_children and Insert])
         * T(n) = O(lgn)
         */

        @Override
        public void removeMax(){
            int heap_size = list.size();        //get the size of the heap
            if(heap_size == 0){
                throw new NoSuchElementException();    //Raise Error that there is no element in the heap, it is empty
            }

            Pair<Integer,Node<K,V>> mx = _max();  //get the maximum element in the heap which will be one of the roots of the trees
            //Traverse the heap
            int mxind = mx.getKey();
            Node<K,V> need = list.get(mxind);       //get the maximum root
            list.remove(mxind);                     //remove it
            for(int i =0; i < need.getChildren().size(); i++) {
                insert(need.getChildren().get(i));  //extract its children and insert them in the heap
            }
        }
        /**
         * This method is to merge the trees of the binomial heap, and place them in its correct position
         * $Complexity proof$ This if will be T(n) = O(lgn [Traversing] + lgn [Restoring the original state])
         * as pushing in the stack and reorder is constant as they are just stacks
         */
        private void merge_tree(){
            //Traverse the heap, if there are two equal trees , merge them
            for(int i = 0; i < list.size()-1; i++){
                //if the list is only one, we have succssfully merged everything
                if(list.size() == 1)
                    break;
                Node<K,V> n = list.get(i);          //get the ith root
                Node<K,V> tmp_node = list.get(i+1); //get the i+1th root

                int n_list_size = n.getChildren().size();       //get the degree of the first node
                int tmp_node_list_size = tmp_node.getChildren().size();     //get the degree of the second node

                //if the degree of the first root is less than, just put them back in the correct order (Swap the order)
                if(tmp_node_list_size < n_list_size){
                    list.remove(i);
                    list.add(i,tmp_node);
                    list.remove(i+1);
                    list.add(i+1,n);
                }
                //If they are equal, merge them
                if(tmp_node_list_size == n_list_size){
                    //If the second is smaller, put the first one is first, then put the second as child for the first
                    if(tmp_node.getKey().compareTo(n.getKey()) < 0){
                        (list.get(i)).getChildren().add(tmp_node);  //add the smallest under the bigger
                        list.remove(i+1);                     //Remove the smallest
                        i--;        //decrement the index counter in order to merge again the last result
                    }
                    //If the first is smaller or equal, put the second one is first, then put the first as child for the second
                    else{
                        (list.get(i+1)).getChildren().add(n);   //add the smallest under the bigger
                        list.remove(i);                         //Remove the smallest
                        i--;     //decrement the index counter in order to merge again the last result
                    }
                }

            }

        }
        /**
         * This method is to merge the two heaps
         * extract the trees from the second heap then, insert them and merge
         * T(n) -> O(lgn1 * lgn2)
         * It can be done in lgn using two pointers approach and iterate on the two heaps and choose the smallest in the size first and put it
         */
        @Override
        public void merge(MergeableHeap h){
            //Traverse on the second heap
            int heap_size = ((BinomialHeap)h).getList().size();

            for(int i = 0; i < heap_size; i++){
                //extract all the trees, then, insert them which will make them in the correct order
                Node<K,V> tmp_tree = (Node<K, V>) ((BinomialHeap)h).getList().get(i);
                insert(tmp_tree);
            }

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
            System.out.println("-------------------------");
            System.out.printf("SIZE: %d \n",list.size());

            for(int i = 0; i < list.size(); i++){
                Node<K,V> n = list.get(i);
                System.out.printf(" ->%d. #%d(",i+1,n.getChildren().size());
                print_tree(n);
                System.out.print(")");
            }

            System.out.println("\n-------------------------");
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
//
        Scanner input = new Scanner(System.in); //Scanner for the input
        SimpleDateFormat date_format = new SimpleDateFormat("dd.MM.yy");    //Set the format of the time as it is in the assignment descripition

        BinomialHeap<Integer,Task> todo_list = new BinomialHeap();                      //Binomial heap act as priority queue in order to have all the tasks in the right order to print them in the end
        //Date as key, the pair to wrap the priorituy queue for each date and the number of the tasks in the date
        //act like every day will have its own todolist
        Map<Date, Pair<BinomialHeap<Integer,Task>,Integer>> sub_todo_list = new TreeMap<>();    //Tree map which will be always sorted and hasing according to the date, and in each date there will be binomial heap for its priority

        int n = input.nextInt();        //Get the number of tasks
        int current_tasks = 0;          //counter to store the number of the tasks
        //Get the tasks
        for(int i = 0; i < n; i++){
            String time = input.next(); //Get the first part

            //If the first character is not a number then it is deletion order
            if(Character.isDigit(time.charAt(0)) == false) {
                String delete_time = input.next();          //get the time of delete
                int tmp = sub_todo_list.get(date_format.parse(delete_time)).getValue(); //get the number of tasks in the date
                //If there at least one task in the at date, delete the most important
                if(tmp > 0) {
                    sub_todo_list.get(date_format.parse(delete_time)).getKey().removeMax();
                    sub_todo_list.get(date_format.parse(delete_time)).setValue(tmp-1);
                    current_tasks--;        //decrement the counter of the tasks that in the todolist
                }
                continue;           //Skip the rest
            }

            String description = input.next(); //Get the description of the task
            int priority_value = input.nextInt();   //Get the pritioty level of the task
            BinomialHeap<Integer,Task> tmp_bh = new BinomialHeap<>();       //to store the binomialheap of the date
            int tmp = 0;    //to store the counter of the tasks in the date
            //if the day had already tasks
            if(sub_todo_list.containsKey(date_format.parse(time))) {
                tmp = sub_todo_list.get(date_format.parse(time)).getValue();        //get the number of the tasks
                tmp_bh = sub_todo_list.get(date_format.parse(time)).getKey();       //get the binomial heap
                sub_todo_list.get(date_format.parse(time)).setValue(tmp+1);         //increment the number of the tasks in the date
                tmp_bh.insert(priority_value, new Task(priority_value,description,date_format.parse(time)));    //Insert te task in the binomial heap
                sub_todo_list.get(date_format.parse(time)).setKey(tmp_bh);  //Set the new updated todolist for the day
            }
            //if the date is new, no tasks in the day, insert new binomial heap and make the number of the tasks is equal to one
            else {
                tmp_bh.insert(priority_value, new Task(priority_value, description, date_format.parse(time)));  //insert the task
                sub_todo_list.put(date_format.parse(time), new Pair<>(tmp_bh, 1));  //make the number of the tasks equal to one
            }
            current_tasks++;    //increment the counter
        }

        //Print the tasks for each day, iterate on the sorted hashmap -> Treemap
        for(Map.Entry<Date,Pair<BinomialHeap<Integer,Task>,Integer>> entry : sub_todo_list.entrySet()) {
            String key = date_format.format(entry.getKey());
            System.out.printf("TODOList %s\n",key);
            //iterate over the binomial tree for each date
            for(int i = 0; i < entry.getValue().getValue(); i++){
                Pair<Integer,Task> tmp = entry.getValue().getKey().max();   //get thte most important task
                System.out.printf("\t%s\n", tmp.getValue().getDescription());
                //insert it in the general todo_list
                todo_list.insert(tmp.getKey(),tmp.getValue());
                entry.getValue().getKey().removeMax();  //remove it from the date's binomial heap

            }

        }
        //Print the whole todolist, and print the elements according to its impotantance
        System.out.println("TODOList");
        for(int i = 0; i < current_tasks; i++){
            Node<Integer,Task> tmp_n = new Node<>(todo_list.max().getKey(),todo_list.max().getValue());
            System.out.printf("\t%s\n", tmp_n.getValue().getDescription());
            todo_list.removeMax();
        }


        //Test Cases
//        BinomialHeap<Integer,Character> bh = new BinomialHeap<>();
//        bh.insert(1,'a');
////        System.out.println("Final -----");
//        bh.print_heap();
//        bh.insert(-1,'b');
//////        System.out.println("Final -----");
//        bh.print_heap();
//        bh.insert(3,'c');
//////        System.out.println("Final -----");
//        bh.print_heap();
//        bh.insert(0,'d');
//////        System.out.println("Final -----");
//        bh.print_heap();
//        bh.insert(10,'d');
//////        System.out.println("Final -----");
//        bh.print_heap();
//        bh.insert(0,'d');
//////        System.out.println("Final -----");
//        bh.print_heap();
//        bh.insert(3,'d');
//////        System.out.println("Final -----");
//        bh.print_heap();
//////        SIZE: 3
//////        ->1. #0(<3,d>/) ->2. #1(<10,d><0,d>/) ->3. #2(<3,c><0,d>/<1,a><-1,b>/)
////
////
//        BinomialHeap<Integer,Character> bh2 = new BinomialHeap<>();
//        bh2.insert(5,'a');
//        System.out.println("Final -----");
//        bh2.print_heap();
//        bh2.insert(-4,'b');
//        System.out.println("Final -----");
//        bh2.print_heap();
//        bh2.insert(9,'c');
//        System.out.println("Final -----");
//        bh2.print_heap();
//        bh2.insert(7,'d');
//        System.out.println("Final -----");
//        bh2.print_heap();
//        bh2.insert(3,'c');
//        bh2.print_heap();
//
////        SIZE: 2
////        ->1. #0(<3,c>/) ->2. #2(<9,c><7,d>/<5,a><-4,b>/)
////
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
