import java.util.NoSuchElementException;

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
        void printAll();
    }

    public static class DLL_Node <E> {

        private DLL_Node<E> next;
        private DLL_Node<E> prev;
        private E value;

        DLL_Node (E value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }
        DLL_Node () {
            this(null);
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
    public static class DLL<E> implements List<E> {

        private int size = 0;
        private DLL_Node<E> head = new DLL_Node<>();
        private DLL_Node<E> tail = new DLL_Node<>();

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
            if(size == 0) {
                throw new IndexOutOfBoundsException();
//                addFirst(o);
            }
            //if the list has greater number than this index
            else if(i+1 <= size) {
                if (i == 0)
                    addFirst(o);
                else if (i == size - 1)
                    addLast(o);
                else {
                    DLL_Node<E> target = new DLL_Node<>();
                    target.setNext(head.getNext());

                    for (int x = 0; (target.getNext() != null && x != i); x++)
                        target.setNext((target.getNext()).getNext());

                    DLL_Node<E> leftTmp = new DLL_Node<>();

                    //(__.getNext() is our node)
                    //add tmp point to the previous of our target
                    //left -> prev node
                    //target.prev -> new
                    //new.next -> target
                    //left.next -> new
                    //new.prev -> left
                    leftTmp.setNext((target.getNext()).getPrev());
                    (target.getNext()).setPrev(new DLL_Node<>(o));
                    ((target.getNext()).getPrev()).setNext(target.getNext());
                    (leftTmp.getNext()).setNext((target.getNext()).getPrev());
                    ((leftTmp.getNext()).getNext()).setPrev(leftTmp.getNext());
                    size++;

                }

            }

            //if not
            //add in the last
            else {
                throw new IndexOutOfBoundsException();
//                addLast(o);
            }

        }

        @Override
        public void addFirst(E o) {
            if (size == 0) {
                head.setNext(new DLL_Node<>(o));
                tail.setNext(head.getNext());
            }
            else {
                DLL_Node<E> tmp = new DLL_Node<>();
                tmp.setNext(head.getNext());
                head.setNext(new DLL_Node<>(o));
                (head.getNext()).setNext(tmp.getNext());
                (tmp.getNext()).setPrev(head.getNext());

            }
            size++;
            System.out.println("DONE");

        }
        @Override
        public void addLast(E o) {
            if(size == 0) {
                head.setNext(new DLL_Node<>(o));
                tail.setNext(head.getNext());
            }
            else {
                DLL_Node<E> tmp = new DLL_Node<>();
                tmp.setNext(tail.getNext());
                tail.setNext(new DLL_Node<>(o));
                (tail.getNext()).setPrev(tmp.getNext());
                (tmp.getNext()).setNext(tail.getNext());
            }
            size++;

        }

        @Override
        public void delete(E o) {
            System.out.println("Print object");
            //if found it
            if(size > 1) {
                DLL_Node<E> target = new DLL_Node<>();
                target.setNext(head.getNext());
                int x;
                for (x = 0; (target.getNext() != null && !(((target.getNext()).getValue()).equals(o))); x++)
                    target.setNext((target.getNext()).getNext());

                if(x == size || !(target.getNext().getValue()).equals(o))
                    throw new NoSuchElementException();

                if(x == 0)
                    deleteFirst();
                else if(x == size - 1)
                    deleteLast();
                else {
                    DLL_Node<E> leftTmp = new DLL_Node<>();
                    DLL_Node<E> rightTmp = new DLL_Node<>();

                    leftTmp.setNext((target.getNext()).getPrev());
                    rightTmp.setNext((target.getNext()).getNext());

                    (leftTmp.getNext()).setNext(rightTmp.getNext());
                    (rightTmp.getNext()).setPrev(leftTmp.getNext());

                    (target.getNext()).setPrev(null);
                    (target.getNext()).setNext(null);
                    size--;
                }
            }
            else if(size == 1) {
                if(head.getNext().getValue().equals(o)) {
                    head.setNext(null);
                    tail.setNext(null);
                    size--;
                }
                else
                    throw new NoSuchElementException();
            }
            //size == 0
            else
                throw new IndexOutOfBoundsException();

        }

        @Override
        public void delete(int i) {
            //if the index exist
            //generic case: we can optimize it
            // if i is equal to the size to be the tail
            // or closer to the end to work with the tail

            if(size >= i+1) {
                if(i == 0)
                    deleteFirst();
                else if(i == size -1)
                    deleteLast();
                else {
                    if (size == 1) {
                        head.setNext(null);
                        tail.setNext(null);
                    }
                    else {
                        DLL_Node<E> target = new DLL_Node<>();
                        target.setNext(head.getNext());

                        for (int x = 0; (target.getNext() != null && x != i); x++)
                            target.setNext((target.getNext()).getNext());


                        DLL_Node<E> leftTmp = new DLL_Node<>();
                        DLL_Node<E> rightTmp = new DLL_Node<>();

                        leftTmp.setNext((target.getNext()).getPrev());
                        rightTmp.setNext((target.getNext()).getNext());

                        (leftTmp.getNext()).setNext(rightTmp.getNext());
                        (rightTmp.getNext()).setPrev(leftTmp.getNext());

                        (target.getNext()).setPrev(null);
                        (target.getNext()).setNext(null);
                    }
                    size--;
                }
            }
            else
                throw new IndexOutOfBoundsException();

        }

        @Override
        public void deleteFirst() {
            //if te list is not empty
            if(size > 1) {
                head.setNext((head.getNext()).getNext());
                ((head.getNext()).getPrev()).setNext(null);
                (head.getNext()).setPrev(null);

//                delete(0);
            }
            else if(size == 1) {
                head.setNext(null);
                tail.setNext(null);

            }
            else
                throw new IndexOutOfBoundsException();
            size--;
        }

        @Override
        public void deleteLast() {
            //if the list is not empty
            if(size > 1) {
                tail.setNext((tail.getNext()).getPrev());
                ((tail.getNext()).getNext()).setPrev(null);
                (tail.getNext()).setNext(null);
//                delete(size-1);
            }
            else if(size == 1) {
                head.setNext(null);
                tail.setNext(null);
            }

            else
                throw new IndexOutOfBoundsException();
            size--;

        }

        @Override
        public void set(int i, E o) {
            //if the element exists
            if (size >= i + 1) {
                DLL_Node<E> target = new DLL_Node<>();
                target.setNext(head.getNext());

                int x;
                for (x = 0; (target.getNext() != null && x != i); x++)
                    target.setNext((target.getNext()).getNext());

                (target.getNext()).setValue(o);
            }

            else
                throw new IndexOutOfBoundsException();

        }
        @Override
        public E get(int i) {
            //if the element exists
            if (size >= i + 1) {
                DLL_Node<E> target = new DLL_Node<>();
                target.setNext(head.getNext());

                for (int x = 0; (target.getNext() != null && x != i); x++)
                    target.setNext((target.getNext()).getNext());

                return (target.getNext()).getValue();
            }

            else
                throw new IndexOutOfBoundsException();

        }
        @Override
        public void printAll() {
            System.out.printf("Size: %d\n",size);
            if(size > 0) {
                DLL_Node<E> target = new DLL_Node<>();
                target.setNext(head.getNext());
                for (int x = 0; target.getNext() != null; x++) {
                    System.out.printf("index: %d -> %s -- value: %d -- next: %s -- prev: %s\n", x,target.getNext(),(target.getNext()).getValue(),(target.getNext()).getNext(),(target.getNext()).getPrev());
                    target.setNext((target.getNext()).getNext());

                }
            }
        }
    }


    public static void main(String[] args) {
	// write your code here

        DLL<Integer> li = new DLL<>();

        li.printAll();
        li.addFirst(Integer.parseInt("10"));
        li.printAll();
        li.deleteFirst();
        li.printAll();
        li.addFirst(Integer.parseInt("9"));
        li.printAll();
        li.add(0,8);
        li.printAll();
        li.add(1,89);
        li.printAll();
        li.addLast(11);
        li.printAll();

        li.deleteFirst();
        li.printAll();
        li.addFirst(88);
        li.printAll();

        li.deleteLast();
        li.printAll();
        li.addLast(111);
        li.printAll();

//        li.delete(1);
//        li.printAll();
//        li.delete(2);
//        li.printAll();
        System.out.printf("%s\n",li.get(2));
        li.set(2,1);
        li.printAll();
        li.delete(2);
        li.printAll();
//        li.delete(Integer.parseInt("9"));
        li.printAll();
//        li.delete(Integer.parseInt("111"));
        li.printAll();
        li.delete(0);
        li.printAll();

    }
}
