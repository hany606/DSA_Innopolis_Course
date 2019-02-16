

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Scanner;


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
        void sort();
        void printAll();
    }

    public static class DA <E extends Comparable<E>> implements List<E>, Comparator {
        private int size = 0;
        public int initialSize = 1;
        private E[] array;
        public DA (int c) {
            if(c <= 0)
                throw new IndexOutOfBoundsException("Initial size cannot be less than or equal to Zero");
            array = (E[]) new Comparable[c];
            initialSize = c;
        }

        public DA () {
            array = (E[]) new Comparable[initialSize];
        }


        @Override
        public boolean isEmpty() {
            if(size == 0)
                return true;

            return false;
        }
        //add function to move, expand and copy

        @Override
        public void add(int i, E e) {
            if(i > size && i != 0) {
                throw new IndexOutOfBoundsException();
            }
            else {
                if(size == initialSize) {
                    System.out.println("Double");
                    //copy, expand, move
                    E[] array2 = (E[]) new Comparable[initialSize];
                    for (int x = 0; x < initialSize; x++) {
                        array2[x] = array[x];
                    }

                    int originalSize = initialSize;
                    initialSize *= 2;
                    array = (E[]) new Comparable[initialSize];

                    for (int x = 0; x < originalSize; x++) {
                        array[x] = array2[x];
                    }
                }
                //shift
                E[] tmp = (E[]) new Comparable[2];
//                E[] tmp2 = (E[]) new Comparable[1];
                tmp[0] = array[i];
                for(int x = i; x < size; x++) {
                    tmp[1] = array[x+1];
                    array[x+1] = tmp[0];
                    tmp[0] = tmp[1];

                }
                array[i] = e;
                size++;
            }
        }

        @Override
        public void addFirst(E e) {
            add(0,e);
        }

        @Override
        public void addLast(E e) {
            add(size,e);
        }

        @Override
        public void delete(E e) {
            boolean flag = false;
            for(int x = 0; x < size-1; x++) {
                System.out.printf("%d -> %d\n", array[x],e);
                if(array[x] == e) flag = true;
                if(flag) {
                    array[x] = array[x+1];
                }
            }
            if(flag || array[size-1] == e)
                array[size-1] = null;
            else
                throw new NoSuchElementException();
            size--;

        }

        @Override
        public void delete(int i) {
            if(i+1 > size) {
                throw new IndexOutOfBoundsException();
            }
            else {
                delete(array[i]);
            }
        }

        @Override
        public void deleteFirst() {
            delete(0);
        }

        @Override
        public void deleteLast() {
            delete(size-1);
        }

        @Override
        public void set(int i, E e) {
            if(i+1 > size )
                throw new IndexOutOfBoundsException();
            else
                array[i] = e;

        }

        @Override
        public E get(int i) {
            if(i+1 > size)
                throw new IndexOutOfBoundsException();
            else
                return array[i];
        }

        void swap(int i, int x) {
            E tmp = array[i];
            array[i] = array[x];
            array[x] = tmp;
        }

        @Override
        public void sort() {
//            return array[i].compareTo(array[x]); // compareTo: asserts like greater i_th > x_th
            for(int i = 0; i < size; i++)
            {
                int index = i;
                for(int x = i; x < size; x++)
                {
                    //get the smallest element than it
                    if(array[x].compareTo(array[index]) == -1) {
                        index = x;
                    }
                }
                swap(index,i);
            }

        }

        @Override
        public void printAll() {
            System.out.printf("Size = %d, ", size);
            System.out.printf("Initial Size = %d\n", initialSize);
            for(int x = 0; x < size; x++)
                System.out.printf("index: %d -- value: %d \n", x,array[x]);
            System.out.printf("-------------------------\n");


        }


        @Override
        public int compare(Object o1, Object o2) {
            E e1 = (E) o1;
            E e2 = (E) o2;
            return e1.compareTo(e2);
        }
    }

    public static class DLL_Node <E extends Comparable<E>> implements Comparable<DLL_Node> {

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


        @Override
        public int compareTo(DLL_Node o) {
            return this.value.compareTo((E) o.value);
        }
    }

    //-----------------------------------------------------------------------
    public static class DLL<E extends Comparable<E>> implements List<E>, Comparator {

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
            if(i+1 > size && (i != 0)) {
                throw new IndexOutOfBoundsException();
//                addFirst(o);
            }
            //if the list has greater number than this index
            //else if(i+1 <= size) {
             else {
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

        void swap(DLL_Node<E> i, DLL_Node<E> x) {
//            System.out.printf("it1-> %d , it0-> %d\n", i.getNext().getValue(), x.getNext().getValue());
            E tmp = (i.getNext()).getValue();
            (i.getNext()).setValue((x.getNext()).getValue());
            (x.getNext()).setValue(tmp);
        }

        @Override
        public void sort() {
//            return array[i].compareTo(array[x]); // compareTo: asserts like greater i_th > x_th
            DLL_Node<E> it0 = new DLL_Node<>();
            it0.setNext(head.getNext());
            for(int i = 0; it0.getNext() != null; i++)
            {
                DLL_Node<E> it1 = new DLL_Node<>();
                it1.setNext(it0.getNext());
                DLL_Node<E> target = new DLL_Node<>();
                target.setNext(it0.getNext());

                for (int x = 0; it1.getNext() != null; x++) {

                    if((it1.getNext()).compareTo(it0.getNext()) == -1) {

                        target.setNext(it1.getNext());
                    }

                    it1.setNext((it1.getNext()).getNext());

                }

                swap(it0,target);
                it0.setNext((it0.getNext()).getNext());
            }

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

        @Override
        public int compare(Object o1, Object o2) {
            E e1 = (E) o1;
            E e2 = (E) o2;
            return e1.compareTo(e2);
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
            this.wins++;
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
            return 0;
        }

    }


    public static void main(String[] args) {
	// write your code here
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();

        for(int t = 0; t < N; t++) {
            System.out.println(input.next());
            int T = input.nextInt();
            List<Team> teams = new DA<>(T+1);

            for (int i = 0; i < T; i++) {
                String teamName = input.next();
                Team tmp = new Team(teamName, i+1);
                teams.addLast(tmp);
            }

            int G = input.nextInt();
            for(int i = 0; i < G; i++) {
                String s = input.next();
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
                
//                System.out.printf("Team1: %s -> %d \t Team2: %s -> %d\n",teamName1,teamGoals1,teamName2,teamGoals2);
            }
        }

    /*
        List<Integer> li = new DLL<>();
//        List<Integer> li = new DA<>();

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
