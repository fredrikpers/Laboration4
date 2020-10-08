package HigherGrade;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
    private final int maxN;                 // maximum number of elements on PQ
    private int n;                          // number of elements on PQ
    private final int[] prioQueue;          // binary heap using 1-based indexing
    private final int[] invPrioQueue;       // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
    private Key[] keys;                     // keys[i] = priority of i

    /**
     * Skapar en tom priority kö
     *
     */
    public IndexMinPQ(int maxN) {
        this.maxN = maxN; // Sätter max antal element till det som skickas med
        n = 0;  // Sätter antalet element till 0
        keys = (Key[]) new Comparable[maxN + 1];
        prioQueue = new int[maxN + 1];
        invPrioQueue = new int[maxN + 1];
        for (int i = 0; i <= maxN; i++)
            invPrioQueue[i] = -1;
    }

    /**
     * Returnerar true om kön är tom annars false
     *
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Kollar om i finns med i kön
     * Returnerar true om den finns annars false
     *
     */
    public boolean contains(int i) {
        return invPrioQueue[i] != -1;
    }

    /**
     * Lägger till ett nytt index,key par i kön
     *
     */
    public void insert(int i, Key key) {
        n++; //Uppdaterar antalet element i kön
        invPrioQueue[i] = n; // elementet på plats i blir n
        prioQueue[n] = i; //Sista elementet i pq blir i
        keys[i] = key; //Nyckeln på plats i blir key
        swim(n); //Swim n för att få denna på sätt ställe
    }

    /**
     * Tar bort den det minsta elementet i kön och returnerar dess index
     *
     */
    public int delMin() {
        int min = prioQueue[1];        // minsta elementet ligger på plats 1
        exch(1, n--);         // byt plats på sista och första elementet
        sink(1);             // Sänk första elementet
        invPrioQueue[min] = -1;        // Tar bort elementet genom att sätta den till -1
        keys[min] = null;    // to help with garbage collection
        prioQueue[n+1] = -1;
        return min;
    }

    /**
     * Minskar värdet på nyckeln
     *
     */
    public void decreaseKey(int i, Key key) {
        keys[i] = key; //Uppdaterar nyckeln
        swim(invPrioQueue[i]); // Sätter den på rätt ställe i heapen
    }

    /**
     * Kollar om i är större än j
     * returnerar true om så är fallet annars false
     *
     */
    private boolean greater(int i, int j) {
        return keys[prioQueue[i]].compareTo(keys[prioQueue[j]]) > 0;
    }

    /**
     * Byter plats på i och j
     *
     */
    private void exch(int i, int j) {
        int swap = prioQueue[i];
        prioQueue[i] = prioQueue[j];
        prioQueue[j] = swap;
        invPrioQueue[prioQueue[i]] = i;
        invPrioQueue[prioQueue[j]] = j;
    }

    /**
     * elementet åker upp så att den ligger på rätt ställe i heapen
     *
     */
    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    /**
     * Sänker ner ett element så att det hamnar på rätt ställe i heapen
     *
     */
    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && greater(j, j+1))
                j++;
            if (!greater(k, j))
                break;
            exch(k, j);
            k = j;
        }
    }

    /**
     * Iterator
     *
     */
    public Iterator<Integer> iterator() {
        return new HeapIterator(); }

    private class HeapIterator implements Iterator<Integer> {

        private IndexMinPQ<Key> copy;

        public HeapIterator() {
            copy = new IndexMinPQ<Key>(prioQueue.length - 1);
            for (int i = 1; i <= n; i++)
                copy.insert(prioQueue[i], keys[prioQueue[i]]);
        }

        public boolean hasNext()  {
            return !copy.isEmpty();
        }

        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }
}