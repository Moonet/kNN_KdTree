import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Xin on 4/3/16.
 */
public class BoundedPQueue<T> {
    double inf = Double.POSITIVE_INFINITY;
    private int maxsize;
    private NavigableMap<Double, T> elems = new TreeMap<>();
    private double worst;

    public BoundedPQueue(int maxSize){
        this.maxsize = maxSize;
    }

    public void enqueue(double priority, T Value){
        elems.put(priority,Value);

        // If there are too many elements in the queue, drop off the last one.
        if(Size() > maxSize()){
            elems.pollLastEntry();
        }
    }

    public T dequeue(){
        T result = elems.pollFirstEntry().getValue();
        return result;
    }

    public boolean isEmpty(){
        return elems.size() == 0;
    }

    public int Size(){
        return elems.size();
    }

    public int maxSize(){
        return maxsize;
    }

    public double worst(){
        worst = elems.lastKey();
        return isEmpty()? inf : worst;
    }

}
