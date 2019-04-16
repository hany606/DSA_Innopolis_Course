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
    public static class Node<K extends Comparable<K>, V>{
        private K key;
        private V value;
        private ArrayList<Node<K,V>> children = new ArrayList<>();

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
         * This method is made to ...............
         * @return ............
         */
        void insert(K k, V v);

        Node max();

        void removeMax();

        void merge(MergeableHeap h); //It will be mistake

    }


    public static class BinomialHeap<K extends Comparable<K>, V> implements MergeableHeap<K,V>{
        private Stack<Node<K,V>> list = new Stack<>();

        private Stack<Node<K, V>> getList() {
            return list;
        }

        private void insert(Node<K,V> no){
            boolean inserted = true;

            if(list.size() == 0){
//                System.out.println("INSERT0");
                list.push(no);
                return;
            }
            Node<K,V> tmp_node = list.pop();
            if(tmp_node.getChildren().size() == no.getChildren().size()){
//                System.out.println("INSERT--Normal--Merge");
                list.push(tmp_node);
                list.push(no);
                this.merge_tree();
            }
            else if(tmp_node.getChildren().size() < no.getChildren().size()){
//                System.out.println("Insert--Traverse--Merge");
                list.push(tmp_node);
                int heap_size = list.size();
                Stack<Node<K,V>> tmp_queue = new Stack<>();
                for(int i = 0; i < heap_size; i++){
//                    print_heap();
                    if(this.list.size() == 0)
                        break;
                    Node<K,V> tmp_node2 = list.pop();
//                    System.out.println(tmp_node2.getKey());
//                    System.out.println(no.getKey());
                    int n_list_size = no.getChildren().size();
                    int tmp_node_list_size = tmp_node2.getChildren().size();
//                    System.out.printf("TMP_LIST_SIZE:%d\n",tmp_node_list_size);
                    if(tmp_node_list_size < n_list_size){
//                        System.out.println("Down");
                        tmp_queue.push(tmp_node2);
                        inserted = false;
//                        list.push(no);
//                        list.push(tmp_node2);
//                        break;
                    }
                    else if(tmp_node_list_size > n_list_size){
//                        System.out.println("Correct");
                        //move down
//                    tmp_queue.add(tmp_node);
                        list.push(tmp_node2);
                        list.push(no);
                        inserted = true;
                        break;
                    }
                    else if(tmp_node_list_size == n_list_size){
//                        System.out.println("Correct pos equal");
                        list.push(tmp_node2);
                        list.push(no);
                        inserted = true;
                        merge_tree();
                    }
                }

                if(!inserted)
                    list.push(no);
                int tmp_queue_size = tmp_queue.size();
                for(int i = 0; i < tmp_queue_size; i++){
                    list.push(tmp_queue.pop());
                }

//                System.out.println("inside insert");
//                print_heap();
                // ->1. #1(<10,d><0,d>/) ->2. #3(<9,c><7,d>/<5,a><-4,b>/<3,c><0,d>/<1,a><-1,b>/)
            }
            else{
//                System.out.println("INSERT--NORMAL");

                list.push(tmp_node);
                list.push(no);
            }
        }
        @Override
        public void insert(K k, V v){
            insert(new Node<>(k,v));
        }
        @Override
        public Node max(){
            int heap_size = list.size();
            if(heap_size == 0){
                //Raise error there is no max
//                return;
            }
            Node<K,V> mx = list.peek();
            Stack<Node<K,V>> tmp_stack = new Stack<>();
            for(int i =0; i < heap_size; i++){
                Node<K,V> tmp_node = list.pop();
                tmp_stack.push(tmp_node);
                if(mx.getKey().compareTo(tmp_node.getKey()) < 0){
                    mx = tmp_node;
                }
            }
            int tmp_stack_size = tmp_stack.size();
            for(int i = 0; i < tmp_stack_size; i++){
                list.push(tmp_stack.pop());
            }
            return mx;
        }
        @Override
        public void removeMax(){

            int heap_size = list.size();
            Stack<Node<K,V>> tmp_stack = new Stack<>();
            Node<K,V> mx = max();
            for(int i =0; i < heap_size; i++){
                Node<K,V> tmp_node = list.pop();
                if(mx.getKey().compareTo(tmp_node.getKey()) == 0){
                    int children_size = mx.getChildren().size();
//                    System.out.println("as;dmaspdomasd");
//                    System.out.println(children_size);
                    for(int x = 0; x < children_size; x++){
                        //extract all the trees, then, merge
//                        System.out.println(x);
                        Node<K,V> tmp_tree = mx.getChildren().get(x);
                        insert(tmp_tree);
//                        System.out.println("--==-=--=");
//                        print_heap();
//                        System.out.println("--==-=--=");
                    }
                    break;
                }
                tmp_stack.push(tmp_node);
            }

            int tmp_stack_size = tmp_stack.size();
            for(int i = 0; i < tmp_stack_size; i++){
                insert(tmp_stack.pop());
            }
//            System.out.println("^^^^^^^^^^^^^^^^^66");
//            print_heap();
//            merge_tree();
        }
        private void merge_tree(){       //Node<K,V>
            int heap_size = this.list.size();
            Stack<Node<K,V>> tmp_queue = new Stack<>();
            for(int i = 0; i < heap_size; i++){
//                print_heap();
                if(this.list.size() == 1)
                    break;
                Node<K,V> n = list.pop();
                Node<K,V> tmp_node = list.pop();
//                System.out.println(tmp_node.getKey());
//                System.out.println(n.getKey());
                int n_list_size = n.getChildren().size();
                int tmp_node_list_size = tmp_node.getChildren().size();
//                System.out.printf("TMP_LIST_SIZE:%d\n",tmp_node_list_size);
                if(tmp_node_list_size < n_list_size){
//                    System.out.println("asodapodm");
                    list.push(n);
                    list.push(tmp_node);
                    break;
                }
                else if(tmp_node_list_size > n_list_size){
                    //move down
//                    tmp_queue.add(tmp_node);
                    list.push(tmp_node);
                    tmp_queue.push(n);
                }
                else{
                    //real merge
//                    System.out.println("---Real Merge---");
                    if(tmp_node.getKey().compareTo(n.getKey()) < 0){
//                        System.out.println("TMP GREATER");
                        ArrayList<Node<K,V>> tmp_list = n.getChildren();
                        tmp_list.add(tmp_node);
                        list.push(n);
//                        tmp_node.setChildren(tmp_list); //cycle!!!!!!
                    }
                    else{   // if(tmp_node.getKey().compareTo(n.getKey()) > 0)  //n.key smaller
//                        System.out.println("OTHER");
                        ArrayList<Node<K,V>> tmp_list = tmp_node.getChildren();
                        tmp_list.add(n);
                        list.push(tmp_node);
//                        tmp_node.setChildren(tmp_list);
//                        System.out.println(tmp_list);
                    }

                }
//                list.push(tmp_node);
//                tmp_queue.add(tmp_node);
            }
            int tmp_queue_size = tmp_queue.size();
            for(int i = 0; i < tmp_queue_size; i++){
                list.push(tmp_queue.pop());
            }

        }
        @Override
        public void merge(MergeableHeap h){
            int heap_size = ((BinomialHeap)h).getList().size();
            for(int i = 0; i < heap_size; i++){
                //extract all the trees, then, merge
                Node<K,V> tmp_tree = (Node<K, V>) ((BinomialHeap)h).getList().pop();
                this.insert(tmp_tree);
            }
            merge_tree();
        }

        private void print_tree(Node<K,V> n){
            System.out.print("<");
            System.out.print(n.getKey());
            System.out.print(",");
            System.out.print(n.getValue());
            System.out.print(">");
            if(n.getChildren().size() == 0) {
                System.out.print("/");
                return;
            }
            for(int i = 0; i < n.getChildren().size(); i++){
                print_tree(n.getChildren().get(i));
            }
        }

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
//                for(int x = 0; x < n.getChildren().size(); x++){
//                    System.out.print("<");
//                    System.out.print(n.getChildren().get(x).getKey());
//                    System.out.print(",");
//                    System.out.print(n.getChildren().get(x).getValue());
//                    System.out.print(">");
//                }
                System.out.print(")");
            }
            System.out.println("\n-------------------------");
            int tmp_size = tmp_list.size();
            for(int i = 0; i < tmp_size; i++){
                this.list.push(tmp_list.pop());
            }
        }
    }

    public static class Task{
        private int priority_value;
        private String description;
        private Date time;

        public Task(int priority_value, String description, Date time){
            this.priority_value = priority_value;
            this.description = description;
            this.time = time;
        }

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
        // write your code here

        Scanner input = new Scanner(System.in);
        SimpleDateFormat date_format = new SimpleDateFormat("dd.MM.yyyy");
//        Date d = date_format.parse("13.11.1999");
//        System.out.println(date_format.format(d));
//
        BinomialHeap<Integer,Task> todo_list = new BinomialHeap();
        ArrayList<Date> time_list = new ArrayList<>();

        int n = input.nextInt();
        String time0 = "";
        int current_tasks = 0;
        int diff_time = 1;
        for(int i = 0; i < n; i++){
            String time = input.next();
            if(Character.isDigit(time.charAt(0)) == false) {
                String delete_time = input.next();
                Stack<Task> tmp_stack = new Stack<>();
                for (int x = 0; x < current_tasks; x++) {
                    Node<Integer, Task> tmp_n = todo_list.max();
                    todo_list.removeMax();
                    if (tmp_n.getValue().getTime().equals(date_format.parse(delete_time))) {
                        break;
                    }
                    tmp_stack.push(tmp_n.getValue());
                }
                int stack_size = tmp_stack.size();
                for(int x = 0 ; x < stack_size; x++){
                    Task tmp_task = tmp_stack.pop();
                    todo_list.insert(tmp_task.getPriority_value(),tmp_task);
                }
                current_tasks--;
                continue;
            }
            String description = input.next();
            int priority_value = input.nextInt();
            todo_list.insert(priority_value, new Task(priority_value,description,date_format.parse(time)));
            current_tasks++;
            if(i == 0)
                time_list.add(date_format.parse(time));
            if(i > 0 && !time.equals(time0)) {
                diff_time++;
                time_list.add(date_format.parse(time));
            }
            time0 = time;
        }

        Collections.sort(time_list);

        for(int i = 0; i < diff_time; i++){
            System.out.printf("TODOList %s\n",time_list.get(i).toString());
            Stack<Task> tmp_stack = new Stack<>();
            for(int x = 0; x < current_tasks; x++){
                Node<Integer,Task> tmp_n = todo_list.max();
                tmp_stack.push(tmp_n.getValue());
                if(tmp_n.getValue().getTime().equals(time_list.get(i)))
                    System.out.printf("\t%s\n", tmp_n.getValue().getDescription());

                todo_list.removeMax();
            }

            int stack_size = tmp_stack.size();
            for(int x = 0 ; x < stack_size; x++){
                Task tmp_task = tmp_stack.pop();
                todo_list.insert(tmp_task.getPriority_value(),tmp_task);
            }
        }

        System.out.println("TODOList");
        for(int i = 0; i < current_tasks; i++){
            Node<Integer,Task> tmp_n = todo_list.max();
            System.out.printf("\t%s\n", tmp_n.getValue().getDescription());
            todo_list.removeMax();
        }






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
