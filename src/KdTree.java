import java.util.*;


/**
 * Created by Xin on 4/2/16.
 */
public class KdTree<T> {
    private Node root;
    private int K;
    private class Node{
        private PointKD key;
        T v;
        private Node left, right;
        private int N;
        private int level;

        private Node(PointKD key, T value, int N, int lvl){
            this.key = key;
            this.v = value;
            this.left = null;
            this.right = null;
            this.N = N;
            this.level = lvl;
        }
    };

    //Constructs a new, empty KDTree.
    public KdTree(){

    }

    public KdTree(int K){
        this.K = K;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.N;
    }

    public boolean contains(PointKD key) {
        if (key == null) throw new NullPointerException("argument to contains() is null");
        return get(key) != null;
    }


    public void put(PointKD pointKD, T val, int lvl){
        if (pointKD == null)
            throw new NullPointerException("first argument to put() is null");
        root = put(root, pointKD, val, lvl);
    }

    //Inserts the specified point into the KDTree with associated value value.
    private Node put(Node x, PointKD pointKD, T val, int lvl){

        int next_lvl = (lvl+1) % K;
        if(x == null){
            return new Node(pointKD, val, 1 ,lvl);
        }

        // value is same
        if(pointKD.equals(x.key)){
            x.v = val;
        }
        else if(pointKD.pointkd[lvl] < x.key.pointkd[lvl]){
            x.left = put(x.left, pointKD, val, next_lvl);
        }
        else {
            x.right = put(x.right, pointKD, val, next_lvl);
        }
        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }

    public T get(PointKD key) {
        return get(root, key, 0);
    }


    private T get(Node x, PointKD pointKD, int lvl) {
        int next_lvl = (lvl+1) % K;
        if(x == null){
            return null;
        }

        if(pointKD.equals(x.key)){
            return x.v;
        }
        else if(pointKD.pointkd[lvl] < x.key.pointkd[lvl]){
            return get(x.left, pointKD, next_lvl);
        }
        else {
            return get(x.right, pointKD, next_lvl);
        }
    }

    public Object kNNValue(PointKD pointKD, int K){
        BoundedPQueue k_nearest_points = new BoundedPQueue(K);
        get_all_nearest_points(pointKD,k_nearest_points,root);
        return most_occur(k_nearest_points);
    }

    private void get_all_nearest_points(PointKD pointKD, BoundedPQueue knp, Node curr){
        boolean toleft = false;
        if(curr == null){
            return;
        }
        // enqueue curr into bpq with priority distance(P, curr)
        double distance = pointKD.distanceSquaredTo(curr.key);
        knp.enqueue(distance, curr.key);
        double value = knp.worst();

        // Recursively search the half of the tree that contains the test point.
        int keyIndex = curr.level % K;
        if(pointKD.pointkd[keyIndex] < curr.key.pointkd[keyIndex]){
            get_all_nearest_points(pointKD, knp, curr.left);

        }else{
            get_all_nearest_points(pointKD, knp, curr.right);
            toleft = true;
        }

        // If the candidate hypersphere crosses this splitting plane,
        // look on the other side of the plane by examining the other subtree.
        if(knp.Size()!= knp.maxSize()||
                (curr.key.pointkd[keyIndex] - pointKD.pointkd[keyIndex])
                        *(curr.key.pointkd[keyIndex] - pointKD.pointkd[keyIndex]) < value){
            if(toleft)
                get_all_nearest_points(pointKD, knp, curr.left);
            else
                get_all_nearest_points(pointKD, knp, curr.right);
        }


    }

    private Object most_occur(BoundedPQueue knp){

        Map<Object, Integer> m = new HashMap<>();

        List<Object> points = new ArrayList<>();
        while (!knp.isEmpty()){
            points.add(knp.dequeue());
        }

        for (Object a : points) {
            Integer freq = m.get(a);
            m.put(a, (freq == null) ? 1 : freq + 1);
        }

        int max = -1;
        Object mostFrequent = -1;

        for (Map.Entry<Object, Integer> e : m.entrySet()) {
            if (e.getValue() > max) {
                mostFrequent = e.getKey();
                max = e.getValue();
            }
        }

        return mostFrequent;

    }

}
