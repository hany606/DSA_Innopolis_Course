import sun.jvm.hotspot.debugger.windbg.DLL;

public class Main {

    public interface List<E>{
        boolean isEmpty();
        void add(int i, E e);
        void addFirst(E e);
        void addLast(E e);
        void delete(E e);
        void delete(int i);
        void deleteFirst();
        void deleteLast();
        void set(int i, E e);
        E get(int i);
    }

    public class DLL_Node <E> {

        private DLL_Node<E> next;
        private DLL_Node<E> prev;
        private E value;

        DLL_Node (E value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        public void setNext(DLL_Node<E> next) {
            this.next = next;
        }

        public void setPrev(DLL_Node<E> prev) {
            this.prev = prev;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public DLL_Node<E> getNext() {
            return next;
        }

        public DLL_Node<E> getPrev() {
            return prev;
        }

        public E getValue() {
            return value;
        }
    }

    //-----------------------------------------------------------------------
    public class DLL<E> implements List<E> {

        private int size = 0;
        private DLL_Node<E> head;
        private DLL_Node<E> tail;

        DLL(){
            head.setPrev(null);
            head.setNext(null);
            tail.setPrev(null);
            tail.setNext(null);
        }

        public DLL_Node<E> getHead() {
            return head;
        }

        public DLL_Node<E> getTail() {
            return tail;
        }

        //add check index method for exceptions

        @Override
        public boolean isEmpty() {
            if(size == 0)
                return true;

            return false;
        }

        @Override
        public void add(int i, E o) {

            //to initialize the list
            if(size == 0)
                addFirst(o);

            //if the list has greater number than this index
            else if(i+1 <= size) {
                DLL_Node<E> target = head;
                for (int x = 0; (x < size && x != i); x++)
                    target = target.getNext();


                (target.getPrev()).setNext(new DLL_Node<>(o));
                ((target.getPrev()).getNext()).setNext(target);
                ((target.getPrev()).getNext()).setPrev(target.getPrev());
                target.setPrev((target.getPrev()).getNext());
            }

            //if not
            //add in the last
            else
                addLast(o);

            size++;

        }

        @Override
        public void addFirst(E o) {
            size++;
            DLL_Node<E> tmp = head;
            head = new DLL_Node<>(o);
            head.setNext(tmp);
            head.setPrev(null);
            tmp.setPrev(head);
        }

        @Override
        public void addLast(E o) {
            size++;
            DLL_Node<E> tmp = tail;
            tail = new DLL_Node<>(o);
            tail.setPrev(tmp);
            tail.setNext(null);
            tmp.setNext(tail);
        }

        @Override
        public void delete(E o) {
            //if found it
            if(size != 0) {
                DLL_Node<E> target = head;

                for (int x = 0; (x < size && (target.getNext()).getValue() != o); x++)
                    target = target.getNext();

                (target.getNext()).setPrev(null);
                target.setNext((target.getNext()).getNext());
                (target.getNext()).setPrev(target);
                size--;
            }

        }

        @Override
        public void delete(int i) {
            //if the index exist
            //generic case: we can optimize it
            // if i is equal to the size to be the tail
            // or closer to the end to work with the tail
            if(size >= i+1) {
                DLL_Node<E> target = head;

                for (int x = 0; (x < size && x != i); x++)
                    target = target.getNext();

                (target.getNext()).setPrev(null);
                target.setNext((target.getNext()).getNext());
                (target.getNext()).setPrev(target);
                size--;
            }
        }

        @Override
        public void deleteFirst() {
            //if te list is not empty
            size--;

        }

        @Override
        public void deleteLast() {
            //if the list is not empty
            size--;
        }

        @Override
        public void set(int i, E o) {
            //if the element exists
        }

        @Override
        public E get(int i) {
            //if the element exists

            return null;
        }
    }


    public static void main(String[] args) {
	// write your code here

    }
}
