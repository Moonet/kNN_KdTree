/**
 * Created by Xin on 4/2/16.
 */
public class PointKD{

    public double[] pointkd;

    public PointKD(int n) {
        pointkd = new double[n];
    }

    public PointKD(double[] x){
        pointkd = new double[x.length];
        //
        for(int i = 0; i < x.length; i++){
            pointkd[i] = x[i];
        }
    }

    public boolean equals(PointKD p){
        for(int i = 0; i < pointkd.length; i++){
            if(pointkd[i]!=p.pointkd[i]){
                return false;
            }
        }
        return true;
    }


    public String toString(){
        String s = "";
        for (double p : pointkd) {
            s = s + p + " ";
        }
        return s;
    }

    public double distanceSquaredTo(PointKD that){
        double dist = 0;
        for(int i = 0; i < pointkd.length; i++){
            double diff = that.pointkd[i] - pointkd[i];
            dist += diff * diff;
        }
        return dist;
    }
}
