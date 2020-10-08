package Utility;

/**
 * Symbol table som har tagits från Princeton
 * Några justeringar samt nya kommentarer har gjorts.
 */
public class ST<Key extends Comparable<Key>, Value> {
    private final Key[] keys;
    private final Value[] vals;
    private int n = 0;

    public ST(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        vals = (Value[]) new Object[capacity];
    }

    /**
     * Returnerar storleken på Utility.ST
     */
    public int size() {
        return n;
    }

    /**
     * Om Utility.ST är tom returnera true annars false.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Kollar om en viss nyckel finns i Utility.ST
     * Om ja, returnera true annars false.
     */
    public boolean contains(Key key) {

        return get(key) != null;
    }

    /**
     * Returnerar värdet av en viss nyckel
     * Detta görs i log(N).
     */
    public Value get(Key key) {
        if (isEmpty())
            return null;

        int i = rank(key);
        if (i < n && keys[i].compareTo(key) == 0) //Kollar de är lika
            return vals[i]; // Returnerar nyckels värde
        return null; //Om nyckeln inte finns returnera null
    }

    /**
     * Letar upp indexet för den nyckeln vi söker
     * Detta görs genom delning för att få det att gå i Log(N) tid.
     * Om nyckeln inte hittas så returneras antalet nycklar som är mindre än denna.
     */
    public int rank(Key key) {

        int lo = 0, hi = n-1; //Minsta och högsta index

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2; //Beräknar mittersta indexet
            int cmp = key.compareTo(keys[mid]); //Jämför detta med nyckeln vi letar efter

            if (cmp < 0) //Om mindre än 0 så finns det på vänstra delen
                hi = mid - 1;
            else if (cmp > 0) //Om större än noll finns det på högra sidan
                lo = mid + 1;
            else //Om de är lika returnera det indexet
                return mid;
        }
        return lo; //Om vi inte hittar det vi söker så returnera antalet nycklar mindre än denna
    }
    /**
     * Lägger till ett nytt key-value par i symbol tabellen. Om en nyckel redan finns
     * med så skrivs värdet över med den nya värdet.
     */
    public void put(Key key, Value val)  {

        int i = rank(key); //Returnerar precis på vilket index vi ska sätta det nya värdet.

        // key is already in table
        if (i < n && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        }

        for (int j = n; j > i; j--)  { //Går igenom hela vår Utility.ST och flyttar alla element ett steg åt höger
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }
        keys[i] = key; //Nya värdet
        vals[i] = val; //Nya värdet

        n++; //Uppdaterar storleken på Utility.ST
    }

    /**
     * Returnerar nyckeln på första indexet
     */
    public Key min() {
        return keys[0];
    }

    /**
     * Returnerar nyckeln på sista indexet
     *
     */
    public Key max() {
        return keys[n-1];
    }

    /**
     * Returnerar en nyckel med ett visst index k
     *
     */
    public Key select(int k) {
        return keys[k];
    }

    /**
     * Returnerar alla nycklar i Utility.ST som en Iterable
     * Detta för att vi enkelt ska kunna gå igenom
     * alla nycklar och deras värden i Symbol tabellen
     * @return alla nycklar i symbol tabellen, som en kö.
     */
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    /**
     * Returnerar alla element i Utility.ST som en kö.
     * Detta för att vi enkelt ska kunna iterera över den
     * med en foreach loop.
     *
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<>(); //Skapar en kö
        if (lo.compareTo(hi) > 0) //Om det inte finns några element så returnerar vi en tom kö.
            return queue;

        for (int i = rank(lo); i < rank(hi); i++) //Lägger till alla nycklar i kön
            queue.enqueue(keys[i]);
        if (contains(hi)) queue.enqueue(keys[rank(hi)]);
            return queue; //Returnerar kön
    }

}
