package Utility;

import java.util.Iterator;

public class Queue<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue

    /**
     * Skapar en klass Node
     *
     */
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Skapar en tom kö
     *
     */
    public Queue() {
        first = null;
        last = null;
    }

    /**
     * Kollar om kön är tom
     * @return returnerar true om den är tom annars false
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Lägger till ett element i kön
     * @param item det element som ska läggas till i kön
     */
    public void enqueue(Item item) {
        Node<Item> oldlast = last;
        last = new Node<>();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldlast.next = last;
    }

    /**
     * Tar bort ett element i kön
     * @return returnerar det element som togs bort
     */
    public Item dequeue() {
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;   // to avoid loitering
        return item;
    }

    /**
     * Skapar en string av kön
     * @return returnerar stringen
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }

    /**
     * Iterator som gör att man kan iterera över kön
     *
     */
    public Iterator<Item> iterator() {
        return new LinkedIterator(first);
    }

    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}

