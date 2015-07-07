package hdimagesearch.core;

import org.apache.hadoop.fs.Path;

/**
 * Created by hduser on 04.07.15.
 */
public class ImageFeature {

    private String filePath;
    private double[] histogram;

    public ImageFeature(String filePath, double[] histogram) {
        this.filePath = filePath;
        this.histogram = histogram;
    }

    public ImageFeature(String filePath, String histogram) {
        this.filePath = filePath;
        this.histogram = parseHistogramString(histogram);
    }

    public String getFilePath() {
        return filePath;
    }

    public double[] getHistogram() {
        return histogram;
    }

    public String getHistogramString() {

        String doubleValues = "";

        // histogram values with semicolon separated
        for (double v : histogram) {
            doubleValues += v + ";";
        }

        return doubleValues;
    }

    private double[] parseHistogramString(String raw) {

        String[] doubleValues   = raw.split(";");
        double[] histogram      = new double[doubleValues.length];

        for (int i = 0; i < doubleValues.length; i++) {

            histogram[i] = Double.valueOf(doubleValues[i]);
        }

        return histogram;
    }
}
