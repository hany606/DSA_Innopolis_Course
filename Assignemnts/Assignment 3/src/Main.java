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
    public static class Node<K,V>{
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
    public interface MergeableHeap<K,V>{
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

        public Stack<Node<K, V>> getList() {
            return list;
        }

        @Override
        public void insert(K k, V v){

            if(list.size() == 0){
                list.push(new Node<>(k,v));
                return;
            }
            Node<K,V> tmp_node = list.pop();
            if(tmp_node.getChildren().size() == 1){
                BinomialHeap<K,V> tmp_binomialHeap = new BinomialHeap<>();
                tmp_binomialHeap.insert(k,v);
                this.merge(tmp_binomialHeap);
            }
            else{
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
        public void merge_tree(Node n){
            int heap_size = list.size();
            Queue<Node<K,V>> tmp_queue = new LinkedList<>();
            for(int i = 0; i < heap_size; i++){
                Node<K,V> tmp_node = list.pop();
                int n_list_size = n.getChildren().size();
                int tmp_node_list_size = tmp_node.getChildren().size();
                if(tmp_node_list_size < n_list_size){
                    list.push(n);
                    list.push(tmp_node);
                    break;
                }
                else if(tmp_node_list_size > n_list_size){
                    //move down
                    tmp_queue.add(tmp_node);
                }
                else{
                    //real merge
                }
            }
            for(int i = 0; i < tmp_queue.size(); i++){
                list.push(tmp_queue.remove());
            }

        }
        @Override
        public void merge(BinomialHeap h){
            int heap_size = h.getList().size();
            for(int i = 0; i < heap_size; i++){
                //extract all the trees, then, merge

            }

        }
    }
    public static void main(String[] args) {
        // write your code here

        Scanner input = new Scanner(System.in);



        //Test Cases
    }

}
