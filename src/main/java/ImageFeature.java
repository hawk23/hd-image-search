import org.apache.hadoop.fs.Path;

/**
 * Created by hduser on 04.07.15.
 */
public class ImageFeature {

    private String filePath;
    private String fileName;
    private double[] histogram;

    public ImageFeature(String filePath, String fileName, double[] histogram) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.histogram = histogram;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public double[] getHistogram() {
        return histogram;
    }

    public String toString() {

        String doubleValues = "";

        // histogram values with semicolon separated
        for (double v : histogram) {
            doubleValues += v + ";";
        }

        return filePath + " " + doubleValues;
    }

    public static ImageFeature fromString(String line) {

        String filePath = line.split(" ")[0];
        Path path = new Path(filePath);
        String fileName = path.getName();

        String[] doubleValues = line.split(" ")[1].split(";");

        double[] histogram = new double[doubleValues.length];
        for (int i = 0; i < doubleValues.length; i++) {
            histogram[i] = Double.valueOf(doubleValues[i]);
        }

        return new ImageFeature(filePath, fileName, histogram);
    }
}
