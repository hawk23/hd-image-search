/**
 * Created by mario on 04.07.15.
 */
public class EuclideanDistance
{
    public static double calulate (double[] vector1, double[] vector2)
    {
        double innerSum = 0.0;

        for (int i = 0; i < vector1.length; i++)
        {
            innerSum += Math.pow(vector1[i] - vector2[i], 2.0);
        }

        return Math.sqrt(innerSum);
    }
}
