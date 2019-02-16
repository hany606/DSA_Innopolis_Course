import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Data Structure & Algorithms Course (DSA)
 * Innopolis University, Sprinng 2019
 * Assignment 1
 *
 * @author Hany Hamed
 */

public class Main {
    /**
     * This is just the interface for List
     * @param <E> This is the generic type of the elements inside the List
     */
    public interface List<E>{
        /**
         * This function is made to know if the List is empty or not
         * @return boolean This returns true if the List is empty otherwise False
         */
        boolean isEmpty();

        /**
         * This method is used to add an element in certain position
         * @param i This is the position to put the element e
         * @param e This is the element we need to add
         * @return Nothing
         */
        void add(int i, E e);

        /**
         * This method is used to add an element in the first position of the List
         * @param e This is the element we need to add in the first of the List
         * @return Nothing
         */
        void addFirst(E e);

        /**
         * This method is used to add an element in the last position of the List
         * @param e This is the element we need to add
         * @return Nothing
         */
        void addLast(E e);

        /**
         * This method is used to delete a certain element from the List
         * @param e This is the element we need to delete
         * @return Nothing
         */
        void delete(E e);

        /**
         * This method is used to delete the ith element from the List
         * @param i This is the index of the element we need to delete
         * @return Nothing
         */
        void delete(int i);

        /**
         * This method is used to delete the first element from the List
         * @return Nothing
         */
        void deleteFirst();

        /**
         * This method is used to delete the last element from the List
         * @return Nothing
         */
        void deleteLast();

        /**
         * This method is used to set the value of the ith element by some value
         * @param i This is the index of the element
         * @param e This is the element we need to set its value
         * @return Nothing
         */
        void set(int i, E e);

        /**
         * This method is used to get the ith element from the List
         * @param i This is the index of the element
         * @return E This returns the ith element from the List
         */
        E get(int i);

        /**
         * This method is used to sort the elements of the List in ascending order
         * @return Nothing
         */
        void sort();

        /**
         * This method is used to print the details of the List
         * @return Nothing
         */
        void printAll();

        /**
         * This method is used to get the size of the List
         * @return int This returns the size of the List
         */
        int getSize();
    }

    /**
     * This is the implementation of List interface using Dynamic Array approach
     * @param <E> This is the generic type of the elements of the List. E should always inherit from Comparable Class
     */
    public static class DynamicArray <E extends Comparable<E>> implements List<E>{
        private int size = 0;   // Store the size of the array
        public int initialSize = 100; // Initial size of the array
        private E[] array;  // The array that represent the List


        /**
         * This is the constructor of the Class if the user didn't provide a preferred size
         */
        public DynamicArray () {
            array = (E[]) new Comparable[initialSize];
        }

        /**
         * This is the constructor of the Class in order to have a flexible size according to the user
         * @param c This is the initialSize which is provided from the user
         * @throws IndexOutOfBoundsException This exception happens when the given size is less than or equal to zero
         */
        public DynamicArray (int c) {
            if(c <= 0)
                throw new IndexOutOfBoundsException("Initial size cannot be less than or equal to Zero");

            array = (E[]) new Comparable[c];    // Safe Down Casting from array of Comparable Objects to array of E type Objects
            initialSize = c;    // Set the initial size of the array to c
        }


        /**
         * This method is used to check correctness of the given index from the user
         * @param i a number to indicate the number of elements in the List
         * @param n a number to indicate the size of the List
         * @throws IndexOutOfBoundsException
         */
        private void checkIndex(int i, int n) throws IndexOutOfBoundsException {
            if(i < 0 || i >= n)
                throw new IndexOutOfBoundsException("Index is not correct");
        }

        /**
         * {@inheritDoc}
         * @return boolean This returns true if the List is empty otherwise False
         */
        @Override
        public boolean isEmpty() {
            if(size == 0)
                return true;

            return false;
        }

        /**
         * {@inheritDoc}
         * @param i This is the position to put the element e
         * @param e This is the element we need to add
         */
        @Override
        public void add(int i, E e) {

            checkIndex(i,size+1);

            // Check if the size equals to the initial size
            if(size == initialSize) {
                E[] tmp = (E[]) new Comparable[initialSize]; // Create a tmp array from the same size

                // Copy the elements of the first array to the tmp array
                for (int x = 0; x < initialSize; x++) {
                    tmp[x] = array[x];
                }

                int originalSize = initialSize;
                initialSize *= 2;   // Double the initialSize variable
                array = (E[]) new Comparable[initialSize];  // Create a new array with the doubled number of elements
                // Copy back the elements from the tmp array to the new array
                for (int x = 0; x < originalSize; x++) {
                    array[x] = tmp[x];
                }
            }

            E[] tmp = (E[]) new Comparable[2];  // two tmp variables is made to shift the elements of the array
            // Shift right the array to add the new element in the ith position
            tmp[0] = array[i];
            for(int x = i; x < size; x++) {
                tmp[1] = array[x+1];
                array[x+1] = tmp[0];
                tmp[0] = tmp[1];
            }
            array[i] = e;   // Add the element in the ith position
            size++; // Increase the size variable of the array

        }

        /**
         * {@inheritDoc}
         * @param e This is the element we need to add in the first of the List
         */
        @Override
        public void addFirst(E e) {
            add(0,e);
        }

        /**
         * {@inheritDoc}
         * @param e This is the element we need to add
         */
        @Override
        public void addLast(E e) {
            add(size,e);
        }

        /**
         * {@inheritDoc}
         * @param e This is the element we need to delete
         */
        @Override
        public void delete(E e) {
            boolean found = false;   // Create a flag to indicate if we found the element or not

            //iterate over the array
            for(int x = 0; x < size-1; x++) {
                if(array[x] == e) found = true; // If we found the element we assert the flag
                if(found) array[x] = array[x+1];    // If the flag is asserted we begin to shift left the array elements (overwrite on the needed element)
            }
            // Empty the last element of the array if we found the element or if it was the last element in the array
            if(found || array[size-1] == e)
                array[size-1] = null;
            // If we didn't find the element we throw an NoSuchElementException
            else
                throw new NoSuchElementException();
            size--; //decrease the size variable of the array

        }

        /**
         * {@inheritDoc}
         * @param i This is the index of the element we need to delete
         */
        @Override
        public void delete(int i) {
            checkIndex(i, size);
            delete(array[i]);

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void deleteFirst() {
            delete(0);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void deleteLast() {
            delete(size-1);
        }

        /**
         * {@inheritDoc}
         * @param i This is the index of the element
         * @param e This is the element we need to set its value
         */
        @Override
        public void set(int i, E e) {
            checkIndex(i,size);
            array[i] = e;

        }

        /**
         * {@inheritDoc}
         * @param i This is the index of the element
         * @return E This returns the ith element from the List
         */
        @Override
        public E get(int i) {
            checkIndex(i,size);
            return array[i];
        }

        /**
         * This method is swap two elements of the array
         * @param i This is the index of the first element in the array
         * @param x This is the index of the second element in the array
         */
        void swap(int i, int x) {
            E tmp = array[i];   // Tmp variable from E type to store the intermediate stage of swapping
            array[i] = array[x];
            array[x] = tmp;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void sort() {
            // The first loop is used to iterate over the places of the array to choose the specific element for that position
            for(int i = 0; i < size-1; i++)
            {
                int index = i;  // This will store the index of the target element to swap which will be the smallest element in the rest subset
                for(int x = i+1; x < size; x++)
                {
                    // Get the smallest element in the subset
                    if(array[x].compareTo(array[index]) <= -1)
                        index = x;
                }
                swap(index,i);
            }

        }


        /**
         * {@inheritDoc}
         */
        @Override
        public void printAll() {

            System.out.printf("Size = %d, ", size);
            System.out.printf("Initial Size = %d\n", initialSize);
            for(int x = 0; x < size; x++)
                System.out.printf("Index: %s -- Value: %s \n", String.valueOf(x),array[x].toString());
            System.out.printf("--------------------------------------------------\n");


        }

        /**
         * {@inheritDoc}
         *
         * @return int This returns the size of the List
         */
        @Override
        public int getSize() {
            return size;
        }

    }


    /**
     * This is the Class of the Nodes of the Doubly Linked List.
     * Inherit and override from Comparable Class to have the feature of comparing between nodes depending on the value
     * @param <E> This is the generic type of the elements of the List. E should always inherit from Comparable Class
     */
    public static class DoublyLinkedList_Node <E extends Comparable<E>> implements Comparable<DoublyLinkedList_Node> {

        private DoublyLinkedList_Node<E> next;  // Store the address of the next node
        private DoublyLinkedList_Node<E> prev;  // Store the address of the previous node
        private E value;    // Store the value of the Node

        /**
         * This is the basic constructor of the Class which will fill the value with null
         */
        DoublyLinkedList_Node () {
            this(null);
        }

        /**
         * This is the constructor of the Class to store a specific value for the node
         * @param value This is the given value for the node
         */
        DoublyLinkedList_Node (E value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        /**
         * Setters and Getters
         */

        /**
         * This is used to set the next variable
         * @param next This is the address of the next node
         */
        public void setNext(DoublyLinkedList_Node<E> next) {
            this.next = next;
        }

        /**
         * This is used to set the previous variable
         * @param prev This is the address of the prev node
         */
        public void setPrev(DoublyLinkedList_Node<E> prev) {
            this.prev = prev;
        }


        /**
         * This is used to set the value variable
         * @param value This is the value of the node
         */
        public void setValue(E value) {
            this.value = value;
        }


        /**
         * This is used to get the address of the next Node
         * @return DoublyLinkedList_Node the address of the next Node
         */
        public DoublyLinkedList_Node<E> getNext() {
            return next;
        }

        /**
         * This is used to get the address of the previous Node
         * @return DoublyLinkedList_Node the address of the previous Node
         */
        public DoublyLinkedList_Node<E> getPrev() {
            return prev;
        }

        /**
         * This is used to get the value of the Node
         * @return E the value of the Node
         */
        public E getValue() {
            return value;
        }


        /**
         * This is used to compare between two nodes according to their value variable
         * @param o This is the second node
         * @return int the same values of compareTo method from Comparable Class
         */
        @Override
        public int compareTo(DoublyLinkedList_Node o) {
            return this.value.compareTo((E) o.value);
        }
    }

    /**
     * This is the implementation of List interface using Doubly Linked List approach
     * @param <E> This is the generic type of the elements of the List. E should always inherit from Comparable Class
     */
    public static class DoublyLinkedList<E extends Comparable<E>> implements List<E> {

        private int size = 0;   // Store the size of the array
        private DoublyLinkedList_Node<E> head;  // Store the address of the head of the Linked List
        private DoublyLinkedList_Node<E> tail;  // Store the address of the tail of the Linked List

        /**
         * This is the constructor of the Class to initialize the Linked List with zero number of elements
         */
        DoublyLinkedList(){
            head = new DoublyLinkedList_Node<>();
            tail = new DoublyLinkedList_Node<>();
        }

        /**
         * Getter for the head of the Linked List
         * @return E the address of the head of the Linked List
         */
        public DoublyLinkedList_Node<E> getHead() {
            return head;
        }

        /**
         * Getter for the tail of the Linked List
         * @return E the address of the tail of the Linked List
         */
        public DoublyLinkedList_Node<E> getTail() {
            return tail;
        }

        /**
         * This method is used to check correctness of the given index from the user
         * @param i a number to indicate the number of elements in the List
         * @param n a number to indicate the size of the List
         * @throws IndexOutOfBoundsException
         */
        private void checkIndex(int i, int n) throws IndexOutOfBoundsException {
            if(i < 0 || i >= n)
                throw new IndexOutOfBoundsException("Index is not correct");
        }

        /**
         * {@inheritDoc}
         * @return boolean true if the Linked List is empty, otherwise false
         */
        @Override
        public boolean isEmpty() {
            if(size == 0)
                return true;

            return false;
        }

        /**
         * {@inheritDoc}
         * @param i This is the position to put the element e
         * @param o This is the element to be added
         */
        @Override
        public void add(int i, E o) {

            checkIndex(i,size+1);
            // If the index equals to 0 add in the first of the List
            if (i == 0)
                addFirst(o);
            // If the index equals to the size add in the last of the List
            else if (i == size)
                addLast(o);
            // If in the middle
            else {
                DoublyLinkedList_Node<E> target = new DoublyLinkedList_Node<>();    // Create a new temporary target node will point on the needed position
                target.setNext(head.getNext()); // Points where the head node points to (the start of the list)

                // Loop over the List until we find the element
                for (int x = 0; x != i; x++)
                    target.setNext((target.getNext()).getNext());   // Point to the next node in the list

                DoublyLinkedList_Node<E> leftTmp = new DoublyLinkedList_Node<>();   // Create a new temporary left node to store the previous node of the target

                //left -> prev node
                //target.prev -> new
                //new.next -> target
                //left.next -> new
                //new.prev -> left
                leftTmp.setNext((target.getNext()).getPrev());  // Left points to the previous node of the target
                (target.getNext()).setPrev(new DoublyLinkedList_Node<>(o)); // Set the previous node of the target node to the new inserted node
                ((target.getNext()).getPrev()).setNext(target.getNext());   // Set the next node of the new inserted node to the target
                (leftTmp.getNext()).setNext((target.getNext()).getPrev());  // Set the next of the Left node to the previous of the target
                ((leftTmp.getNext()).getNext()).setPrev(leftTmp.getNext()); // Set the previous of the new inserted node to the left node
                size++;
            }

        }

        /**
         * {@inheritDoc}
         * @param o This is the element to be added to the first
         */
        @Override
        public void addFirst(E o) {
            // If the size equals zero, then the head node and the tail node will point to the new node
            if (size == 0) {
                head.setNext(new DoublyLinkedList_Node<>(o));
                tail.setNext(head.getNext());
            }
            // Else, just insert the node in the first place
            else {
                DoublyLinkedList_Node<E> tmp = new DoublyLinkedList_Node<>();   // Create a temporary node to store the head's node
                tmp.setNext(head.getNext());                                    // Store in the tmp the starting of the Linked List
                head.setNext(new DoublyLinkedList_Node<>(o));                   // Make the head node point to the new inserted node
                (head.getNext()).setNext(tmp.getNext());                        // Make the new inserted node point to the node which pointed by the tmp
                (tmp.getNext()).setPrev(head.getNext());                        // Make previous of the node which is pointed by the tmp
            }
            size++; // Increase the size variable of the List
        }
        @Override
        public void addLast(E o) {
            // If the size equals zero, then the head node and the tail node will point to the new node
            if(size == 0) {
                head.setNext(new DoublyLinkedList_Node<>(o));
                tail.setNext(head.getNext());
            }
            else {
                DoublyLinkedList_Node<E> tmp = new DoublyLinkedList_Node<>();   // Create a temporary node to store the tail's node
                tmp.setNext(tail.getNext());                                    // Store in the tmp the end of the Linked List
                tail.setNext(new DoublyLinkedList_Node<>(o));                   // Make the tail node point to the new inserted node
                (tail.getNext()).setPrev(tmp.getNext());                        // Make the new inserted node point to the node which pointed by the tmp
                (tmp.getNext()).setNext(tail.getNext());                        // Make previous of the node which is pointed by the tmp
            }
            size++;

        }

        @Override
        public void delete(E o) {
            // Check if there are more than one element
            if(size > 1) {
                DoublyLinkedList_Node<E> target = new DoublyLinkedList_Node<>();    // Create a target node will point to the needed node
                target.setNext(head.getNext()); // Make the target points to the start of the List
                int x; // Variable will be used to iterate over the List and store the position of that node
                // Loop until we found the element or we reached to the end
                for (x = 0; (target.getNext() != null && !(((target.getNext()).getValue()).equals(o))); x++)
                    target.setNext((target.getNext()).getNext());
                //if we reached to the end and we didn't find the element, then throw an NoSuchElementException
                if(x == size || !(target.getNext().getValue()).equals(o))
                    throw new NoSuchElementException();

                // If the target element was the first element of the List, deleteFirst()
                if(x == 0)
                    deleteFirst();
                // If the target element was the last element of the List, deleteLast()
                else if(x == size - 1)
                    deleteLast();
                // If the target element was in the middle
                else {
                    DoublyLinkedList_Node<E> leftTmp = new DoublyLinkedList_Node<>();   // Node to point the previous node of the target
                    DoublyLinkedList_Node<E> rightTmp = new DoublyLinkedList_Node<>();  // Node to point the next node of the target

                    leftTmp.setNext((target.getNext()).getPrev());  // Point the previous node of the target
                    rightTmp.setNext((target.getNext()).getNext()); // Point the previous node of the target

                    (leftTmp.getNext()).setNext(rightTmp.getNext());    // Make the previous point to the next
                    (rightTmp.getNext()).setPrev(leftTmp.getNext());    // and the next point to the previous

                    // Detach the target node
                    (target.getNext()).setPrev(null);
                    (target.getNext()).setNext(null);
                    size--; // Decrement the size variable of the List
                }
            }

            // If the size equals one, then delete and make head and tail not pointing to anything
            else if(size == 1) {
                //If the element exist and it is only element in the List
                if(head.getNext().getValue().equals(o)) {
                    head.setNext(null);
                    tail.setNext(null);
                    size--;
                }
                // If the element doesn't exist
                else
                    throw new NoSuchElementException();
            }
            // If the size equals to zero
            else
                throw new IndexOutOfBoundsException();

        }

        @Override
        public void delete(int i) {
            checkIndex(i, size);

            // If we need to delete the first element
            if(i == 0)
                deleteFirst();
            // If we need to delete the last element
            else if(i == size -1)
                deleteLast();
            // If the element was in the middle
            else {
                // If the size equals one
                if (size == 1) {
                    // Detach the node
                    head.setNext(null);
                    tail.setNext(null);
                }
                else {
                    DoublyLinkedList_Node<E> target = new DoublyLinkedList_Node<>();    // Node to point the target node we need to delete (Will be used to loop over the list)
                    target.setNext(head.getNext()); // Point to the start of the List

                    // Loop over the List until you find it
                    for (int x = 0; x != i; x++)
                        target.setNext((target.getNext()).getNext());   // Point to the next node


                    DoublyLinkedList_Node<E> leftTmp = new DoublyLinkedList_Node<>();   // Node to point the previous node of the target
                    DoublyLinkedList_Node<E> rightTmp = new DoublyLinkedList_Node<>();  // Node to point the next node of the target

                    leftTmp.setNext((target.getNext()).getPrev());  // Point the previous node of the target
                    rightTmp.setNext((target.getNext()).getNext()); // Point the previous node of the target

                    (leftTmp.getNext()).setNext(rightTmp.getNext());    // Make the previous point to the next
                    (rightTmp.getNext()).setPrev(leftTmp.getNext());    // and the next point to the previous

                    // Detach the target node
                    (target.getNext()).setPrev(null);
                    (target.getNext()).setNext(null);
                }
                size--; // Decrement the size variable of the List
            }

        }

        @Override
        public void deleteFirst() {
            // If the list has more than one element
            if(size > 1) {
                head.setNext((head.getNext()).getNext());   // Make the head point to the node next to the start
                ((head.getNext()).getPrev()).setNext(null); // Make the next of the first one equal to null
                (head.getNext()).setPrev(null);             // Make the prev of the first one equal to null
            }
            // If the List has at least one node
            else if(size == 1) {
                // Detach the only node
                head.setNext(null);
                tail.setNext(null);
            }
            // If the List is empty
            else
                throw new IndexOutOfBoundsException();
            size--; // Decrement the size variable
        }

        @Override
        public void deleteLast() {
            // If the list has more than one element
            if(size > 1) {
                tail.setNext((tail.getNext()).getPrev());   // Make the tail point to the previous node to the end
                ((tail.getNext()).getNext()).setPrev(null); // Make the prev of the last one equal to null
                (tail.getNext()).setNext(null);             // Make the next of the last one equal to null
            }
            // If the List has at least one node
            else if(size == 1) {
                // Detach the only node
                head.setNext(null);
                tail.setNext(null);
            }

            // If the List is empty
            else
                throw new IndexOutOfBoundsException();
            size--; // Decrement the size variable

        }

        /**
         * {@inheritDoc}
         * @param i This is the index of the element
         * @param o This is the new value of the element
         */
        @Override
        public void set(int i, E o) {

            checkIndex(i,size);

            DoublyLinkedList_Node<E> target = new DoublyLinkedList_Node<>();    // Node to point to the target node (It is used to iterate over the List)
            target.setNext(head.getNext()); // Point to the beginning of the List

            // Loop over the List
            for (int x = 0; x != i; x++)
                target.setNext((target.getNext()).getNext());

            (target.getNext()).setValue(o); // Change the value of the target node

        }

        /**
         * {@inheritDoc}
         * @param i This is the index of the element
         * @return E The ith element
         */
        @Override
        public E get(int i) {
            checkIndex(i,size);

            DoublyLinkedList_Node<E> target = new DoublyLinkedList_Node<>();    // Node to point to the target node (It is used to iterate over the List)
            target.setNext(head.getNext()); // Point to the beginning of the List

            // Loop over the List
            for (int x = 0; x != i; x++)
                target.setNext((target.getNext()).getNext());

            // Return the needed element
            return (target.getNext()).getValue();

        }

        /**
         * This method is used to swap two elements in the List
         * @param i the index of the first element
         * @param x the index of the second element
         */
        void swap(DoublyLinkedList_Node<E> i, DoublyLinkedList_Node<E> x) {
            E tmp = (i.getNext()).getValue();
            (i.getNext()).setValue((x.getNext()).getValue());
            (x.getNext()).setValue(tmp);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void sort() {
            DoublyLinkedList_Node<E> it0 = new DoublyLinkedList_Node<>(); // Node to iterate over the List
            it0.setNext(head.getNext());    // Points to the start of the List

            // Iterate on the List
            while(it0.getNext().getNext() != null)
            {
                DoublyLinkedList_Node<E> it1 = new DoublyLinkedList_Node<>();   // Iterate on the unsorted subset
                it1.setNext((it0.getNext()).getNext()); // Points to the start of the subset

                DoublyLinkedList_Node<E> target = new DoublyLinkedList_Node<>();    // This will point to the minimum node
                target.setNext(it0.getNext());  // Points to the start of the subset

                // Iterate on the subset
                while(it1.getNext() != null) {
                    // get the minimum node
                    if((it1.getNext()).compareTo(target.getNext()) <= -1)
                        target.setNext(it1.getNext());

                    it1.setNext((it1.getNext()).getNext()); // Point to the next node

                }

                swap(it0,target);   // Swap the minimum and the pivot
                it0.setNext((it0.getNext()).getNext()); // Point to the next node
            }

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void printAll() {
            System.out.printf("Size: %d\n",size);
            if(size == 1)
                System.out.printf("index: %s -> %s -- value: %s -- next: %s -- prev: %s\n", String.valueOf(0),head.getNext().toString(),(head.getNext()).getValue().toString(),null,null);
            if(size > 1) {
                DoublyLinkedList_Node<E> target = new DoublyLinkedList_Node<>();
                target.setNext(head.getNext());
                System.out.printf("index: %s -> %s -- value: %s -- next: %s -- prev: %d\n", String.valueOf(0), target.getNext().toString(), (target.getNext()).getValue().toString(), (target.getNext()).getNext().toString(), null);
                target.setNext(target.getNext().getNext());
                for (int x = 1; target.getNext() != null; x++) {
                    if(target.getNext().getNext() == null) {
                        System.out.printf("index: %s -> %s -- value: %s -- next: %d -- prev: %s\n", String.valueOf(x), target.getNext().toString(), (target.getNext()).getValue().toString(), null, (target.getNext()).getPrev().toString());
                        break;
                    }
                    else
                        System.out.printf("index: %s -> %s -- value: %s -- next: %s -- prev: %s\n", String.valueOf(x),target.getNext().toString(),(target.getNext()).getValue().toString(),(target.getNext()).getNext().toString(),(target.getNext()).getPrev().toString());
                    target.setNext((target.getNext()).getNext());
                }
            }
        }

        /**
         * {@inheritDoc}
         * @return int the size of the List
         */
        @Override
        public int getSize() {
            return size;
        }

    }


    public static class Team implements Comparable {
        private String name;
        private int teamRank;
        private int numGames;
        private int wins;
        private int ties;
        private int goalsScored;
        private int goalsAgainst;

        public Team (String name, int teamRank){
            this.name = name;
            this.teamRank = teamRank;
            this.numGames = 0;
            this.wins = 0;
            this.ties = 0;
            this.goalsScored = 0;
            this.goalsAgainst = 0;
        }

        public int getGoalsAgainst() {
            return goalsAgainst;
        }

        public int getGoalsScored() {
            return goalsScored;
        }

        public int getNumGames() {
            return numGames;
        }

        public int getTeamRank() {
            return teamRank;
        }

        public int getTies() {
            return ties;
        }

        public int getWins() {
            return wins;
        }

        public int getLosses() {
            return(this.numGames-(this.wins+this.ties));
        }
        public int getTotalPoints() {
            return (this.wins * 3 + this.ties * 1);
        }
        public int getGoalsDifference() {
            return (this.goalsScored - this.goalsAgainst);
        }

        public String getName() {
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }

        public void setGoalsAgainst(int goalsAgainst) {
            this.goalsAgainst = goalsAgainst;
        }

        public void setGoalsScored(int goalsScored) {
            this.goalsScored = goalsScored;
        }

        public void setNumGames(int numGames) {
            this.numGames = numGames;
        }

        public void setTeamRank(int teamRank) {
            this.teamRank = teamRank;
        }

        public void setTies(int ties) {
            this.ties = ties;
        }

        public void setWins(int wins) {
            this.wins = wins;
        }

        public void addWin() {
            this.wins++;
        }
        public void addTie() {
            this.ties++;
        }
        public void addNumGames() {
            this.numGames++;
        }
        public void addGoalsScored(int i) {
            this.goalsScored += i;
        }
        public void addGoalsAgainst(int i) {
            this.goalsAgainst += i;
        }


        @Override
        public int compareTo(Object o) {
            Team tmp = (Team) o;
            if(this.getTotalPoints() > tmp.getTotalPoints()) return 1;
            else if(this.getTotalPoints() < tmp.getTotalPoints()) return -1;
            else {
                if(this.getWins() > tmp.getWins()) return 1;
                else if(this.getWins() < tmp.getWins()) return -1;
                else {
                    if(this.getGoalsDifference() > tmp.getGoalsDifference()) return 1;
                    else if(this.getGoalsDifference() < tmp.getGoalsDifference()) return -1;
                    else return (this.name.compareTo(tmp.getName()));

                }
            }
        }

        public String toString() {
            return String.format("%s %dp, %dg (%d-%d-%d), %dgd (%d-%d)",getName(),getTotalPoints(),getNumGames(),getWins(),getTies(),getLosses(),getGoalsDifference(),getGoalsScored(),getGoalsAgainst());
        }

    }


    public static void main(String[] args) {
	// write your code here
//        /*
        Scanner input = new Scanner(System.in);
        int N = Integer.parseInt(input.nextLine());

        for(int t = 0; t < N; t++) {
            String tournamentName = input.nextLine();
            System.out.println(tournamentName);
            int T = Integer.parseInt(input.nextLine());
            List<Team> teams = new DynamicArray<>();
//            List<Team> teams = new DoublyLinkedList<>();

            for (int i = 0; i < T; i++) {
                String teamName = input.nextLine();
                Team tmp = new Team(teamName, i+1);
                teams.addLast(tmp);
            }

            int G = Integer.parseInt(input.nextLine());
            for(int g = 0; g < G; g++) {
//                System.out.println("--------New--------");
                String s = input.nextLine();
                boolean flag = false, flag1 = false;
                String teamName1 = "", teamName2 = "";
                int teamGoals1 = 0, teamGoals2 = 0;
                int hashIndex = 0, colIndex = 0;
                for(int x = 0; x < s.length(); x++) {
                    if(s.charAt(x) == '#') {
                        if(flag == false) {
                            teamName1 = s.substring(0,x);
                            hashIndex = x;
                            flag = true;
                            continue;
                        }
                        else {
                            teamGoals2 = Integer.parseInt(s.substring(colIndex+1,x));
                            teamName2 = s.substring(x+1,s.length());
                            break;
                        }
                    }
                    if(s.charAt(x) == ':') {
                        colIndex = x;
                        teamGoals1 = Integer.parseInt(s.substring(hashIndex + 1, x));
                        flag1 = true;
                        continue;
                    }
                }
//                                System.out.printf("Team1: %s -> %d \t Team2: %s -> %d\n",teamName1,teamGoals1,teamName2,teamGoals2);

                for(int i = 0; i < T; i++) {
                    Team tmp = teams.get(i);
                    if (tmp.name.equals(teamName1)) {
                        tmp.addNumGames();
                        tmp.addGoalsScored(teamGoals1);
                        tmp.addGoalsAgainst(teamGoals2);
                        if (teamGoals1 > teamGoals2) tmp.addWin();
                        else if (teamGoals1 == teamGoals2) tmp.addTie();
                        teams.set(i, tmp);
                    }
                    if(tmp.name.equals(teamName2)) {
                        tmp.addNumGames();
                        tmp.addGoalsScored(teamGoals2);
                        tmp.addGoalsAgainst(teamGoals1);
                        if(teamGoals2 > teamGoals1) tmp.addWin();
                        else if(teamGoals2 == teamGoals1) tmp.addTie();
                        teams.set(i,tmp);
                    }
                }



//                System.out.printf("Team1: %s -> %d \t Team2: %s -> %d\n",teamName1,teamGoals1,teamName2,teamGoals2);
            }
            teams.sort();
//            teams.printAll();
            for(int i = T-1; i >= 0; i--) {
                Team tmp = teams.get(i);
                System.out.printf("%d) "+tmp.toString()+'\n',T-i);
            }
//            System.out.println("Finish teams------------");
            System.out.println("");
        }
//        */
    /*
//        List<Integer> li = new DoublyLinkedList<>();
        List<Integer> li = new DynamicArray<>();

        //try add from first using the index and last and also delete

        li.printAll();
        li.add(0,Integer.parseInt("11"));
        li.printAll();
        li.addFirst(Integer.parseInt("10"));
        li.printAll();
        li.addFirst(Integer.parseInt("8"));
        li.printAll();

        li.add(1,Integer.parseInt("9"));

        li.printAll();
        li.deleteFirst();
        li.printAll();
        li.addFirst(Integer.parseInt("7"));
        li.printAll();
        li.add(0,6);
        li.printAll();
        li.add(1,61);
        li.printAll();
        li.addLast(12);
        li.printAll();

        li.deleteFirst();
        li.printAll();
        li.addFirst(59);
        li.printAll();

        li.deleteLast();
        li.printAll();
        li.addLast(13);
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

        li.deleteFirst();
        li.deleteFirst();
        li.deleteFirst();
        li.deleteFirst();
        li.deleteFirst();
        li.printAll();

        for(int i = 6; i >= 0; i--) {
            li.addLast(i);
        }
        li.printAll();
        li.sort();
        li.printAll();


//        System.out.println(li.sort(1,0));
//    */

    }
}
