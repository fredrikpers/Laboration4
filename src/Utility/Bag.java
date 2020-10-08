package Utility;

import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int size;

    /**
     * Linked list klass
     *
     */
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Skapar en tom Bag
     */
    public Bag() {
        first = null;
        size = 0;
    }

    /**
     * Returnerar storleken på Bagen
     *
     */
    public int size() {
        return size;
    }

    /**
     * Lägger till ett element i bagen
     * @param item det element som ska läggas till
     */
    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldfirst;
        size++;
    }

    /**
     * Iterator för att kunna iterera över elementen i bagen
     *
     */
    public Iterator<Item> iterator()  {
        return new LinkedIterator(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext(){
            return current != null;
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

}