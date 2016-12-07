import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Xin on 4/6/16.
 */
public class Main {

    public static void main(String[] args) {


        KdTree test = new KdTree(2);
        readFileToTree(test, "input1M.txt");
        double[] x = new double[]{0.1, 0.2};
        PointKD pointKD = new PointKD(x);
        long start = System.nanoTime();
        System.out.println(test.kNNValue(pointKD,1));
        long end = System.nanoTime();
        System.out.println(end - start);


    }

    private void basicTest(){
        KdTree kdTree = new KdTree(3);

        double[] x = new double[]{3, 1, 4};
        PointKD pointKD = new PointKD(x);

        kdTree.put(pointKD,1,0);

        x = new double[]{2, 3, 7};
        pointKD = new PointKD(x);
        kdTree.put(pointKD,1,0);

        x = new double[]{4, 3, 4};
        pointKD = new PointKD(x);
        kdTree.put(pointKD,1,0);

        x = new double[]{2, 1, 3};
        pointKD = new PointKD(x);
        kdTree.put(pointKD,1,0);

        x = new double[]{6, 1, 4};
        pointKD = new PointKD(x);
        kdTree.put(pointKD,7,0);

        x = new double[]{5, 2, 5};
        pointKD = new PointKD(x);
        kdTree.put(pointKD,7,0);

        x = new double[]{6, 1, 4};
        pointKD = new PointKD(x);
        kdTree.put(pointKD,8,0);

        x = new double[]{2, 4, 5};
        pointKD = new PointKD(x);
        kdTree.put(pointKD,7,0);

        x = new double[]{1, 4, 4};
        pointKD = new PointKD(x);
        kdTree.put(pointKD,22,0);

        System.out.println(kdTree.get(pointKD));

        x = new double[]{1, 3, 3};
        pointKD = new PointKD(x);
        System.out.println(kdTree.kNNValue(pointKD,1));
    }

    public static void readFileToTree(KdTree kdt, String fileUrl) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileUrl));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] numbers = line.split(" ");
                double[] x = new double[]{Double.parseDouble(numbers[0]),Double.parseDouble(numbers[1])};
                kdt.put(new PointKD(x),1,0);
                // Do something with line
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KdTree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KdTree.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(KdTree.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
