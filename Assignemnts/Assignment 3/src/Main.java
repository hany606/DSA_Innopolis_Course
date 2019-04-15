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

        void merge(BinomialHeap h); //It will be mistake

    }


    public static class BinomialHeap<K extends Comparable<K>, V> implements MergeableHeap<K,V>{
        private Stack<Node<K,V>> list = new Stack<>();

        private Stack<Node<K, V>> getList() {
            return list;
        }

        @Override
        public void insert(K k, V v){

            if(list.size() == 0){
//                System.out.println("INSERT0");
                list.push(new Node<>(k,v));
                return;
            }
            Node<K,V> tmp_node = list.pop();
            if(tmp_node.getChildren().size() == 0){
//                System.out.println("INSERT--Normal--Merge");
                list.push(tmp_node);
                list.push(new Node(k,v));
                this.merge_tree();
            }
            else{
//                System.out.println("INSERT--NORMAL");

                list.push(tmp_node);
                list.push(new Node<>(k,v));
            }
        }
        @Override
        public Node max(){

            return null;
        }
        @Override
        public void removeMax(){

        }
        private void merge_tree(){       //Node<K,V>
            int heap_size = this.getList().size();
            Stack<Node<K,V>> tmp_queue = new Stack<>();
            for(int i = 0; i < heap_size; i++){
                this.print_heap();
                if(this.getList().size() == 1)
                    break;
                Node<K,V> n = list.pop();
                Node<K,V> tmp_node = list.pop();
                System.out.println(tmp_node.getKey());
                System.out.println(n.getKey());
                int n_list_size = n.getChildren().size();
                int tmp_node_list_size = tmp_node.getChildren().size();
//                System.out.printf("TMP_LIST_SIZE:%d\n",tmp_node_list_size);
                if(tmp_node_list_size < n_list_size){
                    list.push(n);
                    list.push(tmp_node);
                    break;
                }
                else if(tmp_node_list_size > n_list_size){
                    //move down
//                    tmp_queue.add(tmp_node);
                }
                else{
                    //real merge
//                    System.out.println("---Real Merge---");
                    if(tmp_node.getKey().compareTo(n.getKey()) < 0){
                        System.out.println("TMP GREATER");
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
            for(int i = 0; i < tmp_queue.size(); i++){
                list.push(tmp_queue.pop());
            }

        }
        @Override
        public void merge(BinomialHeap h){
            int heap_size = h.getList().size();
            for(int i = 0; i < heap_size; i++){
                //extract all the trees, then, merge
                Node<K,V> tmp_tree = (Node<K, V>) h.getList().pop();
                this.list.push(tmp_tree);
                merge_tree();
            }
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
                Node<K,V> n = this.getList().pop();
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
            for(int i = 0; i < heap_size; i++){
                this.list.push(tmp_list.pop());
            }
        }
    }
    public static void main(String[] args) {
        // write your code here

        Scanner input = new Scanner(System.in);




        //Test Cases
        BinomialHeap<Integer,Character> bh = new BinomialHeap<>();
        bh.insert(1,'a');
        bh.print_heap();
        bh.insert(-1,'b');
        bh.print_heap();
        bh.insert(3,'c');
        bh.print_heap();
        bh.insert(0,'d');
        bh.print_heap();
    }

}
